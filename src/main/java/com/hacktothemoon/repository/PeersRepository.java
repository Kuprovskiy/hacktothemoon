package com.hacktothemoon.repository;

import com.hacktothemoon.domain.Peers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Peers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeersRepository extends JpaRepository<Peers, Long> {

}
