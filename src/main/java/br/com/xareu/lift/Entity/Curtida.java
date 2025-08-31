package br.com.xareu.lift.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TBL_CURTIDA")
@Data
public class Curtida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CURTIDA")
    private Long id;

    /*private ... id_postagem*/
    @ManyToOne
    @JoinColumn(name = "ID_POSTAGEM", nullable = false)
    private Postagem postagem;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario autor;

}
