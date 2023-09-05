package co.edu.unisabana.recursos_humanos.controlador;

import co.edu.unisabana.recursos_humanos.controlador.dto.Respuesta;
import co.edu.unisabana.recursos_humanos.db.PerfilEmpleadoDB;
import co.edu.unisabana.recursos_humanos.logica.LogicaPerfil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GestionPerfilController {

    public LogicaPerfil logica;

    public GestionPerfilController(LogicaPerfil logica) {
        this.logica = logica;
    }

    @PostMapping(path = "/perfil/subir")
    public Respuesta subirRol(@RequestBody PerfilEmpleadoDB perfil) {
        try {
            logica.crearPerfil(perfil);
            return new Respuesta("Exitoso","El perfil se ha creado correctamente.");
        } catch (Exception e) {
            return new Respuesta("Fallido", "Algo ha salido mal. No se ha podido crear el perfil");
        }
    }
}
