package com.sacidpak.playground.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LightNameEntityDTO extends LightEntityDTO {
    private String code;
    private String name;
}
