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

    @Column(name = "TX_TITULO")
    private String titulo;

    @Column(name = "TX_DESCRICAO")
    private String descricao;

    @Column(name = "TX_LOCALIZACAO")
    private String localizacao;

    @Column(name = "TX_ATIVIDADE")
    private String atividade;

    @Column(name = "DT_INICIO")
    private LocalDateTime dataInicio;


    @Column(name = "DT_FIM")
    private LocalDateTime dataFim;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario autor;

    @ManyToMany(mappedBy = "eventosParticipar")
    private List<Usuario> participantes;

}
