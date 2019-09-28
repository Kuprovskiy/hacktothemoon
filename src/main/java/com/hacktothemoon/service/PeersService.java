package com.hacktothemoon.service;

import com.hacktothemoon.service.dto.PeersDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.hacktothemoon.domain.Peers}.
 */
public interface PeersService {

    /**
     * Save a peers.
     *
     * @param peersDTO the entity to save.
     * @return the persisted entity.
     */
    PeersDTO save(PeersDTO peersDTO);

    /**
     * Get all the peers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PeersDTO> findAll(Pageable pageable);


    /**
     * Get the "id" peers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PeersDTO> findOne(Long id);

    /**
     * Delete the "id" peers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
