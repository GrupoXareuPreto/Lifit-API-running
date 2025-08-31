package br.com.xareu.lift.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TBL_COMPARTILHAMENTO")
@Data
public class Compartilhamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COMPARTILHAMENTO")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "ID_POSTAGEM", nullable = false)
    private Postagem postagem;

    @OneToOne
    @JoinColumn(name = "MENSAGEM", nullable = false)
    private Mensagem mensagem;

}
