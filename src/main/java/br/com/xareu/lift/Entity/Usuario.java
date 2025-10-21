package br.com.xareu.lift.Entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "TBL_USUARIO")
@Data

public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO", nullable = false)
    private Long id;

    @Column(name = "TX_NOME", nullable = false)
    private String nome;

    @Column(name = "TX_BIOGRAFIA")
    private String biografia;

    @Column(name = "TX_EMAIL", unique = true, nullable = false)
    private String email;

    /*Ver como funciona o hash e o salt*/
    @Column(name = "TX_SENHA", nullable = false)
    private String senha;

    @Column(name = "TX_NOMEUSUARIO" , nullable = false, unique = true)
    private String nomeUsuario;

    @Column(name = "TX_FOTO_PERFIL")
    private String fotoPerfil;

    /*Ver como isso vai funcionar*/
    /*statusUsuario -------> online | Offline */

// =========================================================================================
// Ver com a Marion

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Meta> metas;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Postagem> postagens;

    @OneToMany(mappedBy = "autor",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Curtida> curtidas;

    @OneToMany(mappedBy = "autor",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios;

    @OneToMany(mappedBy = "autor",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evento> eventos;

    @OneToMany(mappedBy = "autor",   cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mensagem> mensagens;

    @ManyToMany
    @JoinTable(
            name = "TBL_USUARIO_EVENTO",
            joinColumns = @JoinColumn(name = "ID_USUARIO"),
            inverseJoinColumns = @JoinColumn(name = "ID_EVENTO")
    )
    private List<Evento> eventosParticipar;

    @ManyToMany
    @JoinTable(
            name = "TBL_SEGUIDORES",
            joinColumns = @JoinColumn(name = "ID_USUARIO"),
            inverseJoinColumns = @JoinColumn(name = "ID_SEGUIDOR")
    )
    private List<Usuario> seguidores;

    @ManyToMany
    @JoinTable(
            name = "TBL_SEGUINDO",
            joinColumns = @JoinColumn(name = "ID_USUARIO"),
            inverseJoinColumns = @JoinColumn(name = "ID_SEGUIDO")
    )
    private List<Usuario> seguindo;

    @ManyToMany
    @JoinTable(
            name = "TBL_USUARIO_CONVERSA",
            joinColumns = @JoinColumn(name = "ID_USUARIO"),
            inverseJoinColumns = @JoinColumn(name = "ID_CONVERSA")
    )
    private List<Conversa> conversas;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Para este TCC, se você não tem papéis (roles) como ADMIN, USER,
        // pode retornar uma lista vazia.
        return List.of();
    }

    @Override
    public String getPassword() {
        // Retorna a senha (hashada) do seu usuário.
        return this.senha;
    }

    @Override
    public String getUsername() {
        // Retorna o identificador único que será usado para o login.
        // O email é uma ótima escolha.
        return this.email;
    }

    // Os métodos abaixo controlam o status da conta.
    // Para simplificar, vamos retornar 'true' para todos.
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
