package br.com.v3s.authentication.model;

import lombok.Data;

@Data
public class AuthBody {
	private String username;
	private String password;
}
