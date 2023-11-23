package co.edu.unisabana.recursos_humanos.db.entidad;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Perfil")
public class PerfilEmpleadoDB {

    @Id
    @Column
    private int id;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_empleado", referencedColumnName = "ID")
    private EmpleadoDB empleado;

    @Column
    private String habilidades;

    @Column
    private int anosExperiencia;

    @Column(name = "Fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "Fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
}
