package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@Table(name = "taskPJ")
@NoArgsConstructor
public class UploadFileResponse {
    @Id
    @GeneratedValue
    private Long id;

    private @NonNull String fileName;
    private @NonNull String fileDownloadUri;
    private @NonNull String fileType;
    private @NonNull long size;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    @JsonBackReference
    private @NonNull Task task;

    @Lob
    @Column(name = "pic")
    private byte[] pic;


    public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size, byte[] pic) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
        this.pic = pic;
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
