package com.sacidpak.playground.common.service;

import com.sacidpak.playground.common.domain.Play;
import com.sacidpak.playground.common.dto.PlayDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PlayServiceImpl extends BaseServiceImpl<Play, PlayDTO, UUID> implements IPlayService{
}
