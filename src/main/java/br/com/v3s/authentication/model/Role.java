package br.com.v3s.authentication.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Document()
@ApiModel(description="Role")
public class Role implements Serializable {
	private static final long serialVersionUID = 2652797113543449221L;
	
	@Id
	@ApiModelProperty(notes="Id", position=0)
	private String id;
	
	@NotNull()
	@Size(max=20)
	@ApiModelProperty(notes="Role", position=1)
	private String role;

}