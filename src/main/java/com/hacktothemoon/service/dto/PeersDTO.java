package com.hacktothemoon.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.hacktothemoon.domain.Peers} entity.
 */
public class PeersDTO implements Serializable {

    private Long id;

    @NotNull
    private String url;

    private String reserved;

    @NotNull
    private Integer quantity;


    private Long filesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getFilesId() {
        return filesId;
    }

    public void setFilesId(Long filesId) {
        this.filesId = filesId;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PeersDTO peersDTO = (PeersDTO) o;
        if (peersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), peersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PeersDTO{" +
            "id=" + getId() +
            ", url='" + getUrl() + "'" +
            ", quantity=" + getQuantity() +
            ", files=" + getFilesId() +
            "}";
    }
}
