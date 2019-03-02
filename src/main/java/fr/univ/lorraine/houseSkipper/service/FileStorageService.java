package fr.univ.lorraine.houseSkipper.service;

import fr.univ.lorraine.houseSkipper.configuration.FileStorageProperties;
import fr.univ.lorraine.houseSkipper.exceptions.FileStorageException;
import fr.univ.lorraine.houseSkipper.exceptions.MyFileNotFoundException;
import fr.univ.lorraine.houseSkipper.model.Task;
import fr.univ.lorraine.houseSkipper.model.UploadFileResponse;
import fr.univ.lorraine.houseSkipper.repositories.FileRepository;
import fr.univ.lorraine.houseSkipper.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileStorageService {

    public final Path fileStorageLocation;
    private FileRepository fileRepository;
    private TaskRepository repository;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties, FileRepository fileRepository, TaskRepository repository) {
        this.fileRepository = fileRepository;
        this.repository = repository;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file, String Id) {
        // Normalize file name
        String fileN = StringUtils.cleanPath(file.getOriginalFilename());
        String fileName = fileN.replace(" ", "");

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            /*------------------------*/
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();

            UploadFileResponse fileResponse = new UploadFileResponse(fileName, filePath.toUri().toString(), "pdf/image", file.getSize(), file.getBytes());
            System.out.println("FileResponse : -----------------" + fileResponse.getFileName());

            Task task = this.repository.findByNom(Id).get(0);

                System.out.println("tskBudget : !!!!-----------------" + task.getPartiesExacte().size());
                fileResponse.setTask(task);
                fileRepository.save(fileResponse);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public List<String> loadFileAsResource() {
        File folder = new File("./uploads");
        File[] listOfFiles = folder.listFiles();
        List<String> names = new ArrayList<>();
        for (File f:
                listOfFiles) {
            names.add(f.getName());
        }

        return names;
    }

    public ResponseEntity<?> downloadFile(String file, HttpServletRequest request){
        String fileName = file.replace("\\s", "");
        System.out.println("Request received to download file");
        System.out.println("File to download :"+fileName);
        try{
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            byte[] content = null;
            try {
                content = FileCopyUtils.copyToByteArray(resource.getInputStream());
                System.out.println("Converted file to bytes successfully.");
            } catch (IOException e1) {
                System.err.println("Error while converting file to bytes.");
                e1.printStackTrace();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("text/csv"));
            System.out.println("Download sample file request completed");
            return new ResponseEntity<byte[]>(content, headers, HttpStatus.OK);


        }catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }
}
