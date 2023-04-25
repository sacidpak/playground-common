package com.sacidpak.playground.common.controller;

import com.sacidpak.playground.common.dto.GameDTO;
import com.sacidpak.playground.common.service.IGameService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/game")
public class GameApiController extends BaseController<IGameService, GameDTO> {
}
