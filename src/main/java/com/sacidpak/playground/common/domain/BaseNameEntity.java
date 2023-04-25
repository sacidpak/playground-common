package com.sacidpak.playground.common.domain;

import com.sacidpak.playground.common.dto.BaseNameEntityDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@MappedSuperclass
@Where(clause = "deleted = 0")
public abstract class BaseNameEntity<T extends BaseNameEntity, D extends BaseNameEntityDTO> extends BaseEntity<T, D> {

    @NotNull
    private String code;

    @NotNull
    private String name;

}
