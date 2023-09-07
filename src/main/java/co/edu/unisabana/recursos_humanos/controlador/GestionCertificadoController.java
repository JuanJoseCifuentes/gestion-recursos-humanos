package co.edu.unisabana.recursos_humanos.controlador;

import co.edu.unisabana.recursos_humanos.controlador.dto.CertificadoDTO;
import co.edu.unisabana.recursos_humanos.controlador.dto.EmpleadoDTO;
import co.edu.unisabana.recursos_humanos.controlador.dto.Respuesta;
import co.edu.unisabana.recursos_humanos.db.entidad.EmpleadoDB;
import co.edu.unisabana.recursos_humanos.logica.LogicaCertificado;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GestionCertificadoController {

    public LogicaCertificado logica;

    public GestionCertificadoController(LogicaCertificado logica) {
        this.logica = logica;
    }

    @GetMapping(path = "/certificado/buscar/todos")
    public List<EmpleadoDB> buscarCertificados() {
        return logica.buscarCertificadosTodos();
    }

    @GetMapping(path = "/certificado/buscar/id_empleado")
    public String buscarCertificadoPorID(@RequestParam int id) {
        return logica.buscarCertificadosEmpleado(id);
    }

    @PostMapping(path = "/certificado/subir")
    public Respuesta subirCertificado(@RequestBody CertificadoDTO certificado) {
        try {
            logica.crearCertificado(certificado);
            return new Respuesta("Exitoso","El certificado se ha creado correctamente.");
        } catch (Exception e) {
            return new Respuesta("Fallido", "Algo ha salido mal. No se ha podido crear el certificado");
        }
    }

    @DeleteMapping(path = "/certificado/eliminar/id")
    public Respuesta eliminarCertificado(@RequestParam int id){
        try {
            logica.eliminarCertificado(id);
            return new Respuesta("Exitoso", "El empleado se ha eliminado correctamente.");
        } catch (Exception e) {
            return new Respuesta("Fallido", "Algo ha salido mal. No se ha podido eliminar al empleado.");
        }
    }
}
