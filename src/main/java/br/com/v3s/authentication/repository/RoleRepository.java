package br.com.v3s.authentication.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.v3s.authentication.model.Role;

@Repository
public interface RoleRepository extends MongoRepository<Role, String>, RoleRepositoryCustom {

	public Role findByRole(String role);
}
