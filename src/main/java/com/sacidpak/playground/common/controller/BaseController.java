package com.sacidpak.playground.common.controller;

import com.sacidpak.playground.common.dto.BaseEntityDTO;
import com.sacidpak.playground.common.dto.LightShortCodeEntityDTO;
import com.sacidpak.playground.common.dto.PagedApiRequest;
import com.sacidpak.playground.common.service.IBaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public abstract class BaseController<S extends IBaseEntityService, D extends BaseEntityDTO> {

    @Autowired
    private S entityService;

    @GetMapping("/{id}")
    public ResponseEntity<D> getById(@PathVariable UUID id){
        return ResponseEntity.ok((D)entityService.getById(id));
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<D>> getListAll (D searchParams){
        return ResponseEntity.ok(entityService.getAll(searchParams));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<D>> getPageList (D searchParams, PagedApiRequest pageParams){
        return ResponseEntity.ok(entityService.getPageableList(searchParams,pageParams));
    }

    @GetMapping("/lightList")
    public ResponseEntity<List<LightShortCodeEntityDTO>> getLightList (D searchParams){
        return ResponseEntity.ok(entityService.getLightList(searchParams));
    }

    @PostMapping
    public ResponseEntity<UUID> save (@RequestBody D dto){
        return ResponseEntity.ok((UUID) entityService.save(dto));
    }

    @PutMapping
    public void update (@RequestBody D dto){
        entityService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete (@RequestParam UUID id, @RequestParam Integer version){
        entityService.delete(id,version);
    }

}
