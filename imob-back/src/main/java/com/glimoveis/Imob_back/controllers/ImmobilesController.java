package com.glimoveis.Imob_back.controllers;

import com.glimoveis.Imob_back.DTOs.Responses.ImmobileResponse;
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


    public ImmobilesController (ImmobilesService immobilesService){
        this.immobilesService = immobilesService;
    }

    @GetMapping()
    public ResponseEntity<List<ImmobileResponse>> findAll(){
        return ResponseEntity.ok(immobilesService.getAll());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<ImmobileResponse>> findByType(@PathVariable(name = "type")String type){
        return ResponseEntity.ok(immobilesService.findByType(type));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable(name = "id")Long id){
        return ResponseEntity.ok(immobilesService.findById(id));
    }

    @PostMapping(value = "/new-property", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity newProperty( @RequestParam(value = "immobileJson", required = false) String immobileText,
                                       @AuthenticationPrincipal User user,
                                       @RequestParam("file") List<MultipartFile> images) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(immobilesService.newProperty(immobileText, user, images));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteImob(@PathVariable Long id, @AuthenticationPrincipal User user) throws Exception {
            immobilesService.deleteImob(id, user);
            return ResponseEntity.ok().body("O imóvel foi deletado com sucesso!");
    }

    @GetMapping("/usuario")
    public ResponseEntity imobByUserLogged(@AuthenticationPrincipal User user) throws Exception {
        return ResponseEntity.ok().body(immobilesService.imobByUserLogged(user));
    }

    @GetMapping("${url.block.new.property}")
    public ResponseEntity statusNewProperty(){
         return ResponseEntity.ok().body(immobilesService.statusNewProperty());
    }

}
