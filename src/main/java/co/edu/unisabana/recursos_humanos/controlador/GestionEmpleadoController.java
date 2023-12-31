package co.edu.unisabana.recursos_humanos.controlador;

import co.edu.unisabana.recursos_humanos.controlador.dto.EmpleadoDTO;
import co.edu.unisabana.recursos_humanos.controlador.dto.Respuesta;
import co.edu.unisabana.recursos_humanos.logica.LogicaEmpleado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/empleado")
public class GestionEmpleadoController {
    private static final String EXITOSO = "Exitoso";
    private static final String FALLIDO = "Fallido";

    private final LogicaEmpleado logica;
    private final Logger logger = LoggerFactory.getLogger(GestionEmpleadoController.class);

    public GestionEmpleadoController(LogicaEmpleado logica) {
        this.logica = logica;
    }

    @GetMapping(path = "/buscar/todos")
    public List<EmpleadoDTO> buscarEmpleados() {
        return logica.buscarEmpleadosTodos();
    }

    @GetMapping(path = "/buscar/id")
    public List<EmpleadoDTO> buscarEmpleadoPorID(@RequestParam int id) {
         return logica.buscarEmpleadoPorID(id);
    }

    @PostMapping(path = "/crear")
    public Respuesta crearEmpleado(@RequestBody EmpleadoDTO empleadoDTO) {
        try {
            logica.crearEmpleado(empleadoDTO);
            logger.info("Se ha creado un nuevo EMPLEADO. ID: %d", empleadoDTO.getId());
            return new Respuesta(EXITOSO, "El empleado se ha guardado correctamente.");
        } catch (Exception e) {
            logger.error("Error al crear un nuevo EMPLEADO. ID: %d", empleadoDTO.getId());
            return new Respuesta(FALLIDO, "Algo ha salido mal. No se ha podido guardar el empleado.");
        }
    }

    @PutMapping(path = "/actualizar/informacion_personal")
    public Respuesta actualizarInformacionPersonalEmpleado(@RequestBody EmpleadoDTO nuevoEmpleado){
        try {
            logica.modificarInformacionPersonalEmpleado(nuevoEmpleado.getId(), nuevoEmpleado);
            logger.warn("Se ha actualizado la informacion personal de un EMPLEADO. ID: %d", nuevoEmpleado.getId());
            return new Respuesta(EXITOSO, "La informacion personal del empleado se ha actualizado correctamente.");
        } catch (Exception e) {
            logger.error("Error al actualizar la informacion personal de un EMPLEADO. ID: %d", nuevoEmpleado.getId());
            return new Respuesta(FALLIDO, "Algo ha salido mal. No se ha podido actualizar la informacion personal del empleado.");
        }
    }

    @PutMapping(path = "/actualizar/rol")
    public Respuesta actualizarRolEmpleado(@RequestParam int id, int idNuevoRol){
        try {
            logica.modificarRolEmpleado(id, idNuevoRol);
            logger.warn("Se ha actualizado el rol de un EMPLEADO. ID: %d", id, ", ID_Nuevo_Rol: %d", idNuevoRol);
            return new Respuesta(EXITOSO, "El rol del usuario ha sido actualizado correctamente.");
        } catch (Exception e) {
            logger.error("Error al actualizar el rol de un EMPLEADO. ID: %d", id, ", ID_Nuevo_Rol: %d", idNuevoRol);
            return new Respuesta(FALLIDO, "Algo ha salido mal. No se ha podido actualizar el rol empleado.");
        }
    }

    @DeleteMapping(path = "/eliminar/id")
    public Respuesta eliminarEmpleado(@RequestParam int id){
        try {
            logica.eliminarEmpleado(id);
            logger.warn("Se ha eliminado un EMPLEADO. ID: %d", id);
            return new Respuesta(EXITOSO, "El empleado se ha eliminado correctamente.");
        } catch (Exception e) {
            logger.error("Error al eliminar un EMPLEADO. ID: %d", id);
            return new Respuesta(FALLIDO, "Algo ha salido mal. No se ha podido eliminar al empleado.");
        }
    }
}
