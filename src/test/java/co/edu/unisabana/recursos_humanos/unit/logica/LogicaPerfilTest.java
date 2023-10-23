package co.edu.unisabana.recursos_humanos.unit.logica;

import co.edu.unisabana.recursos_humanos.controlador.dto.PerfilEmpleadoDTO;
import co.edu.unisabana.recursos_humanos.db.EmpleadoRepository;
import co.edu.unisabana.recursos_humanos.db.PerfilEmpleadoRepository;
import co.edu.unisabana.recursos_humanos.db.entidad.EmpleadoDB;
import co.edu.unisabana.recursos_humanos.db.entidad.PerfilEmpleadoDB;
import co.edu.unisabana.recursos_humanos.logica.LogicaPerfil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles(profiles = "test")
class LogicaPerfilTest {


    @Mock
    private PerfilEmpleadoRepository perfilEmpleadoRepository;
    @Mock
    private EmpleadoRepository empleadoRepository;
    @InjectMocks
    LogicaPerfil logicaPerfil;


    @Test
    void Dado_db_perfilEmpleado_y_empleado_existente_Cuando_logica_buscar_todos_Entonces_retorna_lista_dto_correspondiente() {
        EmpleadoDB empleado = new EmpleadoDB();
        empleado.setId(1234);

        List<PerfilEmpleadoDB> listDB = new ArrayList<>();
        PerfilEmpleadoDB perfilEmpleado1 = new PerfilEmpleadoDB();
        perfilEmpleado1.setEmpleado(empleado);
        listDB.add(perfilEmpleado1);
        listDB.add(perfilEmpleado1);
        listDB.add(perfilEmpleado1);

        when(perfilEmpleadoRepository.findAll()).thenReturn(listDB);

        List<PerfilEmpleadoDTO> listaDTO = logicaPerfil.buscarPerfilTodos();

        Mockito.verify(perfilEmpleadoRepository).findAll();
        assertEquals(listaDTO.size(), perfilEmpleadoRepository.findAll().size());
    }

    @Test
    void Dado_db_perfiles_y_empleado_existentes_Cuando_logica_buscar_por_id_correcto_Entonces_retorna_dto_correspondiente() {
        int id = (int) Math.floor(Math.random()*(999999)+1);

        EmpleadoDB empleado = new EmpleadoDB();
        empleado.setId(1);

        PerfilEmpleadoDB perfil = new PerfilEmpleadoDB();
        perfil.setId(id);
        perfil.setHabilidades("Responsable");
        perfil.setEmpleado(empleado);
        Optional<PerfilEmpleadoDB> optPerfil = Optional.of(perfil);

        when(perfilEmpleadoRepository.findById(id)).thenReturn(optPerfil);

        List<PerfilEmpleadoDTO> perfilDto = logicaPerfil.buscarPerfilPorID(id);

        Mockito.verify(perfilEmpleadoRepository).findById(id);
        assertEquals(1, perfilDto.size());
        assertEquals("Responsable", perfilDto.get(0).getHabilidades());
    }

    @Test
    void Dado_db_perfiles_y_empleado_inexistentes_Cuando_logica_buscar_por_id_incorrecto_Entonces_retorna_vacio() {
        int id = (int) Math.floor(Math.random()*(999999)+1);

        Optional<PerfilEmpleadoDB> optPerfil = Optional.empty();

        when(perfilEmpleadoRepository.findById(id)).thenReturn(optPerfil);

        List<PerfilEmpleadoDTO> dtoPerfil = logicaPerfil.buscarPerfilPorID(id);

        Mockito.verify(perfilEmpleadoRepository).findById(id);
        assertEquals(0,dtoPerfil.size());
    }

    @Test
    void Dado_perfil_dto_Cuando_logica_subir_Entonces_sube_db() {
        PerfilEmpleadoDTO dto = new PerfilEmpleadoDTO();
        dto.setId((int) Math.floor(Math.random()*(999999) + 1));
        dto.setIdEmpleado(1234);
        dto.setHabilidades("Confiable");
        dto.setAnosExperiencia(7);

        PerfilEmpleadoDB perfilDB = logicaPerfil.crearPerfil(dto);

        assertEquals("Confiable", perfilDB.getHabilidades());
        assertEquals(7, perfilDB.getAnosExperiencia());
        Mockito.verify(perfilEmpleadoRepository).save(perfilDB);
    }

    @Test
    void Dado_dto_y_db_existentes_Cuando_logica_actualizar_perfil_Enfonces_modifica_db() {
        PerfilEmpleadoDTO dto = new PerfilEmpleadoDTO();
        dto.setId((int) Math.floor(Math.random() * (999999) + 1));
        dto.setIdEmpleado(1234);
        dto.setHabilidades("Liderazgo");
        dto.setAnosExperiencia(5);

        PerfilEmpleadoDB dbAntiguo = new PerfilEmpleadoDB();

        when(perfilEmpleadoRepository.getReferenceById(dto.getId())).thenReturn(dbAntiguo);

        logicaPerfil.actualizarPerfil(dto.getId(), dto);

        assertEquals(dto.getHabilidades(), dbAntiguo.getHabilidades());
        assertEquals(dto.getAnosExperiencia(), dbAntiguo.getAnosExperiencia());
        Mockito.verify(perfilEmpleadoRepository).save(dbAntiguo);
    }

    @Test
    void Dado_perfil_db_existente_Cuando_logica_eliminar_Entonces_elimina_db() {
        int id = (int) Math.floor(Math.random() * (999999) + 1);

        logicaPerfil.eliminarPerfil(id);

        Mockito.verify(perfilEmpleadoRepository).deleteById(id);
    }
}