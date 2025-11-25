package br.com.xareu.lift.Entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TBL_EVENTO")
@Data
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EVENTO")
    private Long id;

    @Column(name = "TX_TITULO", nullable = false)
    private String titulo;

    @Column(name = "IMG", nullable = false)
    private String midia;

    @Column(name = "TX_DESCRICAO")
    private String descricao;

    @Column(name = "TX_LOCALIZACAO",nullable = false)
    private String localizacao;

    @Column(name = "DT_INICIO" , nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "DT_FIM")
    private LocalDateTime dataFim;

    @Column(name = "DT_CRIACAO", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario autor;

    @OneToMany
    private List<Curtida> curtidas;

    @OneToMany
    private List<Comentario> comentarios;

    @Column(name = "NM_COMPARTILHAMENTOS")
    private int compartilhamentos = 0;

    @ManyToMany(mappedBy = "eventosParticipar")
    private List<Usuario> participantes;



}
