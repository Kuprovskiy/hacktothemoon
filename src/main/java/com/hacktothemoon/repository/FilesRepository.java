package com.hacktothemoon.repository;

import com.hacktothemoon.domain.Files;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Files entity.
 */
@Repository
public interface FilesRepository extends JpaRepository<Files, Long> {

    Page<Files> findByUuid(Pageable pageable, String uuid);
}
