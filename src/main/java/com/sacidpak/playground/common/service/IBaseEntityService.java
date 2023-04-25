package com.sacidpak.playground.common.service;

import com.sacidpak.playground.common.domain.BaseEntity;
import com.sacidpak.playground.common.dto.BaseEntityDTO;
import com.sacidpak.playground.common.dto.LightShortCodeEntityDTO;
import com.sacidpak.playground.common.dto.PagedApiRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IBaseEntityService<T extends BaseEntity, D extends BaseEntityDTO, ID> {

    ID save (D dto);

    void update(D dto);

    void delete(ID id, Integer version);

    D getById(ID id);

    List<D> getAll(D searchParams);

    Page<D> getPageableList(D searchParams, PagedApiRequest pageParams);

    List<LightShortCodeEntityDTO> getLightList(D searchParams);
}
