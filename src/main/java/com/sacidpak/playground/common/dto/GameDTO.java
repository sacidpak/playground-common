package com.sacidpak.playground.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDTO extends BaseShortCodeEntityDTO{
    private LightShortCodeEntityDTO play;
}
