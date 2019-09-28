package com.hacktothemoon.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.hacktothemoon.domain.enumeration.FileType;

/**
 * A DTO for the {@link com.hacktothemoon.domain.Files} entity.
 */
public class FilesDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String file;

    @NotNull
    private String uuid;

    private Instant createdAt;

    @NotNull
    private FileType fileType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FilesDTO filesDTO = (FilesDTO) o;
        if (filesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), filesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FilesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", file='" + getFile() + "'" +
            ", uuid='" + getUuid() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", fileType='" + getFileType() + "'" +
            "}";
    }
}
