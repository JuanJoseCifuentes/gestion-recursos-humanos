package co.edu.unisabana.recursos_humanos.controlador.dto;

import lombok.Data;

@Data
public class CetificadoDTO {

    private int id;
    private int idEmpleado;
    private String tipo;
    private String descripcion;
    private String entidadExpedidora;
}
