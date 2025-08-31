package br.com.xareu.lift.Entity;

import br.com.xareu.lift.Enum.StatusMensagemEnum;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_MENSAGEM")
@Data
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name  = "ID_MENSAGEM")
    private Long  id;

    @Column(name = "DT_ENVIO")
    private LocalDateTime dataEnvio;

    @Column(name = "STATUS_MENSAGEM")
    @Enumerated(EnumType.STRING)
    private StatusMensagemEnum status;

    @ManyToOne
    @JoinColumn(name = "ID_CONVERSA", nullable = false)
    private Conversa conversa;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario autor;

    @OneToOne(mappedBy = "mensagem", cascade = CascadeType.ALL)
    private Compartilhamento compartilhamento;

}
