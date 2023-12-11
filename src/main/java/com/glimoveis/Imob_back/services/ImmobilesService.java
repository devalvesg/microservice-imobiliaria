package com.glimoveis.Imob_back.services;

import com.glimoveis.Imob_back.entities.Adress;
import com.glimoveis.Imob_back.entities.Immobiles;
import com.glimoveis.Imob_back.entities.Informations;
import com.glimoveis.Imob_back.entities.User;
import com.glimoveis.Imob_back.exceptions.ImmobilesException;
import com.glimoveis.Imob_back.repositories.AdressRepository;
import com.glimoveis.Imob_back.repositories.ImmobileRepository;
import com.glimoveis.Imob_back.repositories.InformationsRepository;
import com.glimoveis.Imob_back.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ImmobilesService {
    private ImmobileRepository immobileRepository;
    private InformationsRepository informationsRepository;
    private UserRepository userRepository;
    private AdressRepository adressRepository;

    public ImmobilesService(ImmobileRepository immobileRepository,
                            InformationsRepository informationsRepository,
                            UserRepository userRepository,
                            AdressRepository adressRepository

    ){
        this.immobileRepository = immobileRepository;
        this.informationsRepository = informationsRepository;
        this.userRepository = userRepository;
        this.adressRepository = adressRepository;
    }

    public List<Immobiles> getAll(){
        List<Immobiles> immobiles = immobileRepository.findAll();
        return immobiles;
    }

    public List<Immobiles> findByType(String type){
        List<Immobiles> immobiles = immobileRepository.findByType(type);
        return immobiles;
    }
    private Boolean hasId;
    public Immobiles findById(Long id){
        Immobiles immobiles = immobileRepository.findById(id).orElseThrow(ImmobilesException::new);
        return immobiles;
    }

    public void newImmobile(Immobiles immobiles){

        immobiles.setDatePublish(LocalDateTime.now());
        immobileRepository.save(immobiles);

    }
}
