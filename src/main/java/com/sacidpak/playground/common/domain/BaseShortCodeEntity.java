package com.sacidpak.playground.common.domain;

import com.sacidpak.playground.common.dto.BaseShortCodeEntityDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@MappedSuperclass
@Where(clause = "deleted = 0")
public abstract class BaseShortCodeEntity <T extends BaseShortCodeEntity, D extends BaseShortCodeEntityDTO> extends BaseNameEntity<T, D> {

    @NotNull
    @Size(max = 3)
    private String shortCode;

}
