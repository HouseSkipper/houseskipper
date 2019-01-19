package fr.univ.lorraine.houseSkipper.repositories;

import fr.univ.lorraine.houseSkipper.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Override
    Optional<Task> findById(Long aLong);

    /*
    @Query("SELECT t FROM Task t WHERE t.room = :room")
        Task findTaskByRoom(@Param("room") String room);
     */
    Optional<Task> findByName(String name);

}
