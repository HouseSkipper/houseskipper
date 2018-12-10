package fr.univ.lorraine.houseSkipper.repositories;

import fr.univ.lorraine.houseSkipper.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;


@RepositoryRestResource
public interface RoomRepository extends JpaRepository<Room, Long>{
    public List<Room> findAllByHouse(String houseName);
}
