package com.hacktothemoon.repository;

import com.hacktothemoon.domain.Files;
import com.hacktothemoon.domain.Peers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Peers entity.
 */
@Repository
public interface PeersRepository extends JpaRepository<Peers, Long> {

    Page<Peers> findByFiles(Pageable pageable, Files file);
}
