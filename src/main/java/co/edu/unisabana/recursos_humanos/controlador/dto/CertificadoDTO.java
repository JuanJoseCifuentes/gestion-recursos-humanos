package co.edu.unisabana.recursos_humanos.controlador.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CertificadoDTO {

    private int id;
    private int idEmpleado;
    private String tipo;
    private String descripcion;
    private LocalDateTime fechaExpedicion;
    private String entidadExpedidora;
}
