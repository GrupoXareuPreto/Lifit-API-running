package br.com.xareu.lift.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_COMENTARIO")
@Data
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COMENTARIO")
    private Long id;

    @Column(name = "TX_CONTEUDO")
    private String conteudo;

    @Column(name = "DT_COMENTARIO")
    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "ID_POSTAGEM", nullable = false)
    private Postagem postagem;

}
