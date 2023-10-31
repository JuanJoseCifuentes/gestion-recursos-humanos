package co.edu.unisabana.recursos_humanos.controlador.dto;

import lombok.Data;

@Data
public class Respuesta {
    private String status;
    private String mensaje;

    public Respuesta(String status, String mensaje) {
        this.status = status;
        this.mensaje = mensaje;
    }
}
