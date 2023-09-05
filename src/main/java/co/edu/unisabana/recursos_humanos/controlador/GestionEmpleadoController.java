package co.edu.unisabana.recursos_humanos.controlador;

import co.edu.unisabana.recursos_humanos.controlador.dto.Respuesta;
import co.edu.unisabana.recursos_humanos.db.EmpleadoDB;
import co.edu.unisabana.recursos_humanos.logica.LogicaEmpleado;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GestionEmpleadoController {

    public LogicaEmpleado logica;

    public GestionEmpleadoController(LogicaEmpleado logica) {
        this.logica = logica;
    }

    @GetMapping(path = "/empleado/buscar/todos")
    public List<EmpleadoDB> buscarEmpleados() {
        return logica.buscarEmpleadosTodos();
    }

    @GetMapping(path = "/empleado/buscar/id")
    public String buscarEmpleadoPorID(@RequestParam int id) {
         return logica.buscarEmpleadoPorID(id);
    }

    @PostMapping(path = "/empleado/crear")
    public Respuesta crearEmpleado(@RequestBody EmpleadoDB empleado) {
        try {
            logica.crearEmpleado(empleado);
            return new Respuesta("Exitoso", "El empleado se ha guardado correctamente.");
        } catch (Exception e) {
            return new Respuesta("Fallido", "Algo ha salido mal. No se ha podido guardar el empleado.");
        }
    }

    @PutMapping(path = "/empleado/actualizar/informacion_personal")
    public Respuesta actualizarInformacionPersonalEmpleado(@RequestBody EmpleadoDB nuevoEmpleado){
        try {
            logica.modificarInformacionPersonalEmpleado(nuevoEmpleado.getId(), nuevoEmpleado);
            return new Respuesta("Exitoso", "La informacion personal del empleado se ha actualizado correctamente.");
        } catch (Exception e) {
            return new Respuesta("Fallido", "Algo ha salido mal. No se ha podido actualizar la informacion personal del empleado.");
        }
    }

    @PutMapping(path = "/empleado/actualizar/rol")
    public Respuesta actualizarRolEmpleado(@RequestParam int id, int idNuevoRol){
        try {
            logica.modificarRolEmpleado(id, idNuevoRol);
            return new Respuesta("Exitoso", "El rol del usuario ha sido actualizado correctamente.");
        } catch (Exception e) {
            return new Respuesta("Fallido", "Algo ha salido mal. No se ha podido actualizar el rol empleado.");
        }
    }

    @DeleteMapping(path = "/empleado/eliminar/id")
    public Respuesta eliminarEmpleado(@RequestParam int id){
        try {
            logica.eliminarEmpleado(id);
            return new Respuesta("Exitoso", "El empleado se ha eliminado correctamente.");
        } catch (Exception e) {
            return new Respuesta("Fallido", "Algo ha salido mal. No se ha podido eliminar al empleado.");
        }
    }
}
