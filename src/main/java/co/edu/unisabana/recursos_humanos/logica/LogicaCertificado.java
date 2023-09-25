package co.edu.unisabana.recursos_humanos.logica;

import co.edu.unisabana.recursos_humanos.controlador.dto.CertificadoDTO;
import co.edu.unisabana.recursos_humanos.db.CertificadoRepository;
import co.edu.unisabana.recursos_humanos.db.EmpleadoRepository;
import co.edu.unisabana.recursos_humanos.db.entidad.CertificadoDB;
import co.edu.unisabana.recursos_humanos.db.entidad.EmpleadoDB;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<CertificadoDTO> buscarCertificadosTodos() {
        List<CertificadoDB> certificados = certificadoRepository.findAll();
        List<CertificadoDTO> listaRespuesta = new ArrayList<>();
        for (CertificadoDB certificadoDB : certificados) {
            listaRespuesta.add(transformarToDTO(certificadoDB));
        }
        return listaRespuesta;
    }

    public List<CertificadoDTO> buscarCertificadosPorIdCertificado(int id) {
        Optional<CertificadoDB> certificado = certificadoRepository.findById(id);
        if(certificado.isPresent()){
            List<CertificadoDTO> respuesta = new ArrayList<>();
            respuesta.add(transformarToDTO(certificado.get()));
            return respuesta;
        } else {
            return new ArrayList<>();
        }
    }

    public void eliminarCertificado(int id) {
        empleadoRepository.deleteById(id);
    }

    private static CertificadoDTO transformarToDTO(CertificadoDB certificado) {
        CertificadoDTO certificadoDTO = new CertificadoDTO();
        certificadoDTO.setId(certificado.getId());
        certificadoDTO.setDescripcion(certificado.getDescripcion());
        certificadoDTO.setTipo(certificado.getTipo());
        certificadoDTO.setFechaExpedicion(certificado.getFechaExpedicion());
        certificadoDTO.setEntidadExpedidora(certificado.getEntidadExpedidora());
        certificadoDTO.setIdEmpleado(certificado.getEmpleado().getId());
        return certificadoDTO;
    }

    
}
