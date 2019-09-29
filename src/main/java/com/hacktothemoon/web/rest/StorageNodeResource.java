package com.hacktothemoon.web.rest;

import com.hacktothemoon.domain.StorageNode;
import com.hacktothemoon.repository.StorageNodeRepository;
import com.hacktothemoon.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hacktothemoon.domain.StorageNode}.
 */
@RestController
@RequestMapping("/api")
public class StorageNodeResource {

    private final Logger log = LoggerFactory.getLogger(StorageNodeResource.class);

    private static final String ENTITY_NAME = "storageNode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StorageNodeRepository storageNodeRepository;

    public StorageNodeResource(StorageNodeRepository storageNodeRepository) {
        this.storageNodeRepository = storageNodeRepository;
    }

    /**
     * {@code POST  /storage-nodes} : Create a new storageNode.
     *
     * @param storageNode the storageNode to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new storageNode, or with status {@code 400 (Bad Request)} if the storageNode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/storage-nodes")
    public ResponseEntity<StorageNode> createStorageNode(@Valid @RequestBody StorageNode storageNode) throws URISyntaxException {
        log.debug("REST request to save StorageNode : {}", storageNode);
        if (storageNode.getId() != null) {
            throw new BadRequestAlertException("A new storageNode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StorageNode result = storageNodeRepository.save(storageNode);
        return ResponseEntity.created(new URI("/api/storage-nodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /storage-nodes} : Updates an existing storageNode.
     *
     * @param storageNode the storageNode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated storageNode,
     * or with status {@code 400 (Bad Request)} if the storageNode is not valid,
     * or with status {@code 500 (Internal Server Error)} if the storageNode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/storage-nodes")
    public ResponseEntity<StorageNode> updateStorageNode(@Valid @RequestBody StorageNode storageNode) throws URISyntaxException {
        log.debug("REST request to update StorageNode : {}", storageNode);
        if (storageNode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StorageNode result = storageNodeRepository.save(storageNode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, storageNode.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /storage-nodes} : get all the storageNodes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of storageNodes in body.
     */
    @GetMapping("/storage-nodes")
    public List<StorageNode> getAllStorageNodes() {
        log.debug("REST request to get all StorageNodes");
        return storageNodeRepository.findAll();
    }

    /**
     * {@code GET  /storage-nodes/:id} : get the "id" storageNode.
     *
     * @param id the id of the storageNode to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the storageNode, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/storage-nodes/{id}")
    public ResponseEntity<StorageNode> getStorageNode(@PathVariable Long id) {
        log.debug("REST request to get StorageNode : {}", id);
        Optional<StorageNode> storageNode = storageNodeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(storageNode);
    }

    /**
     * {@code DELETE  /storage-nodes/:id} : delete the "id" storageNode.
     *
     * @param id the id of the storageNode to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/storage-nodes/{id}")
    public ResponseEntity<Void> deleteStorageNode(@PathVariable Long id) {
        log.debug("REST request to delete StorageNode : {}", id);
        storageNodeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
