package com.hacktothemoon.service.impl;

import com.hacktothemoon.repository.PeersRepository;
import com.hacktothemoon.service.FilesService;
import com.hacktothemoon.domain.Files;
import com.hacktothemoon.repository.FilesRepository;
import com.hacktothemoon.service.dto.FilesDTO;
import com.hacktothemoon.service.dto.PeersDTO;
import com.hacktothemoon.service.mapper.FilesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Files}.
 */
@Service
@Transactional
public class FilesServiceImpl implements FilesService {

    private final Logger log = LoggerFactory.getLogger(FilesServiceImpl.class);

    private final FilesRepository filesRepository;

    private final PeersServiceImpl peersServiceImpl;

    private final FilesMapper filesMapper;

    public FilesServiceImpl(FilesRepository filesRepository,
                            FilesMapper filesMapper,
                            PeersServiceImpl peersServiceImpl) {
        this.filesRepository = filesRepository;
        this.filesMapper = filesMapper;
        this.peersServiceImpl = peersServiceImpl;
    }

    /**
     * Save a files.
     *
     * @param filesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FilesDTO save(FilesDTO filesDTO) {
        log.debug("Request to save Files : {}", filesDTO);
        Files files = filesMapper.toEntity(filesDTO);
        files = filesRepository.save(files);

        String file = filesDTO.getFile();
        String[] splitedFile = splitToNChar(file, 32);

        String node = getRandomNode();


        int i = 0;
        for (String splitted : splitedFile) {
            i++;
            PeersDTO peer = new PeersDTO();
            peer.setQuantity(i);
            peer.setUrl(getRandomPeer(splitted));
            peer.setFilesId(files.getId());
            peersServiceImpl.save(peer);
        }

        return filesMapper.toDto(files);
    }

    private String getRandomNode() {

        return "node1";
    }

    /**
     * Split text into n number of characters.
     *
     * @param text the text to be split.
     * @param size the split size.
     * @return an array of the split text.
     */
    private static String[] splitToNChar(String text, int size) {
        List<String> parts = new ArrayList<>();

        int length = text.length();
        for (int i = 0; i < length; i += size) {
            parts.add(text.substring(i, Math.min(length, i + size)));
        }
        return parts.toArray(new String[0]);
    }

    private String getRandomPeer(String splitted) {
        return splitted;
    }

    /**
     * Get all the files.
     * @param uuid user Id
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FilesDTO> findByUuid(Pageable pageable, String uuid) {
        log.debug("Request to get all Files");
        return filesRepository.findByUuid(pageable, uuid)
            .map(filesMapper::toDto);
    }


    /**
     * Get one files by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FilesDTO> findOne(Long id) {
        log.debug("Request to get Files : {}", id);
        return filesRepository.findById(id)
            .map(filesMapper::toDto);
    }

    /**
     * Delete the files by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Files : {}", id);
        filesRepository.deleteById(id);
    }
}
