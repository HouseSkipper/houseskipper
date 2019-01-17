package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@Table(name = "taskPJ")
public class UploadFileResponse {
    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    private @NonNull String fileName;
    private @NonNull String fileDownloadUri;
    private @NonNull String fileType;
    private @NonNull long size;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    //@JsonIgnoreProperties("houses")
    @JsonBackReference
    private @NonNull Task task;

    public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
