package br.com.v3s.authentication.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.v3s.authentication.model.AuthBody;
import br.com.v3s.authentication.model.Usuario;
import br.com.v3s.authentication.provider.JwtTokenProvider;
import br.com.v3s.authentication.repository.UsuarioRepository;
import br.com.v3s.authentication.service.CustomUserDetailsService;

@RestController
@RequestMapping("/auth/v1")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@SuppressWarnings("rawtypes")
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody AuthBody authBody) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authBody.getUsername(), authBody.getPassword()));
			String token = jwtTokenProvider.createToken(authBody.getUsername(), usuarioRepository.findByUsername(authBody.getUsername()).getRoles());

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("CurrentUser", authBody.getUsername());
			responseHeaders.set("Authorization", token);
			
			return ResponseEntity.ok().headers(responseHeaders).body("");
		}catch(AuthenticationException e) {
			throw new BadCredentialsException("Usuário ou senha inválidos");
		}
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody Usuario usuario) {
		Usuario usuarioExists = userDetailsService.findUsuarioByUsername(usuario.getUsername());
		if(usuarioExists != null) {
			throw new BadCredentialsException("Já existe um usuário com o login " + usuario.getUsername());
		}

		userDetailsService.saveUsuario(usuario);
		Map<Object, Object> model = new HashMap<>();
		model.put("message", "Usuário cadastrado com sucesso");
		return ok(model);
	}
}
