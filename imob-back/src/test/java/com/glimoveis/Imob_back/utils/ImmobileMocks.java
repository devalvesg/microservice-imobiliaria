package com.glimoveis.Imob_back.utils;

import com.glimoveis.Imob_back.DTOs.ImmobilesDTO;
import com.glimoveis.Imob_back.models.Address;
import com.glimoveis.Imob_back.models.Immobiles;
import com.glimoveis.Imob_back.models.Informations;
import com.glimoveis.Imob_back.models.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.glimoveis.Imob_back.utils.UserMocks.mockUserEntity;

public class ImmobileMocks {

    public static Long ID = Long.valueOf(1111);
    public static String TITLE = "tituloMock";
    public static Address ADDRESS = mockAddress();

    public static Informations INFORMATIONS = mockInfos();
    public static String DESCRIPTION = "descricaoMock";

    public static User USER = mockUserEntity();

    public static String TYPE = "VENDA";

    public static LocalDateTime DATEPUBLISH = LocalDateTime.now();

    public static List<String> IMAGES;


    public static Informations mockInfos(){
        BigDecimal value = BigDecimal.valueOf(620000);
        return new Informations(value, 2, 2, 4, 200.00, 150.00);
    }

    public static Address mockAddress(){
        return new Address("MorumbiRua", "Morumbi", "1120", "São paulo", "São Paulo");
    }

    public static ImmobilesDTO mockImmobileDTO(){
        ImmobilesDTO dto = ImmobilesDTO.builder()
                .title(TITLE)
                .address(ADDRESS)
                .type(TYPE)
                .informations(INFORMATIONS)
                .images(IMAGES)
                .description(DESCRIPTION).build();
        return dto;
    }

    public static Immobiles mockImmobileEntity(){
        Immobiles immobiles = new Immobiles(mockImmobileDTO());
        immobiles.setId(Long.valueOf(1111));
        immobiles.setUser(USER);
        return immobiles;
    }

    public static List<Immobiles> mockImmobileType(){
        List<Immobiles> listImmobiles = new ArrayList<>();

        Immobiles immobiles = mockImmobileEntity();
        immobiles.setUser(mockUserEntity());
        immobiles.setDatePublish(DATEPUBLISH);
        immobiles.setType("VENDA");
        listImmobiles.add(immobiles);

        return listImmobiles;
    }

    static public List<Immobiles> mockImmobileList(){
        Immobiles immobiles = mockImmobileEntity();
        immobiles.setId(Long.valueOf(1111));
        immobiles.setUser(mockUserEntity());
        immobiles.setDatePublish(DATEPUBLISH);
        List<Immobiles> listImmobiles = new ArrayList<>();
        listImmobiles.add(immobiles);
        return listImmobiles;
    }
}
