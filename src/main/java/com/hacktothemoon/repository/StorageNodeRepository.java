package com.hacktothemoon.repository;

import com.hacktothemoon.domain.StorageNode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StorageNode entity.
 */
@Repository
public interface StorageNodeRepository extends JpaRepository<StorageNode, Long> {

    @Query(value="SELECT * FROM storage_node ORDER BY RAND() LIMIT 1", nativeQuery = true)
    StorageNode findRandomNode();
}
