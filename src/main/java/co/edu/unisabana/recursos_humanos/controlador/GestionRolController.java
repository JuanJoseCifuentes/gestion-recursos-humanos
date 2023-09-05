package co.edu.unisabana.recursos_humanos.controlador;

import co.edu.unisabana.recursos_humanos.controlador.dto.Respuesta;
import co.edu.unisabana.recursos_humanos.db.RolDB;
import co.edu.unisabana.recursos_humanos.logica.LogicaRol;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GestionRolController {

    public LogicaRol logica;

    public GestionRolController(LogicaRol logica) {
        this.logica = logica;
    }

    @PostMapping(path = "/rol/subir")
    public Respuesta subirRol(@RequestBody RolDB rol) {
        try {
            logica.crearRol(rol);
            return new Respuesta("Exitoso","El rol se ha creado correctamente.");
        } catch (Exception e) {
            return new Respuesta("Fallido", "Algo ha salido mal. No se ha podido crear el rol");
        }
    }
}
