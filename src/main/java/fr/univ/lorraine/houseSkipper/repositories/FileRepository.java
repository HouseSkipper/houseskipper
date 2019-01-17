package fr.univ.lorraine.houseSkipper.repositories;

import fr.univ.lorraine.houseSkipper.model.UploadFileResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface FileRepository extends JpaRepository<UploadFileResponse, Long> {
    List<UploadFileResponse> findAllByTask(Long taskId);
}
