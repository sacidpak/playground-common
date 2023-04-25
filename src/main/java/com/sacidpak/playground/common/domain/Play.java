package com.sacidpak.playground.common.domain;

import com.sacidpak.playground.common.dto.PlayDTO;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;

@Entity
@Where(clause = "deleted = 0")
public class Play extends BaseShortCodeEntity<Play, PlayDTO>{
}
