package com.hacktothemoon.web.rest;

import com.hacktothemoon.HacktothemoonApp;
import com.hacktothemoon.domain.Files;
import com.hacktothemoon.repository.FilesRepository;
import com.hacktothemoon.service.FilesService;
import com.hacktothemoon.service.dto.FilesDTO;
import com.hacktothemoon.service.mapper.FilesMapper;
import com.hacktothemoon.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.hacktothemoon.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hacktothemoon.domain.enumeration.FileType;
/**
 * Integration tests for the {@link FilesResource} REST controller.
 */
@SpringBootTest(classes = HacktothemoonApp.class)
public class FilesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE = "AAAAAAAAAA";
    private static final String UPDATED_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_CREATED_AT = Instant.ofEpochMilli(-1L);

    private static final FileType DEFAULT_FILE_TYPE = FileType.PDF;
    private static final FileType UPDATED_FILE_TYPE = FileType.JPEG;

    @Autowired
    private FilesRepository filesRepository;

    @Autowired
    private FilesMapper filesMapper;

    @Autowired
    private FilesService filesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restFilesMockMvc;

    private Files files;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FilesResource filesResource = new FilesResource(filesService);
        this.restFilesMockMvc = MockMvcBuilders.standaloneSetup(filesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Files createEntity(EntityManager em) {
        Files files = new Files()
            .name(DEFAULT_NAME)
            .file(DEFAULT_FILE)
            .uuid(DEFAULT_UUID)
            .createdAt(DEFAULT_CREATED_AT)
            .fileType(DEFAULT_FILE_TYPE);
        return files;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Files createUpdatedEntity(EntityManager em) {
        Files files = new Files()
            .name(UPDATED_NAME)
            .file(UPDATED_FILE)
            .uuid(UPDATED_UUID)
            .createdAt(UPDATED_CREATED_AT)
            .fileType(UPDATED_FILE_TYPE);
        return files;
    }

    @BeforeEach
    public void initTest() {
        files = createEntity(em);
    }

    @Test
    @Transactional
    public void createFilesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = filesRepository.findAll().size();

        // Create the Files with an existing ID
        files.setId(1L);
        FilesDTO filesDTO = filesMapper.toDto(files);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilesMockMvc.perform(post("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = filesRepository.findAll().size();
        // set the field null
        files.setName(null);

        // Create the Files, which fails.
        FilesDTO filesDTO = filesMapper.toDto(files);

        restFilesMockMvc.perform(post("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filesDTO)))
            .andExpect(status().isBadRequest());

        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFileIsRequired() throws Exception {
        int databaseSizeBeforeTest = filesRepository.findAll().size();
        // set the field null
        files.setFile(null);

        // Create the Files, which fails.
        FilesDTO filesDTO = filesMapper.toDto(files);

        restFilesMockMvc.perform(post("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filesDTO)))
            .andExpect(status().isBadRequest());

        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = filesRepository.findAll().size();
        // set the field null
        files.setUuid(null);

        // Create the Files, which fails.
        FilesDTO filesDTO = filesMapper.toDto(files);

        restFilesMockMvc.perform(post("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filesDTO)))
            .andExpect(status().isBadRequest());

        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFileTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = filesRepository.findAll().size();
        // set the field null
        files.setFileType(null);

        // Create the Files, which fails.
        FilesDTO filesDTO = filesMapper.toDto(files);

        restFilesMockMvc.perform(post("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filesDTO)))
            .andExpect(status().isBadRequest());

        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeTest);
    }
    
    @Test
    @Transactional
    public void getFiles() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get the files
        restFilesMockMvc.perform(get("/api/files/{id}", files.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(files.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.file").value(DEFAULT_FILE.toString()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.fileType").value(DEFAULT_FILE_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFiles() throws Exception {
        // Get the files
        restFilesMockMvc.perform(get("/api/files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteFiles() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        int databaseSizeBeforeDelete = filesRepository.findAll().size();

        // Delete the files
        restFilesMockMvc.perform(delete("/api/files/{id}", files.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Files.class);
        Files files1 = new Files();
        files1.setId(1L);
        Files files2 = new Files();
        files2.setId(files1.getId());
        assertThat(files1).isEqualTo(files2);
        files2.setId(2L);
        assertThat(files1).isNotEqualTo(files2);
        files1.setId(null);
        assertThat(files1).isNotEqualTo(files2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FilesDTO.class);
        FilesDTO filesDTO1 = new FilesDTO();
        filesDTO1.setId(1L);
        FilesDTO filesDTO2 = new FilesDTO();
        assertThat(filesDTO1).isNotEqualTo(filesDTO2);
        filesDTO2.setId(filesDTO1.getId());
        assertThat(filesDTO1).isEqualTo(filesDTO2);
        filesDTO2.setId(2L);
        assertThat(filesDTO1).isNotEqualTo(filesDTO2);
        filesDTO1.setId(null);
        assertThat(filesDTO1).isNotEqualTo(filesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(filesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(filesMapper.fromId(null)).isNull();
    }
}
