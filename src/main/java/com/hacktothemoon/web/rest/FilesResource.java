package com.hacktothemoon.web.rest;

import com.hacktothemoon.domain.enumeration.FileType;
import com.hacktothemoon.service.FilesService;
import com.hacktothemoon.service.dto.FilesDTO;
import com.hacktothemoon.web.rest.errors.BadRequestAlertException;
import com.hacktothemoon.web.rest.errors.UnsupportedMediaTypeException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
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

    private static final List<String> imageContentTypes = Arrays.asList("image/png", "image/jpeg");

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

    public static String uploadDirectory = "/home/user/apps/hacktothemoon-be/nodes";

    @PostMapping("/upload")
    public ResponseEntity<FilesDTO> uploadPostImage(
        @RequestHeader HttpHeaders headers,
        @RequestParam("file") MultipartFile files,
        @RequestParam("uuid") String uuid,
        @RequestParam("fileType") String fileType,
        @RequestParam("name") String name
    ) {
        log.debug("REST request to save Files : {}", files);
        log.debug("headers : {}", headers);
        log.debug("uuid : {}", uuid);

        validateRequest(files, imageContentTypes);

        String path1 = "";
        Path fileNameAndPath = Paths.get(uploadDirectory, files.getOriginalFilename());
        try {
            Files.write(fileNameAndPath, files.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        path1=fileNameAndPath.toString();

        FilesDTO newFile = new FilesDTO();
        newFile.setFile(path1);
        newFile.setName(name);
        newFile.setUuid(uuid);
        newFile.setFileType(mapFileType(fileType));

        FilesDTO result = filesService.save(newFile);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, null))
            .body(result);
    }

    private FileType mapFileType(String fileType) {

        switch (fileType.toUpperCase()) {
            case "JPEG":
                return FileType.JPEG;
            case "PNG":
                return FileType.PNG;
            case "PDF":
                return FileType.PDF;
            case "TXT":
                return FileType.TXT;
        }

        return null;
    }

    private void validateRequest(MultipartFile file, List<String> imageContentTypes) {
        String fileContentType = file.getContentType();
        if(!imageContentTypes.contains(fileContentType)) {
            throw new UnsupportedMediaTypeException();
        }
    }

    /**
     * {@code GET  /files} : get all the files.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of files in body.
     */
    @GetMapping("/files")
    public ResponseEntity<List<FilesDTO>> getAllFiles(Pageable pageable,
                                                      @RequestParam(value = "uuid") String uuid) {
        log.debug("REST request to get a page of Files");
        Page<FilesDTO> page = filesService.findByUuid(pageable, uuid);
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
