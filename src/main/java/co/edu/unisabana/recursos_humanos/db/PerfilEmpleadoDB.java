package co.edu.unisabana.recursos_humanos.db;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Perfil")
public class PerfilEmpleadoDB {

    @Id
    @Column
    private int id;

    @Column
    private int idEmpleado;

    @Column
    private String habilidades;

    @Column
    private int anosExperiencia;

    @Column(name = "Fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "Fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
}
