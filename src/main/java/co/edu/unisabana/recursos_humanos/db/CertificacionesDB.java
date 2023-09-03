package co.edu.unisabana.recursos_humanos.db;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Certificaciones")
public class CertificacionesDB {

    @Id
    @Column
    private int id;

    @Column
    private int id_emlpeado;

    @Column
    private String tipo;

    @Column
    private String descripcion;

    @Column
    private LocalDateTime fecha_expedicion;

    @Column
    private String entidad_expedidora;

    @Column(name = "Fecha_creacion")
    private LocalDateTime fecha_creacion;

    @Column(name = "Fecha_actualizacion")
    private LocalDateTime fecha_actualizacion;
}
