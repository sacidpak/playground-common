package com.sacidpak.playground.common.service;

import com.sacidpak.playground.common.domain.Game;
import com.sacidpak.playground.common.dto.GameDTO;

import java.util.UUID;

public interface IGameService extends IBaseEntityService<Game, GameDTO, UUID>{
}
