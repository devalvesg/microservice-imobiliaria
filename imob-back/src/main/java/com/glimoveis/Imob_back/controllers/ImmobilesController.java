package com.glimoveis.Imob_back.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glimoveis.Imob_back.DTOs.DeleteImobDTO;
import com.glimoveis.Imob_back.DTOs.ImmobilesDTO;
import com.glimoveis.Imob_back.DTOs.Responses.ImmobileResponse;
import com.glimoveis.Imob_back.models.Immobiles;
import com.glimoveis.Imob_back.models.User;
import com.glimoveis.Imob_back.services.ImmobilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/imoveis")
public class ImmobilesController {
    private ImmobilesService immobilesService;

    @Value("${url.block.new.property}")
    private static String urlStatus;
    private boolean blockNewProperties;

    public ImmobilesController (ImmobilesService immobilesService){
        this.immobilesService = immobilesService;
    }

    @GetMapping()
    public ResponseEntity<List<ImmobileResponse>> findAll(){
        return ResponseEntity.ok(immobilesService.getAll());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<ImmobileResponse>> findByType(@PathVariable(name = "type")String type){
        type = type.toLowerCase();
        type = type.substring(0, 1).toUpperCase() + type.substring(1);
        return ResponseEntity.ok(immobilesService.findByType(type));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable(name = "id")Long id){
        try{
            ImmobileResponse immobiles = immobilesService.findById(id);
            return ResponseEntity.ok(immobiles);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Imóvel não encontrado em nossa base de dados.");
        }
    }

    @PostMapping(value = "/new-property", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity newProperty( @RequestParam(value = "immobileJson", required = false) String immobileText, @AuthenticationPrincipal User user, @RequestParam("file") List<MultipartFile> images){
        try{
            if(blockNewProperties){
               throw new Exception("O registro de novos imóveis está desativado, consulte o administrador da API para mais informações.");
            }
            if(user == null){
                throw new Exception("Você precisa estar logado para cadastrar um novo imóvel.");
            }
            ObjectMapper obj = new ObjectMapper();
            ImmobilesDTO immobilesDTO = obj.readValue(immobileText, ImmobilesDTO.class);
            Immobiles immobiles = immobilesService.newProperty(immobilesDTO, user, images);
            return ResponseEntity.status(HttpStatus.CREATED).body(immobiles);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteImob(@RequestBody DeleteImobDTO imobDel, @AuthenticationPrincipal User user){
        Long id = imobDel.id();
        try{
            immobilesService.deleteImob(id, user);
            return ResponseEntity.ok().body("O imóvel foi deletado com sucesso!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/usuario")
    public ResponseEntity imobByUserLogged(@AuthenticationPrincipal User user){
        try{
        List<ImmobileResponse> imoveisByUser = immobilesService.imobByUserLogged(user);
        return ResponseEntity.ok().body(imoveisByUser);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("${url.block.new.property}")
    public ResponseEntity statusNewProperty(){
        if(blockNewProperties){
            this.blockNewProperties = false;
            return ResponseEntity.ok().body("Recurso de novos imóveis desligado!");
        }
        this.blockNewProperties = true;
        return ResponseEntity.ok().body("Recurso de novos imóveis ativo!");
    }

}
