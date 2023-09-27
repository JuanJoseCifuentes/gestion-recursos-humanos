package co.edu.unisabana.recursos_humanos.unit.logica;

import co.edu.unisabana.recursos_humanos.controlador.dto.RolDTO;
import co.edu.unisabana.recursos_humanos.db.RolRepository;
import co.edu.unisabana.recursos_humanos.db.entidad.RolDB;
import co.edu.unisabana.recursos_humanos.logica.LogicaRol;
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
class LogicaRolTest {
    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    LogicaRol logicaRol;

    @Test
    void Dado_db_roles_existentes_Cuando_logica_buscar_todos_Entonces_retorna_lista_dto_correspondiente() {
        RolDB rol = new RolDB();
        rol.setId(1);

        List<RolDB> listaDB = new ArrayList<>();

        when(rolRepository.findAll()).thenReturn(listaDB);

        List<RolDTO> listaDTO = logicaRol.buscarRolTodos();

        Mockito.verify(rolRepository).findAll();
        assertEquals(listaDTO.size(), rolRepository.findAll().size());
    }

    @Test
    void Dado_db_roles_existentes_Cuando_logica_buscar_por_id_correcto_Entonces_retorna_dto_correspondiente() {

        int id = (int) Math.floor(Math.random() * (999999) + 1);

        RolDB rol = new RolDB();
        rol.setId(id);
        rol.setResponsabilidades("Ayudar en el cafe");

        Optional<RolDB> optDb = Optional.of(rol);

        when(rolRepository.findById(id)).thenReturn(optDb);

        List<RolDTO> dto = logicaRol.buscarRolPorID(id);

        Mockito.verify(rolRepository).findById(id);
        assertEquals(1, dto.size());
        assertEquals("Ayudar en el cafe", dto.get(0).getResponsabilidades());
    }

    @Test
    void Dado_db_roles_inexistentes_Cuando_logica_buscar_por_id_incorrecto_Entonces_retorna_vacio() {
        int id = (int) Math.floor(Math.random() * (999999) + 1);

        Optional<RolDB> optDb = Optional.empty();

        when(rolRepository.findById(id)).thenReturn(optDb);

        List<RolDTO> dto = logicaRol.buscarRolPorID(id);

        Mockito.verify(rolRepository).findById(id);
        assertEquals(0, dto.size());
    }

    @Test
    void Dado_rol_dto_Cuando_logica_crear_Entonces_sube_bd() {

        RolDTO rol = new RolDTO();
        rol.setId((int) Math.floor(Math.random() * (999999) + 1));
        rol.setResponsabilidades("Actualizar software en computadores");


        RolDB rolDB = logicaRol.crearRol(rol);

        assertEquals("Actualizar software en computadores", rolDB.getResponsabilidades());
        Mockito.verify(rolRepository).save(rolDB);
    }

    @Test
    void Dado_rol_db_existente_Cuando_logica_eliminar_Entonces_elimina_bd() {
        int id = (int) Math.floor(Math.random() * (999999) + 1);

        logicaRol.eliminarRol(id);

        Mockito.verify(rolRepository).deleteById(id);
    }

    @Test
    void Dado_Roldto_y_Roldb_existentes_Cuando_logica_modificar_responsabilidad_Entonces_modifica_bd() {

        RolDTO rol = new RolDTO();
        rol.setId((int) Math.floor(Math.random() * (999999) + 1));


        RolDB rolDBAntiguo = new RolDB();

        when(rolRepository.getReferenceById(rol.getId())).thenReturn(rolDBAntiguo);

        logicaRol.actualizarRol(rol.getId(), rol);

        assertEquals(rol.getResponsabilidades(), rolDBAntiguo.getResponsabilidades());
        Mockito.verify(rolRepository).save(rolDBAntiguo);
    }

}