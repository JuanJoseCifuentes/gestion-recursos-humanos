package co.edu.unisabana.recursos_humanos.logica;

import co.edu.unisabana.recursos_humanos.db.CertificadoDB;
import co.edu.unisabana.recursos_humanos.db.CertificadoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogicaCerticado {
    private final CertificadoRepository certificadoRepository;

    public LogicaCerticado(CertificadoRepository certificadoRepository) {
        this.certificadoRepository = certificadoRepository;
    }

    public void crearCertificado(CertificadoDB certificado){
        certificado.setFechaActualizacion(LocalDateTime.now());
        certificado.setFechaCreacion(LocalDateTime.now());
        certificadoRepository.save(certificado);
    }
}
