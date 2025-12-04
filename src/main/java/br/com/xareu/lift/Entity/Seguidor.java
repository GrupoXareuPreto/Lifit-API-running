package br.com.xareu.lift.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_SEGUIDOR")
@Data
public class Seguidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SEGUIDOR")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_SEGUIDOR_USUARIO", nullable = false)
    private Usuario seguidor; // Quem está seguindo

    @ManyToOne
    @JoinColumn(name = "ID_SEGUIDO_USUARIO", nullable = false)
    private Usuario seguido; // Quem está sendo seguido

    @Column(name = "DT_SEGUIU")
    private LocalDateTime dataSeguiu;

    @PrePersist
    protected void onCreate() {
        dataSeguiu = LocalDateTime.now();
    }
}
