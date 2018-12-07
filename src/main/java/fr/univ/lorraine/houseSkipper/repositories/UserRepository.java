package fr.univ.lorraine.houseSkipper.repositories;

import fr.univ.lorraine.houseSkipper.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:4200")
public interface UserRepository extends JpaRepository<ApplicationUser, Long>{

    public ApplicationUser findByUsername(String username);


}
