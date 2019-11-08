package br.com.v3s.authentication.model;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Document()
@ApiModel(description="Usuário")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 8303906934237266812L;
	
	@Id
	@ApiModelProperty(notes="Id", position=0)
	private String id;
	
	@NotNull()
	@Size(max=100)
	@ApiModelProperty(notes="Username", position=1)
	private String username;
	
	@NotNull()
	@Size(max=20)
	@ApiModelProperty(notes="Password", position=2)
	private String password;
	
	@NotNull()
	@ApiModelProperty(notes="Enabled", position=3)
	private boolean enabled;
	
	@DBRef
	@ApiModelProperty(notes="Roles do usuário", position=4)
	private Set<Role> roles;
}