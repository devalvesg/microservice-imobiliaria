package com.glimoveis.Imob_back.services;

import com.glimoveis.Imob_back.exceptions.ImmobilesException;
import com.glimoveis.Imob_back.models.Immobiles;
import com.glimoveis.Imob_back.models.User;
import com.glimoveis.Imob_back.repositories.ImmobileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.glimoveis.Imob_back.utils.ImmobileMocks.*;
import static com.glimoveis.Imob_back.utils.UserMocks.mockUserDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImmobilesServiceTest {

    @Mock
    ImmobileRepository immobileRepository;

    @InjectMocks
    ImmobilesService immobilesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return a Immobiles List when calling method get all")
    void getAll() {
        when(immobileRepository.findAll()).thenReturn(mockImmobileList());

        List<Immobiles> immobilesList = this.immobilesService.getAll();

        assertNotNull(immobilesList);
        assertEquals(immobilesList.get(0).getTitle(), TITLE);
        assertEquals(immobilesList.get(0).getDescription(), DESCRIPTION);
        assertEquals(immobilesList.get(0).getType(), TYPE);
        assertEquals(immobilesList.get(0).getAddress(), ADDRESS);
        assertEquals(immobilesList.get(0).getInformations(), INFORMATIONS);
        assertEquals(immobilesList.get(0).getDatePublish(), DATEPUBLISH);
        assertEquals(immobilesList.get(0).getUser(), USER);
    }

    @Test
    @DisplayName("Should return Immobiles List where type to the imobille is equals to the solicited by user")
    void findByType() {
        String type = "VENDA";
        when(this.immobileRepository.findByType(type)).thenReturn(mockImmobileType());

        List<Immobiles> immobilesList = this.immobilesService.findByType(type);
        assertNotNull(immobilesList);
        System.out.println(immobilesList);

        assertEquals(immobilesList.get(0).getType(), type);

    }

    @Test
    @DisplayName("Should return the immobile where id to the imobille is equals to the solicited by user")
    void findById() {
        when(this.immobileRepository.findById(Long.valueOf(1111))).thenReturn(Optional.of(mockImmobileEntity()));

        Immobiles immobiles = this.immobilesService.findById(Long.valueOf(1111));

        assertNotNull(immobiles);
        assertEquals(immobiles.getId(), ID);
    }

    @Test
    @DisplayName("An exception is expected to be thrown because the property was not found")
    void findByIdError() {
        when(this.immobileRepository.findById(Long.valueOf(1111))).thenReturn(Optional.empty());

        assertThrows(ImmobilesException.class, () -> {
            Immobiles immobiles = this.immobilesService.findById(Long.valueOf(1111));
        });
    }

    @Test
    @DisplayName("Should delete the immobile solicited by user")
    void deleteImob() throws Exception {
        when(this.immobileRepository.findById(ID)).thenReturn(Optional.of(mockImmobileEntity()));

        this.immobilesService.deleteImob(ID, USER);

        Mockito.verify(immobileRepository, times(1)).deleteById(ID);
    }

    @Test
    @DisplayName("An exception should be thrown when the user is not responsible for the property")
    void deleteImobErrorUser() {
        when(this.immobileRepository.findById(ID)).thenReturn(Optional.of(mockImmobileEntity()));
        User user = new User(mockUserDTO());
        user.setId("ID-ALEATORIO");
        assertThrows(Exception.class, () -> {
            this.immobilesService.deleteImob(ID, user);
        });
    }

    @Test
    @DisplayName("Should return immobiles of the user logged")
    void imobByUserLogged() throws Exception {
        when(this.immobileRepository.findByUserId(USER.getId())).thenReturn(mockImmobileList());

        List<Immobiles> immobiles = this.immobilesService.imobByUserLogged(USER);
        assertNotNull(immobiles);
        assertEquals(immobiles.get(0).getTitle(), TITLE);
        assertEquals(immobiles.get(0).getDescription(), DESCRIPTION);
        assertEquals(immobiles.get(0).getType(), TYPE);
        assertEquals(immobiles.get(0).getAddress(), ADDRESS);
        assertEquals(immobiles.get(0).getInformations(), INFORMATIONS);
        assertEquals(immobiles.get(0).getDatePublish(), DATEPUBLISH);
        assertEquals(immobiles.get(0).getUser(), USER);
    }

    @Test
    @DisplayName("An exception should be thrown when the user don't be logged")
    void imobByUserLoggedError() throws Exception {
        when(this.immobileRepository.findByUserId(USER.getId())).thenReturn(mockImmobileList());

        assertThrows(Exception.class, () ->{
            List<Immobiles> immobiles = this.immobilesService.imobByUserLogged(null);
        });


    }
}