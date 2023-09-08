package co.edu.unisabana.recursos_humanos.controlador;

import co.edu.unisabana.recursos_humanos.controlador.dto.Respuesta;
import co.edu.unisabana.recursos_humanos.controlador.dto.RolDTO;
import co.edu.unisabana.recursos_humanos.db.entidad.RolDB;
import co.edu.unisabana.recursos_humanos.logica.LogicaRol;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GestionRolController {

    public LogicaRol logica;

    public GestionRolController(LogicaRol logica) {
        this.logica = logica;
    }

    @PostMapping(path = "/rol/crear")
    public Respuesta crearRol(@RequestBody RolDTO rol) {
        try {
            logica.crearRol(rol);
            return new Respuesta("Exitoso","El rol se ha creado correctamente.");
        } catch (Exception e) {
            return new Respuesta("Fallido", "Algo ha salido mal. No se ha podido crear el rol");
        }
    }

    @GetMapping (path = "/rol/buscar/id")
    public List<RolDTO> buscarRolPorID(@RequestParam int id){
        return logica.buscarRolPorID(id);
    }

    @GetMapping (path = "/rol/buscar/todos")
    public List<RolDTO> buscarRoles(){
        return logica.buscarRolTodos();
    }

    @PutMapping(path = "/rol/actualizar")
    public Respuesta actualizarRol(@RequestBody RolDTO nuevoRol){
        try {
            logica.actualizarRol(nuevoRol.getId(), nuevoRol);
            return new Respuesta("Exitoso", "El rol ha sido actualizado correctamente.");
        } catch (Exception e) {
            return new Respuesta("Fallido", "Algo ha salido mal. No se ha podido actualizar el rol.");
        }
    }

    @DeleteMapping(path = "/rol/eliminar/id")
    public Respuesta eliminarRol(@RequestParam int id){
        try {
            logica.eliminarRol(id);
            return new Respuesta("Exitoso", "El rol ha sido eliminado correctamente.");
        } catch (Exception e) {
            return new Respuesta("Fallido", "Algo ha salido mal. No se ha podido eliminar el rol.");
        }
    }
}
