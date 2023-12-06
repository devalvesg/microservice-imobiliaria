package com.glimoveis.Imob_back.controllers;

import com.glimoveis.Imob_back.entities.Immobiles;
import com.glimoveis.Imob_back.services.ImmobilesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/imoveis")
public class ImmobilesController {
    private ImmobilesService immobilesService;
    public ImmobilesController (ImmobilesService immobilesService){
        this.immobilesService = immobilesService;
    }

    @GetMapping()
    public ResponseEntity<List<Immobiles>> findAll(){
        return ResponseEntity.ok(immobilesService.getAll());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Immobiles>> findByType(@PathVariable("type")String type){
        return ResponseEntity.ok(immobilesService.findByType(type));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Immobiles> findById(@PathVariable("id")Long id){
        return ResponseEntity.ok(immobilesService.findById(id));
    }


}
