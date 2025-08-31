package br.com.xareu.lift.Entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "TBL_USUARIO")
@Data
public class Usuario {

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


// =========================================================================================
// Ver com a Marion

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Meta> metas;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Postagem> postagens;

    @OneToMany(mappedBy = "autor",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Curtida> curtidas;

    @OneToMany(mappedBy = "autor",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios;

    @OneToMany(mappedBy = "autor",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evento> eventosCriados;

    @OneToMany(mappedBy = "autor",   cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mensagem> mensagens;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Compartilhamento> compartilhamentos;

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

}
