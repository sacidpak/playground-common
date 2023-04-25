package com.sacidpak.playground.common.service;

import com.sacidpak.playground.common.domain.Game;
import com.sacidpak.playground.common.dto.GameDTO;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class GameServiceImpl extends BaseServiceImpl<Game, GameDTO, UUID> implements IGameService{
}
