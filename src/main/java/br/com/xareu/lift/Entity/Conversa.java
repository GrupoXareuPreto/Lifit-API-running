package br.com.xareu.lift.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "TBL_CONVERSA")
@Data
public class Conversa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONVERSA")
    private Long id;

    @Column(name = "TX_Foto")
    private String foto;

    @Column(name = "TX_DESCRICAO")
    private String descricao;

    @OneToMany(mappedBy = "conversa")
    private List<Mensagem> mensagens;

    @ManyToMany(mappedBy = "conversas")
    private List<Usuario> integrantes;


}
