package co.edu.unisabana.recursos_humanos.controlador;

import co.edu.unisabana.recursos_humanos.controlador.dto.Respuesta;
import co.edu.unisabana.recursos_humanos.db.CertificadoDB;
import co.edu.unisabana.recursos_humanos.logica.LogicaCerticado;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GestionCertificadoController {

    public LogicaCerticado logica;

    public GestionCertificadoController(LogicaCerticado logica) {
        this.logica = logica;
    }

    @PostMapping(path = "/certificado/subir")
    public Respuesta subirRol(@RequestBody CertificadoDB certificado) {
        try {
            logica.crearCertificado(certificado);
            return new Respuesta("Exitoso","El certificado se ha creado correctamente.");
        } catch (Exception e) {
            return new Respuesta("Fallido", "Algo ha salido mal. No se ha podido crear el certificado");
        }
    }
}
