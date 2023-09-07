package co.edu.unisabana.recursos_humanos.logica;

import co.edu.unisabana.recursos_humanos.controlador.dto.CertificadoDTO;
import co.edu.unisabana.recursos_humanos.db.CertificadoRepository;
import co.edu.unisabana.recursos_humanos.db.EmpleadoRepository;
import co.edu.unisabana.recursos_humanos.db.entidad.CertificadoDB;
import co.edu.unisabana.recursos_humanos.db.entidad.EmpleadoDB;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogicaCertificado {
    private final CertificadoRepository certificadoRepository;
    private final EmpleadoRepository empleadoRepository;

    public LogicaCertificado(CertificadoRepository certificadoRepository, EmpleadoRepository empleadoRepository) {
        this.certificadoRepository = certificadoRepository;
        this.empleadoRepository = empleadoRepository;
    }

    public void crearCertificado(CertificadoDTO certificado){
        CertificadoDB dbCertificado = new CertificadoDB();
        EmpleadoDB empleadoReferencia = empleadoRepository.getReferenceById(certificado.getIdEmpleado());

        dbCertificado.setId(certificado.getId());
        dbCertificado.setEmpleado(empleadoReferencia);
        dbCertificado.setTipo(certificado.getTipo());
        dbCertificado.setDescripcion(certificado.getDescripcion());
        dbCertificado.setFechaExpedicion(certificado.getFechaExpedicion());
        dbCertificado.setEntidadExpedidora(certificado.getEntidadExpedidora());
        dbCertificado.setFechaActualizacion(LocalDateTime.now());
        dbCertificado.setFechaCreacion(LocalDateTime.now());
        certificadoRepository.save(dbCertificado);
    }

    public List<EmpleadoDB> buscarCertificadosTodos() {
        return null;
    }

    public String buscarCertificadosEmpleado(int id) {
        return null;
    }

    public void eliminarCertificado(int id) {

    }
}
