package co.edu.unisabana.recursos_humanos.db.entidad;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Empleado")
public class EmpleadoDB {
    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Edad")
    private int edad;

    @Column(name = "Correo")
    private String correo;

    @Column(name = "Telefono")
    private int telefono;

    @Column(name = "ID_rol")
    private int idRol;

    @Column(name = "Direccion")
    private String direccion;

    @Column(name = "Cedula")
    private int cedula;

    @Column(name = "Fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "Fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

}
