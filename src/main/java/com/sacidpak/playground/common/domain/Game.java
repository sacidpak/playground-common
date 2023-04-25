package com.sacidpak.playground.common.domain;

import com.sacidpak.playground.common.dto.GameDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Where(clause = "deleted = 0")
public class Game extends BaseShortCodeEntity<Game, GameDTO>{

    @NotNull
    @ManyToOne
    @JoinColumn
    private Play play;
}
