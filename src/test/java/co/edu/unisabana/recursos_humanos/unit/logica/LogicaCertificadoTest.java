package co.edu.unisabana.recursos_humanos.unit.logica;

import co.edu.unisabana.recursos_humanos.controlador.dto.CertificadoDTO;
import co.edu.unisabana.recursos_humanos.db.CertificadoRepository;
import co.edu.unisabana.recursos_humanos.db.EmpleadoRepository;
import co.edu.unisabana.recursos_humanos.db.entidad.CertificadoDB;
import co.edu.unisabana.recursos_humanos.db.entidad.EmpleadoDB;
import co.edu.unisabana.recursos_humanos.logica.LogicaCertificado;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles(profiles = "test")
class LogicaCertificadoTest {

    @Mock
    private CertificadoRepository certificadoRepository;
    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private LogicaCertificado logicaCertificado;

    @Test
    void Dado_certificado_dto_Cuando_logica_crear_Entonces_sube_bd() {
        EmpleadoDB empleado = new EmpleadoDB();
        empleado.setId(1);

        CertificadoDTO dto = new CertificadoDTO();
        dto.setId((int) Math.floor(Math.random() * (999999) + 1));
        dto.setDescripcion("Certificado que valida la maestría sobre AWS");
        dto.setTipo("Certificado virtual");
        dto.setFechaExpedicion(LocalDateTime.now());
        dto.setEntidadExpedidora("Amazon");
        dto.setIdEmpleado(1);

        CertificadoDB certificadoDB = logicaCertificado.crearCertificado(dto);

        assertEquals("Certificado que valida la maestría sobre AWS", certificadoDB.getDescripcion());
        assertEquals("Certificado virtual", certificadoDB.getTipo());
        assertEquals("Amazon", certificadoDB.getEntidadExpedidora());
        Mockito.verify(certificadoRepository).save(certificadoDB);
    }

    @Test
    void Dado_db_certificados_y_empleados_existentes_Cuando_logica_buscar_todos_Entonces_retorna_lista_dto_correspondiente() {
        EmpleadoDB empleado = new EmpleadoDB();
        empleado.setId(1);

        List<CertificadoDB> listaDB = new ArrayList<>();
        CertificadoDB certificado = new CertificadoDB();
        certificado.setEmpleado(empleado);
        listaDB.add(certificado);
        listaDB.add(certificado);
        listaDB.add(certificado);

        when(certificadoRepository.findAll()).thenReturn(listaDB);

        List<CertificadoDTO> listaDTO = logicaCertificado.buscarCertificadosTodos();

        Mockito.verify(certificadoRepository).findAll();
        assertEquals(listaDTO.size(), certificadoRepository.findAll().size());
    }

    @Test
    void Dado_db_certificado_y_empleado_existentes_Cuando_logica_buscar_por_id_correcto_Entonces_retorna_dto_correspondiente() {
        int id = (int) Math.floor(Math.random() * (999999) + 1);

        EmpleadoDB empleado = new EmpleadoDB();
        empleado.setId(1);

        CertificadoDB db = new CertificadoDB();
        db.setId(id);
        db.setEmpleado(empleado);
        db.setTipo("Diploma");
        Optional<CertificadoDB> optDb = Optional.of(db);

        when(certificadoRepository.findById(id)).thenReturn(optDb);

        List<CertificadoDTO> dto = logicaCertificado.buscarCertificadosPorIdCertificado(id);

        Mockito.verify(certificadoRepository).findById(id);
        assertEquals(1, dto.size());
        assertEquals("Diploma", dto.get(0).getTipo());
    }

    @Test
    void Dado_db_certificado_y_empleado_inexistentes_Cuando_logica_buscar_por_id_incorrecto_Entonces_retorna_vacio() {
        int id = (int) Math.floor(Math.random() * (999999) + 1);

        Optional<CertificadoDB> optDb = Optional.empty();

        when(certificadoRepository.findById(id)).thenReturn(optDb);

        List<CertificadoDTO> dto = logicaCertificado.buscarCertificadosPorIdCertificado(id);

        Mockito.verify(certificadoRepository).findById(id);
        assertEquals(0, dto.size());
    }

    @Test
    void Dado_certificado_db_existente_Cuando_logica_eliminar_Entonces_elimina_bd() {
        int id = (int) Math.floor(Math.random() * (999999) + 1);

        logicaCertificado.eliminarCertificado(id);

        Mockito.verify(certificadoRepository).deleteById(id);
    }
}