package com.glimoveis.Imob_back.services;

import com.glimoveis.Imob_back.models.Immobiles;
import com.glimoveis.Imob_back.models.User;
import com.glimoveis.Imob_back.exceptions.ImmobilesException;
import com.glimoveis.Imob_back.repositories.AdressRepository;
import com.glimoveis.Imob_back.repositories.ImmobileRepository;
import com.glimoveis.Imob_back.repositories.InformationsRepository;
import com.glimoveis.Imob_back.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public Immobiles findById(Long id){
        Immobiles immobiles = immobileRepository.findById(id).orElseThrow(ImmobilesException::new);
        return immobiles;
    }

    public void newImmobile(Immobiles immobiles, User user) throws Exception {
        immobiles.setDatePublish(LocalDateTime.now());

        if(userRepository.findById(user.getId()) == null) throw new Exception("Você precisa fazer login para registrar um novo imóvel");
        immobiles.setUser(user);
        immobileRepository.save(immobiles);
    }


    public void deleteImob(Long id, User user) throws Exception {

        Optional<Immobiles> optionalImob = immobileRepository.findById(id);
        if (!optionalImob.isPresent()) throw new Exception("Imóvel não encontrado");
        Immobiles delImob = optionalImob.get();

        if(!user.getId().equals(delImob.getUser().getId())) throw new Exception("Você não tem permissão para alterar esse imóvel");

        immobileRepository.deleteById(id);
    }


    public List<Immobiles> imobByUserLogged(String id) {
        List<Immobiles> imobUser = immobileRepository.findByuserId(id);
        return imobUser;
    }
}
