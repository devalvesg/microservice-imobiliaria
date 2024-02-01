package com.glimoveis.Imob_back.models;

import com.glimoveis.Imob_back.DTOs.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "tb_users")
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "O campo nome não pode ser em branco")
    private String name;

    @NotBlank(message = "O campo email não pode ser em branco")
    private String email;

    @NotBlank(message = "O campo senha não pode ser em branco")
    private String password;

    @NotNull(message = "O campo cpf não pode ser em branco")
    private String cpf;

    @NotBlank(message = "O campo telefone não pode ser em branco")
    private String phone;

    @OneToMany(mappedBy = "user")
    private List<Immobiles> immobiles;

    public User(String name, String email, String password, String cpf, String phone){
        this.name = name;
        this.password = password;
        this.email = email;
        this.cpf = cpf;
        this.phone = phone;
    }

    public User(UserDTO userDTO){
        this.name = userDTO.name();
        this.email = userDTO.email();
        this.cpf = userDTO.cpf();
        this.phone = userDTO.phone();
        this.password = userDTO.password();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
