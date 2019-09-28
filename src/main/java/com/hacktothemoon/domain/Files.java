package com.hacktothemoon.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.hacktothemoon.domain.enumeration.FileType;

/**
 * A Files.
 */
@Entity
@Table(name = "files")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "file", nullable = false)
    private String file;

    @NotNull
    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "created_at")
    private Instant createdAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", nullable = false)
    private FileType fileType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Files name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public Files file(String file) {
        this.file = file;
        return this;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getUuid() {
        return uuid;
    }

    public Files uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Files createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public FileType getFileType() {
        return fileType;
    }

    public Files fileType(FileType fileType) {
        this.fileType = fileType;
        return this;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Files)) {
            return false;
        }
        return id != null && id.equals(((Files) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Files{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", file='" + getFile() + "'" +
            ", uuid='" + getUuid() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", fileType='" + getFileType() + "'" +
            "}";
    }
}
