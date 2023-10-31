package co.edu.unisabana.recursos_humanos.controlador;

import co.edu.unisabana.recursos_humanos.controlador.dto.CertificadoDTO;
import co.edu.unisabana.recursos_humanos.controlador.dto.Respuesta;
import co.edu.unisabana.recursos_humanos.logica.LogicaCertificado;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/certificado")
public class GestionCertificadoController {
    private static final String EXITOSO = "Exitoso";
    private static final String FALLIDO = "Fallido";

    private final LogicaCertificado logica;

    public GestionCertificadoController(LogicaCertificado logica) {
        this.logica = logica;
    }

    @GetMapping(path = "/buscar/todos")
    public List<CertificadoDTO> buscarCertificados() {
        return logica.buscarCertificadosTodos();
    }

    @GetMapping(path = "/buscar/id_certificado")
    public List<CertificadoDTO> buscarCertificadoPorIdCertificado(@RequestParam int id) {
        return logica.buscarCertificadosPorIdCertificado(id);
    }

    @PostMapping(path = "/subir")
    public Respuesta subirCertificado(@RequestBody CertificadoDTO certificado) {
        try {
            logica.crearCertificado(certificado);
            return new Respuesta(EXITOSO,"El certificado se ha creado correctamente.");
        } catch (Exception e) {
            return new Respuesta(FALLIDO, "Algo ha salido mal. No se ha podido crear el certificado");
        }
    }

    @DeleteMapping(path = "/eliminar/id")
    public Respuesta eliminarCertificado(@RequestParam int id){
        try {
            logica.eliminarCertificado(id);
            return new Respuesta(EXITOSO, "El certificado se ha eliminado correctamente.");
        } catch (Exception e) {
            return new Respuesta(FALLIDO, "Algo ha salido mal. No se ha podido eliminar el certificado.");
        }
    }
}
