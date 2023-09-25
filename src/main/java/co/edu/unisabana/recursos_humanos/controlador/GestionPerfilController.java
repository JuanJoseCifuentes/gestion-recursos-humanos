package co.edu.unisabana.recursos_humanos.controlador;

import co.edu.unisabana.recursos_humanos.controlador.dto.PerfilEmpleadoDTO;
import co.edu.unisabana.recursos_humanos.controlador.dto.Respuesta;
import co.edu.unisabana.recursos_humanos.logica.LogicaPerfil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/perfil")
public class GestionPerfilController {

    public LogicaPerfil logica;

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
    public Respuesta subirRol(@RequestBody PerfilEmpleadoDTO perfil) {
        try {
            logica.crearPerfil(perfil);
            return new Respuesta("Exitoso","El perfil se ha creado correctamente.");
        } catch (Exception e) {
            return new Respuesta("Fallido", "Algo ha salido mal. No se ha podido crear el perfil");
        }
    }

    @PutMapping(path = "/actualizar")
    public Respuesta actualizarPerfil (@RequestBody PerfilEmpleadoDTO perfilNuevo){
        try {
            logica.actualizarPerfil(perfilNuevo.getId(), perfilNuevo);
            return new Respuesta("Exitoso", "El perfil ha sido eliminado correctamente.");
        } catch (Exception e){
            return new Respuesta("Fallido","Algo ha salido mal, no se ha podido eliminar el perfil.");
        }
    }

    @DeleteMapping (path = "/eliminar")
    public Respuesta eliminarPerfil (@RequestParam int id){
        try {
            logica.eliminarPerfil(id);
            return new Respuesta("Exitoso", "El perfil ha sido eliminado correctamente.");
        } catch (Exception e){
            return new Respuesta("Fallido","Algo ha salido mal, no se ha podido eliminar el perfil.");
        }

    }
}
