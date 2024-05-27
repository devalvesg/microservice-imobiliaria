package com.glimoveis.Imob_back.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glimoveis.Imob_back.DTOs.ImmobilesDTO;
import com.glimoveis.Imob_back.DTOs.Responses.ImmobileResponse;
import com.glimoveis.Imob_back.exceptions.BlockPropertiesException;
import com.glimoveis.Imob_back.exceptions.DeleteImmobileException;
import com.glimoveis.Imob_back.exceptions.ImmobilesNotFoundException;
import com.glimoveis.Imob_back.exceptions.LoginException;
import com.glimoveis.Imob_back.models.Immobiles;
import com.glimoveis.Imob_back.models.User;
import com.glimoveis.Imob_back.repositories.AddressRepository;
import com.glimoveis.Imob_back.repositories.ImmobileRepository;
import com.glimoveis.Imob_back.repositories.InformationsRepository;
import com.glimoveis.Imob_back.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImmobilesService {
    private ImmobileRepository immobileRepository;
    private InformationsRepository informationsRepository;
    private UserRepository userRepository;
    private AddressRepository addressRepository;

    private boolean blockNewProperties;
    private S3Service s3Service;

    public ImmobilesService(ImmobileRepository immobileRepository,
                            InformationsRepository informationsRepository,
                            UserRepository userRepository,
                            AddressRepository addressRepository,
                            S3Service s3Service
    ){
        this.immobileRepository = immobileRepository;
        this.informationsRepository = informationsRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.s3Service = s3Service;
    }
    public ImmobileResponse findById(Long id){
        //Busca o imóvel na base de dados
        Immobiles immobiles = immobileRepository.findById(id).orElseThrow(ImmobilesNotFoundException::new);

        //Envia o imovel para gerar a presigned url das imagens
        List<String> presignedUrlImages = s3Service.generateUrlImages(immobiles);

        //Cria o objeto de resposta com as urls presignada das imagens
        ImmobileResponse immobileResponse = new ImmobileResponse(immobiles);
        immobileResponse.setImages(presignedUrlImages);
        immobileResponse.setUserId(immobiles.getUser().getId());

        return immobileResponse;
    }


    public List<ImmobileResponse> getAll(){
        List<Immobiles> immobiles = immobileRepository.findAll();
        List<ImmobileResponse> imobWithPresignedUrls = generatedListImobsWithUrls(immobiles);
        return imobWithPresignedUrls;
    }

    public List<ImmobileResponse> findByType(String type){
        type = type.toLowerCase();
        type = type.substring(0, 1).toUpperCase() + type.substring(1);

        List<Immobiles> immobiles = immobileRepository.findByType(type);
        List<ImmobileResponse> imobWithPresignedUrls = generatedListImobsWithUrls(immobiles);
        return imobWithPresignedUrls;
    }


    public Immobiles newProperty(String immobileText, User user, List<MultipartFile> images) throws Exception {

        //Validações para criar um novo imóvel
        if(blockNewProperties){
            throw new BlockPropertiesException();
        }
        if(user == null){
            throw new LoginException();
        }

        //Lógica de criação de imóvel
        ObjectMapper obj = new ObjectMapper();
        ImmobilesDTO immobilesDTO = obj.readValue(immobileText, ImmobilesDTO.class);

        List<String> imagesPath = s3Service.saveImagesToBucket(images, user.getId());

        Immobiles immobiles = new Immobiles(immobilesDTO);
        immobiles.setImages(imagesPath);

        immobiles.setDatePublish(LocalDateTime.now());

        immobiles.setUser(user);
        return immobileRepository.save(immobiles);
    }


    public void deleteImob(Long id, User user) throws Exception {
        Immobiles immobiles = immobileRepository.findById(id).orElseThrow(ImmobilesNotFoundException::new);
        if(user == null){
            throw new LoginException();
        }
        if(!user.getId().equals(immobiles.getUser().getId())) {
            throw new DeleteImmobileException();
        }
        immobileRepository.deleteById(id);
    }

    public List<ImmobileResponse> imobByUserLogged(User user) throws Exception {
        if(user == null) throw new LoginException();

        List<Immobiles> immobiles = immobileRepository.findByUserId(user.getId());

        List<ImmobileResponse> imobWithPresignedUrls = generatedListImobsWithUrls(immobiles);
        return imobWithPresignedUrls;
    }

    //Método que trata a lista de imóveis com as urls presigned para devolver ao método responsável pela ação
    public List<ImmobileResponse> generatedListImobsWithUrls(List<Immobiles> immobiles){
        List<ImmobileResponse> imobsResponse = new ArrayList<>();
        for (Immobiles imob : immobiles){
            ImmobileResponse immobileResponse = new ImmobileResponse(imob);
            List<String> presignedUrls = s3Service.generateUrlImages(imob);
            immobileResponse.setImages(presignedUrls);
            immobileResponse.setUserId(imob.getUser().getId());
            imobsResponse.add(immobileResponse);
        }
        return imobsResponse;
    }

    public String statusNewProperty() {
        if(blockNewProperties){
            this.blockNewProperties = false;
            return "Recurso de bloqueio de novos imóveis desligado!";
        }
        this.blockNewProperties = true;
        return "Recurso de bloqueio de novos imóveis ativo!";
    }
}
