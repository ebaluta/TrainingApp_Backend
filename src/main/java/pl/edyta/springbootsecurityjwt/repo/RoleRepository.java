package pl.edyta.springbootsecurityjwt.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edyta.springbootsecurityjwt.models.ERole;
import pl.edyta.springbootsecurityjwt.models.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role,Integer> {
    Optional <Role> findByName(ERole name);

}
