package br.com.xareu.lift.Entity;

import br.com.xareu.lift.Enum.StatusMetaEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Table(name = "TBL_META")
@Entity
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_META")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    @JsonBackReference
    private Usuario autor/*Dono das metas*/;

    @Column(name = "TX_NOME", nullable = false)
    private String nome;

    @Column(name = "TX_DESCRICAO")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_META", nullable = false)
    private StatusMetaEnum status = StatusMetaEnum.PENDENTE;

}
