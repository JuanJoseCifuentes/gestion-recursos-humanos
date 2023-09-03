package co.edu.unisabana.recursos_humanos.db;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="Rol")
public class RolDB {

    @Id
    @Column
    private int id;

    @Column
    private String responsabilidades;

    @Column(name = "Fecha_creacion")
    private LocalDateTime fecha_creacion;

    @Column(name = "Fecha_actualizacion")
    private LocalDateTime fecha_actualizacion;
}
