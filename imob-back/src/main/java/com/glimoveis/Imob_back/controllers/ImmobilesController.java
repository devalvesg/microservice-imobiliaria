package com.glimoveis.Imob_back.controllers;

import com.glimoveis.Imob_back.DTOs.DeleteImobDTO;
import com.glimoveis.Imob_back.DTOs.ImmobilesDTO;
import com.glimoveis.Imob_back.DTOs.UserResponse;
import com.glimoveis.Imob_back.models.Immobiles;
import com.glimoveis.Imob_back.models.User;
import com.glimoveis.Imob_back.producers.EmailProducer;
import com.glimoveis.Imob_back.services.ImmobilesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
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
        type = type.toLowerCase();
        type = type.substring(0, 1).toUpperCase() + type.substring(1);
        return ResponseEntity.ok(immobilesService.findByType(type));
    }

    @GetMapping("/id")
    public ResponseEntity findById(@RequestParam(name = "id")Long id){
        try{
            Immobiles immobiles = immobilesService.findById(id);
            return ResponseEntity.ok(immobiles);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Imóvel não encontrado em nossa base de dados.");
        }
    }


    @PostMapping("/new-property")
    public ResponseEntity newProperty(@RequestBody @Valid ImmobilesDTO immobilesDTO, @AuthenticationPrincipal User user){
        try{
            Immobiles immobiles = immobilesService.newProperty(immobilesDTO, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(immobiles);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteImob(@RequestBody DeleteImobDTO imobDel, @AuthenticationPrincipal User user){
        Long id = imobDel.id();
        try{
            immobilesService.deleteImob(id, user);
            return ResponseEntity.status(HttpStatus.OK).body("O imóvel foi deletado com sucesso!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/usuario")
    public ResponseEntity imobByUserLogged(@AuthenticationPrincipal User user){
        if(user == null) return ResponseEntity.badRequest().body("Você precisa estar logado para ver os seus imóveis");
        List<Immobiles> imoveisByUser = immobilesService.imobByUserLogged(user.getId());
        return ResponseEntity.ok().body(imoveisByUser);
    }
}
