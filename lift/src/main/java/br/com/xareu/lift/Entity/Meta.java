package br.com.xareu.lift.Entity;

import br.com.xareu.lift.Enum.StatusMetaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Table(name = "TBL_META")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_META")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario autor/*Dono das metas*/;

    @Column(name = "TX_NOME", nullable = false, unique = true)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_META", nullable = false)
    private StatusMetaEnum status = StatusMetaEnum.PENDENTE;

    @Column(name = "BL_PUBLICA", nullable = false)
    private boolean publica = true;

    @Column(name = "DT_INICIO", nullable = false)
    private LocalDateTime dataInicio = LocalDateTime.now();

    @Column(name = "DT_FIM")
    private LocalDate dataFim;

}
