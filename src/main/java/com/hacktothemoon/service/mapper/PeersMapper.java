package com.hacktothemoon.service.mapper;

import com.hacktothemoon.domain.*;
import com.hacktothemoon.service.dto.PeersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Peers} and its DTO {@link PeersDTO}.
 */
@Mapper(componentModel = "spring", uses = {FilesMapper.class})
public interface PeersMapper extends EntityMapper<PeersDTO, Peers> {

    @Mapping(source = "files.id", target = "filesId")
    PeersDTO toDto(Peers peers);

    @Mapping(source = "filesId", target = "files")
    Peers toEntity(PeersDTO peersDTO);

    default Peers fromId(Long id) {
        if (id == null) {
            return null;
        }
        Peers peers = new Peers();
        peers.setId(id);
        return peers;
    }
}
