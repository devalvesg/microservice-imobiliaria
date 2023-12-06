package com.glimoveis.Imob_back.services;

import com.glimoveis.Imob_back.entities.Immobiles;
import com.glimoveis.Imob_back.exceptions.ImmobilesException;
import com.glimoveis.Imob_back.repositories.ImmobileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImmobilesService {
    private ImmobileRepository immobileRepository;
    public ImmobilesService(ImmobileRepository immobileRepository){
        this.immobileRepository = immobileRepository;
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

}
