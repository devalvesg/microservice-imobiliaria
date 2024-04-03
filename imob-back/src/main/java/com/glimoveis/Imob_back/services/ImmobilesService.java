package com.glimoveis.Imob_back.services;

import com.glimoveis.Imob_back.DTOs.ImmobilesDTO;
import com.glimoveis.Imob_back.models.Immobiles;
import com.glimoveis.Imob_back.models.User;
import com.glimoveis.Imob_back.exceptions.ImmobilesException;
import com.glimoveis.Imob_back.repositories.AddressRepository;
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
    private AddressRepository addressRepository;

    public ImmobilesService(ImmobileRepository immobileRepository,
                            InformationsRepository informationsRepository,
                            UserRepository userRepository,
                            AddressRepository addressRepository

    ){
        this.immobileRepository = immobileRepository;
        this.informationsRepository = informationsRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
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

    public Immobiles newProperty(ImmobilesDTO immobilesDTO, User user) throws Exception {
        Immobiles immobiles = new Immobiles(immobilesDTO);
        immobiles.setDatePublish(LocalDateTime.now());

        if(userRepository.findById(user.getId()) == null) throw new Exception("Você precisa fazer login para registrar um novo imóvel");
        immobiles.setUser(user);
        return immobileRepository.save(immobiles);
    }


    public void deleteImob(Long id, User user) throws Exception {

        Immobiles immobiles = immobileRepository.findById(id).orElseThrow(ImmobilesException::new);

        if(!user.getId().equals(immobiles.getUser().getId())) throw new Exception("Você não tem permissão para alterar esse imóvel");

        immobileRepository.deleteById(id);
    }


    public List<Immobiles> imobByUserLogged(User user) throws Exception {
        if(user == null) throw new Exception("Você precisa estar logado para ver os seus imóveis");
        List<Immobiles> imobUser = immobileRepository.findByUserId(user.getId());
        return imobUser;
    }
}
