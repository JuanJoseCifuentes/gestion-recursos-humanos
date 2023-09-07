package co.edu.unisabana.recursos_humanos.controlador.dto;

import lombok.Data;

@Data
public class PerfilEmpleadoDTO {

    private int id;
    private int idEmpleado;
    private String habilidades;
    private int anosExperiencia;
}
