package com.glimoveis.Imob_back.controllers;

import com.glimoveis.Imob_back.DTOs.ImmobilesDTO;
import com.glimoveis.Imob_back.entities.Immobiles;
import com.glimoveis.Imob_back.services.ImmobilesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/type")
    public ResponseEntity<List<Immobiles>> findByType(@RequestParam(name = "type")String type){
        return ResponseEntity.ok(immobilesService.findByType(type));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id")Long id){
        try{
            Immobiles immobiles = immobilesService.findById(id);
            return ResponseEntity.ok(immobiles);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Imóvel não encontrado em nossa base de dados.");
        }
    }

    @PostMapping("/novo-imovel")
    public ResponseEntity newImob(@RequestBody @Valid ImmobilesDTO immobilesDTO){

        try{
        Immobiles immobiles = new Immobiles(immobilesDTO);
        immobilesService.newImmobile(immobiles);
        return ResponseEntity.status(HttpStatus.CREATED).body(immobiles);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Houve um erro ao cadastrar o imóvel, verifique os dados e tente novamente!");
        }



    }


}
