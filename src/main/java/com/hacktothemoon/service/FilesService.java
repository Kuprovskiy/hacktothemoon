package com.hacktothemoon.service;

import com.hacktothemoon.service.dto.FilesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.hacktothemoon.domain.Files}.
 */
public interface FilesService {

    /**
     * Save a files.
     *
     * @param filesDTO the entity to save.
     * @return the persisted entity.
     */
    FilesDTO save(FilesDTO filesDTO);

    /**
     * Get all the files.
     *
     * @param pageable the pagination information.
     * @param uuid user id
     * @return the list of entities.
     */
    Page<FilesDTO> findByUuid(Pageable pageable, String uuid);


    /**
     * Get the "id" files.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    FilesDTO findOne(Long id);

    /**
     * Delete the "id" files.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
