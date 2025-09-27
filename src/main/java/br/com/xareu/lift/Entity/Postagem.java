package br.com.xareu.lift.Entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "TBL_POSTAGEM")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Postagem {

    @Id
    @Column(name = "ID_POSTAGEM")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TX_MIDIA", nullable = false)
    private String midia;

    @Column(name = "TX_TITULO", nullable = false)
    private String titulo;

    @Column(name = "TX_DESCRICAO")
    private String descricao;

    @Column(name = "NM_COMPARTILHAMENTOS")
    private int compartilhamentos;

    @Column(name = "DT_PUBLICACAO", nullable = false)
    private LocalDateTime dataPublicacao = LocalDateTime.now();

    @OneToMany(mappedBy = "postagem")
    private List<Curtida> curtidas;

    @OneToMany(mappedBy = "postagem")
    private List<Comentario> comentarios;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario autor;

    @OneToOne
    private Mensagem mensagem;
}
