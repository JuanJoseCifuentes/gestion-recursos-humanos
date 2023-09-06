package co.edu.unisabana.recursos_humanos.db.entidad;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Certificaciones")
public class CertificadoDB {

    @Id
    @Column
    private int id;

    @JoinColumn(name = "ID_empleado")
    @OneToOne
    private EmpleadoDB idEmpleado;

    @Column
    private String tipo;

    @Column
    private String descripcion;

    @Column
    private LocalDateTime fechaExpedicion;

    @Column
    private String entidadExpedidora;

    @Column(name = "Fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "Fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
}
