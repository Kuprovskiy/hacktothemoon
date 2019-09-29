package com.hacktothemoon.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A StorageNode.
 */
@Entity
@Table(name = "storage_node")
public class StorageNode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "node", nullable = false)
    private String node;

    @Column(name = "created_at")
    private Instant createdAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNode() {
        return node;
    }

    public StorageNode node(String node) {
        this.node = node;
        return this;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public StorageNode createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StorageNode)) {
            return false;
        }
        return id != null && id.equals(((StorageNode) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StorageNode{" +
            "id=" + getId() +
            ", node='" + getNode() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
