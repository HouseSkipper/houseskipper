package fr.univ.lorraine.houseSkipper.controller;

import fr.univ.lorraine.houseSkipper.model.Task;
import fr.univ.lorraine.houseSkipper.model.UploadFileResponse;
import fr.univ.lorraine.houseSkipper.repositories.FileRepository;
import fr.univ.lorraine.houseSkipper.repositories.TaskRepository;
import fr.univ.lorraine.houseSkipper.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private TaskRepository repository;

    @Autowired
    private FileRepository fileRepository;

    @PostMapping("/uploadFile/{Id}")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String Id) {
        try{
                    System.out.println("-----------" + Id);
                    String fileName = fileStorageService.storeFile(file, Id, 0, "");

                     String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/downloadFile/")
                            .path(fileName)
                            .toUriString();
                    System.out.println(fileDownloadUri + "---------!!!" + fileName + "-----------!!");

                    return new UploadFileResponse(fileName, fileDownloadUri,
                            file.getContentType(), file.getSize(), file.getBytes());
        }catch (IOException ex){
                ex.printStackTrace();
                return new UploadFileResponse();
            }
    }

    @GetMapping("/downloadFile/{fileName:.+}/{Id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String fileName, @PathVariable Long Id) {
        Optional<Task> task = this.repository.findById(Id);
        List<UploadFileResponse> fileResponses = new ArrayList<>();
        if(task.isPresent()){
            Task tsk = task.get();
            fileResponses = fileRepository.findAllByTask(tsk);
        }

        System.out.println(fileResponses.size()+"------------------FilesSize");
        for (UploadFileResponse file: fileResponses
             ) {
            if (file.getFileName().equals(fileName)){
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                        .body(file.getPic());
            }
        }
        /*
        Optional<UploadFileResponse> fileOptional = fileRepository.findByFileName(fileName);

        if(fileOptional.isPresent()) {
            UploadFileResponse file = fileOptional.get();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                    .body(file.getPic());
        }
         */


        return ResponseEntity.status(404).body(null);
    }

    @GetMapping("/fileNames/{taskId}")
    public List<String> fileNames(@PathVariable Long taskId) {
        System.out.println("StoreFile : -----------------" + taskId);
        Optional<Task> task = this.repository.findById(taskId);
        List<String> fileName = new ArrayList<>();
        if(task.isPresent()){
            Task tsk = task.get();
            System.out.println("StoreFile : -----------------" + tsk.getPartiesExacte().size());
            for (UploadFileResponse f: tsk.getFiles()
            ) {
                System.out.println("StoreFile : -----------------" + f.getFileName());
                fileName.add(f.getFileName());
            }
            return fileName;
        }

        return new ArrayList<>();
    }

    @GetMapping("/downloadFilenames")
    public String downloadFiles() {
        // Load file as Resource
        List<String> resource = fileStorageService.loadFileAsResource();
        StringBuffer sb = new StringBuffer();

        for (String s:
                resource) {
            sb.append("; filename=\"" + s + "\"");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }
}