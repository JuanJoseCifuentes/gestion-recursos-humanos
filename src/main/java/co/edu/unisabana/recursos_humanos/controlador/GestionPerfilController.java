package co.edu.unisabana.recursos_humanos.controlador;

import co.edu.unisabana.recursos_humanos.controlador.dto.PerfilEmpleadoDTO;
import co.edu.unisabana.recursos_humanos.controlador.dto.Respuesta;
import co.edu.unisabana.recursos_humanos.logica.LogicaPerfil;
import org.springframework.web.bind.annotation.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/perfil")
public class GestionPerfilController {
    private static final String EXITOSO = "Exitoso";
    private static final String FALLIDO = "Fallido";

    private final LogicaPerfil logica;

    private final Logger logger = LoggerFactory.getLogger(GestionPerfilController.class);
    
    private static final String IDPERFIL = ". ID: %d";

    public GestionPerfilController(LogicaPerfil logica) {
        this.logica = logica;
    }

    @GetMapping(path = "/buscar/todos")
    public List<PerfilEmpleadoDTO> buscarPerfiles() {
        return logica.buscarPerfilTodos();
    }

    @GetMapping(path = "/buscar/id")
    public List<PerfilEmpleadoDTO> buscarPerfilPorID(@RequestParam int id) {
        return logica.buscarPerfilPorID(id);
    }



    @PostMapping(path = "/subir")
    public Respuesta subirPerfil(@RequestBody PerfilEmpleadoDTO perfil) {
        try {
            logger.info("Se ha creado un nuevo perfil para el empleado %d", perfil.getIdEmpleado(), IDPERFIL, perfil.getId());
            logica.crearPerfil(perfil);
            return new Respuesta(EXITOSO,"El perfil se ha creado correctamente.");
        } catch (Exception e) {
            logger.error("Error al crear el perfil del empleado %d", perfil.getIdEmpleado(), IDPERFIL, perfil.getId());
            return new Respuesta(FALLIDO, "Algo ha salido mal. No se ha podido crear el perfil");
        }
    }

    @PutMapping(path = "/actualizar")
    public Respuesta actualizarPerfil (@RequestBody PerfilEmpleadoDTO perfilNuevo){
        try {
            logger.warn("Se ha Actualizado un nuevo perfil para el empleado %d", perfilNuevo.getIdEmpleado(), IDPERFIL, perfilNuevo.getId());
            logica.actualizarPerfil(perfilNuevo.getId(), perfilNuevo);
            return new Respuesta(EXITOSO, "El perfil ha sido actualizado correctamente.");
        } catch (Exception e){
            logger.error("Error al actualizar el perfil del empleado %d", perfilNuevo.getIdEmpleado(), IDPERFIL, perfilNuevo.getId());
            return new Respuesta(FALLIDO,"Algo ha salido mal, no se ha podido actualizar el perfil.");
        }
    }

    @DeleteMapping (path = "/eliminar")
    public Respuesta eliminarPerfil (@RequestParam int id){
        try {
            logger.info("Se ha Eliminado el perfil. ID: %d", id);
            logica.eliminarPerfil(id);
            return new Respuesta(EXITOSO, "El perfil ha sido eliminado correctamente.");
        } catch (Exception e){
            logger.error("Error al eliminar el perfil. ID: %d", id);
            return new Respuesta(FALLIDO,"Algo ha salido mal, no se ha podido eliminar el perfil.");
        }

    }
}
