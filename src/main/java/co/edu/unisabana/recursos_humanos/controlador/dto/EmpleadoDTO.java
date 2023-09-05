package co.edu.unisabana.recursos_humanos.controlador.dto;

import lombok.Data;

@Data
public class EmpleadoDTO {

    private int id;
    private String nombre;
    private int edad;
    private String correo;
    private int telefono;
    private int idRol;
    private String direccion;
    private int cedula;
}
