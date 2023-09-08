package co.edu.unisabana.recursos_humanos.db.entidad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Certificacion")
public class CertificadoDB {

    @Id
    @Column
    private int id;

    @JsonIgnore
    @JoinColumn(name = "id_empleado", referencedColumnName = "ID")
    @ManyToOne(cascade = CascadeType.ALL)
    private EmpleadoDB empleado;

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
