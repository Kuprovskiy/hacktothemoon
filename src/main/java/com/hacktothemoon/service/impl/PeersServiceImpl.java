package com.hacktothemoon.service.impl;

import com.hacktothemoon.domain.Files;
import com.hacktothemoon.repository.FilesRepository;
import com.hacktothemoon.service.PeersService;
import com.hacktothemoon.domain.Peers;
import com.hacktothemoon.repository.PeersRepository;
import com.hacktothemoon.service.dto.PeersDTO;
import com.hacktothemoon.service.mapper.PeersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Peers}.
 */
@Service
@Transactional
public class PeersServiceImpl implements PeersService {

    private final Logger log = LoggerFactory.getLogger(PeersServiceImpl.class);

    private final PeersRepository peersRepository;

    private final FilesRepository filesRepository;

    private final PeersMapper peersMapper;

    public PeersServiceImpl(PeersRepository peersRepository,
                            PeersMapper peersMapper,
                            FilesRepository filesRepository) {
        this.peersRepository = peersRepository;
        this.peersMapper = peersMapper;
        this.filesRepository = filesRepository;
    }

    /**
     * Save a peers.
     *
     * @param peersDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PeersDTO save(PeersDTO peersDTO) {
        log.debug("Request to save Peers : {}", peersDTO);
        Peers peers = peersMapper.toEntity(peersDTO);
        peers = peersRepository.save(peers);
        return peersMapper.toDto(peers);
    }

    /**
     * Get all the peers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PeersDTO> findAll(Pageable pageable, Long id) {
        log.debug("Request to get all Peers");

        Files file = filesRepository.getOne(id);

        return peersRepository.findByFiles(pageable, file)
            .map(peersMapper::toDto);
    }


    /**
     * Get one peers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PeersDTO> findOne(Long id) {
        log.debug("Request to get Peers : {}", id);
        return peersRepository.findById(id)
            .map(peersMapper::toDto);
    }

    /**
     * Delete the peers by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Peers : {}", id);
        peersRepository.deleteById(id);
    }
}
