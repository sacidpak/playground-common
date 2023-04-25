package com.sacidpak.playground.common.controller;

import com.sacidpak.playground.common.dto.PlayDTO;
import com.sacidpak.playground.common.service.IPlayService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/play")
public class PlayApiController extends BaseController<IPlayService, PlayDTO> {
}
