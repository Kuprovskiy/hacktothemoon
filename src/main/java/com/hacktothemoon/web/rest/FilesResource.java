package com.hacktothemoon.web.rest;

import com.hacktothemoon.service.FilesService;
import com.hacktothemoon.web.rest.errors.BadRequestAlertException;
import com.hacktothemoon.service.dto.FilesDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hacktothemoon.domain.Files}.
 */
@RestController
@RequestMapping("/api")
public class FilesResource {

    private final Logger log = LoggerFactory.getLogger(FilesResource.class);

    private static final String ENTITY_NAME = "files";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FilesService filesService;

    public FilesResource(FilesService filesService) {
        this.filesService = filesService;
    }

    /**
     * {@code POST  /files} : Create a new files.
     *
     * @param filesDTO the filesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new filesDTO, or with status {@code 400 (Bad Request)} if the files has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/files")
    public ResponseEntity<FilesDTO> createFiles(@Valid @RequestBody FilesDTO filesDTO) throws URISyntaxException {
        log.debug("REST request to save Files : {}", filesDTO);
        if (filesDTO.getId() != null) {
            throw new BadRequestAlertException("A new files cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FilesDTO result = filesService.save(filesDTO);
        return ResponseEntity.created(new URI("/api/files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /files} : Updates an existing files.
     *
     * @param filesDTO the filesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated filesDTO,
     * or with status {@code 400 (Bad Request)} if the filesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the filesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/files")
    public ResponseEntity<FilesDTO> updateFiles(@Valid @RequestBody FilesDTO filesDTO) throws URISyntaxException {
        log.debug("REST request to update Files : {}", filesDTO);
        if (filesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FilesDTO result = filesService.save(filesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, filesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /files} : get all the files.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of files in body.
     */
    @GetMapping("/files")
    public ResponseEntity<List<FilesDTO>> getAllFiles(Pageable pageable) {
        log.debug("REST request to get a page of Files");
        Page<FilesDTO> page = filesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /files/:id} : get the "id" files.
     *
     * @param id the id of the filesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the filesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/files/{id}")
    public ResponseEntity<FilesDTO> getFiles(@PathVariable Long id) {
        log.debug("REST request to get Files : {}", id);
        Optional<FilesDTO> filesDTO = filesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(filesDTO);
    }

    /**
     * {@code DELETE  /files/:id} : delete the "id" files.
     *
     * @param id the id of the filesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/files/{id}")
    public ResponseEntity<Void> deleteFiles(@PathVariable Long id) {
        log.debug("REST request to delete Files : {}", id);
        filesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
