package com.sacidpak.playground.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LightEntityDTO {
    private UUID id;
    private Long version;
}
