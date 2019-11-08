package br.com.v3s.authentication.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.v3s.authentication.model.Role;
import br.com.v3s.authentication.model.Usuario;
import br.com.v3s.authentication.repository.RoleRepository;
import br.com.v3s.authentication.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	public Usuario findUsuarioByUsername(String username) {
		return usuarioRepository.findByUsername(username);
	}

	public void saveUsuario(Usuario usuario) {
		usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
		usuario.setEnabled(true);

		Role role = roleRepository.findByRole("ADMIN");
		usuario.setRoles(new HashSet<>(Arrays.asList(role)));
		usuarioRepository.save(usuario);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByUsername(username);
		if(usuario != null) {
			List<GrantedAuthority> authorities = getUserAuthority(usuario.getRoles());
			return buildUserForAuthentication(usuario, authorities);
		}else {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
	}

	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
		Set<GrantedAuthority> roles = new HashSet<>();
		
		userRoles.forEach((role) -> {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		});

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
		return grantedAuthorities;
	}
	
	private UserDetails buildUserForAuthentication(Usuario usuario, List<GrantedAuthority> authorities) {
        return new User(usuario.getUsername(), usuario.getPassword(), authorities);
    }
}
