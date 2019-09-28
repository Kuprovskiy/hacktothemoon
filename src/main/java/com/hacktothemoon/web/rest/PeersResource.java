package com.hacktothemoon.web.rest;

import com.hacktothemoon.service.PeersService;
import com.hacktothemoon.web.rest.errors.BadRequestAlertException;
import com.hacktothemoon.service.dto.PeersDTO;

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
 * REST controller for managing {@link com.hacktothemoon.domain.Peers}.
 */
@RestController
@RequestMapping("/api")
public class PeersResource {

    private final Logger log = LoggerFactory.getLogger(PeersResource.class);

    private static final String ENTITY_NAME = "peers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PeersService peersService;

    public PeersResource(PeersService peersService) {
        this.peersService = peersService;
    }

    /**
     * {@code POST  /peers} : Create a new peers.
     *
     * @param peersDTO the peersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new peersDTO, or with status {@code 400 (Bad Request)} if the peers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/peers")
    public ResponseEntity<PeersDTO> createPeers(@Valid @RequestBody PeersDTO peersDTO) throws URISyntaxException {
        log.debug("REST request to save Peers : {}", peersDTO);
        if (peersDTO.getId() != null) {
            throw new BadRequestAlertException("A new peers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeersDTO result = peersService.save(peersDTO);
        return ResponseEntity.created(new URI("/api/peers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /peers} : Updates an existing peers.
     *
     * @param peersDTO the peersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated peersDTO,
     * or with status {@code 400 (Bad Request)} if the peersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the peersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/peers")
    public ResponseEntity<PeersDTO> updatePeers(@Valid @RequestBody PeersDTO peersDTO) throws URISyntaxException {
        log.debug("REST request to update Peers : {}", peersDTO);
        if (peersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PeersDTO result = peersService.save(peersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, peersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /peers} : get all the peers.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of peers in body.
     */
    @GetMapping("/peers")
    public ResponseEntity<List<PeersDTO>> getAllPeers(Pageable pageable) {
        log.debug("REST request to get a page of Peers");
        Page<PeersDTO> page = peersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /peers/:id} : get the "id" peers.
     *
     * @param id the id of the peersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the peersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/peers/{id}")
    public ResponseEntity<PeersDTO> getPeers(@PathVariable Long id) {
        log.debug("REST request to get Peers : {}", id);
        Optional<PeersDTO> peersDTO = peersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(peersDTO);
    }

    /**
     * {@code DELETE  /peers/:id} : delete the "id" peers.
     *
     * @param id the id of the peersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/peers/{id}")
    public ResponseEntity<Void> deletePeers(@PathVariable Long id) {
        log.debug("REST request to delete Peers : {}", id);
        peersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
