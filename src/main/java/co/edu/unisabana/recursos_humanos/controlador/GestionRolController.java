package co.edu.unisabana.recursos_humanos.controlador;

import co.edu.unisabana.recursos_humanos.controlador.dto.Respuesta;
import co.edu.unisabana.recursos_humanos.controlador.dto.RolDTO;
import co.edu.unisabana.recursos_humanos.logica.LogicaRol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rol")
public class GestionRolController {
    private static final String EXITOSO = "Exitoso";
    private static final String FALLIDO = "Fallido";

    private final LogicaRol logica;

    private final Logger logger = LoggerFactory.getLogger(GestionRolController.class);

    public GestionRolController(LogicaRol logica) {
        this.logica = logica;
    }

    @PostMapping(path = "/crear")
    public Respuesta crearRol(@RequestBody RolDTO rol) {
        try {
            logica.crearRol(rol);
            logger.info("El rol ha sido creado exitosamente");
            return new Respuesta(EXITOSO,"El rol se ha creado correctamente.");
        } catch (Exception e) {
            logger.error("Algo ha salido mal, no se ha creado el rol");
            return new Respuesta(FALLIDO, "Algo ha salido mal. No se ha podido crear el rol");
        }
    }

    @GetMapping (path = "/buscar/id")
    public List<RolDTO> buscarRolPorID(@RequestParam int id){
        return logica.buscarRolPorID(id);
    }

    @GetMapping (path = "/buscar/todos")
    public List<RolDTO> buscarRoles(){
        return logica.buscarRolTodos();
    }

    @PutMapping(path = "/actualizar")
    public Respuesta actualizarRol(@RequestBody RolDTO nuevoRol){
        try {
            logica.actualizarRol(nuevoRol.getId(), nuevoRol);
            logger.info("El rol ha sido actualizado exitosamente");
            return new Respuesta(EXITOSO, "El rol ha sido actualizado correctamente.");
        } catch (Exception e) {
            logger.error("Algo ha salido mal, no se ha actualizado el certificado");
            return new Respuesta(FALLIDO, "Algo ha salido mal. No se ha podido actualizar el rol.");
        }
    }

    @DeleteMapping(path = "/eliminar/id")
    public Respuesta eliminarRol(@RequestParam int id){
        try {
            logica.eliminarRol(id);
            logger.info("El rol ha sido eliminado exitosamente");
            return new Respuesta(EXITOSO, "El rol ha sido eliminado correctamente.");
        } catch (Exception e) {
            logger.error("Algo ha salido mal, no se ha eliminado el rol");
            return new Respuesta(FALLIDO, "Algo ha salido mal. No se ha podido eliminar el rol.");
        }
    }
}
