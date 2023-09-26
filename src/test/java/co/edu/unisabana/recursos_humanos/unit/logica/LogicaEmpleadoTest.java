package co.edu.unisabana.recursos_humanos.unit.logica;

import co.edu.unisabana.recursos_humanos.controlador.dto.EmpleadoDTO;
import co.edu.unisabana.recursos_humanos.db.EmpleadoRepository;
import co.edu.unisabana.recursos_humanos.db.RolRepository;
import co.edu.unisabana.recursos_humanos.db.entidad.EmpleadoDB;
import co.edu.unisabana.recursos_humanos.db.entidad.RolDB;
import co.edu.unisabana.recursos_humanos.logica.LogicaEmpleado;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles(profiles = "test")
class LogicaEmpleadoTest {

    @Mock
    private EmpleadoRepository empleadoRepository;
    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    LogicaEmpleado logicaEmpleado;

    @Test
    void Dado_db_empleados_y_roles_existentes_Cuando_logica_buscar_todos_Entonces_retorna_lista_dto_correspondiente() {
        RolDB rol = new RolDB();
        rol.setId(1);

        List<EmpleadoDB> listaDB = new ArrayList<>();
        EmpleadoDB empleado1 = new EmpleadoDB();
        empleado1.setRol(rol);
        listaDB.add(empleado1);
        listaDB.add(empleado1);
        listaDB.add(empleado1);

        when(empleadoRepository.findAll()).thenReturn(listaDB);

        List<EmpleadoDTO> listaDTO = logicaEmpleado.buscarEmpleadosTodos();

        Mockito.verify(empleadoRepository).findAll();
        assertEquals(listaDTO.size(), empleadoRepository.findAll().size());
    }

    @Test
    void Dado_db_empleados_y_roles_existentes_Cuando_logica_buscar_por_id_correcto_Entonces_retorna_dto_correspondiente() {
        int id = (int) Math.floor(Math.random() * (999999) + 1);

        RolDB rol = new RolDB();
        rol.setId(1);

        EmpleadoDB db = new EmpleadoDB();
        db.setId(id);
        db.setNombre("Pedro");
        db.setRol(rol);
        Optional<EmpleadoDB> optDb = Optional.of(db);

        when(empleadoRepository.findById(id)).thenReturn(optDb);

        List<EmpleadoDTO> dto = logicaEmpleado.buscarEmpleadoPorID(id);

        Mockito.verify(empleadoRepository).findById(id);
        assertEquals(1, dto.size());
        assertEquals("Pedro", dto.get(0).getNombre());
    }

    @Test
    void Dado_db_empleados_y_roles_inexistentes_Cuando_logica_buscar_por_id_incorrecto_Entonces_retorna_vacio() {
        int id = (int) Math.floor(Math.random() * (999999) + 1);

        Optional<EmpleadoDB> optDb = Optional.empty();

        when(empleadoRepository.findById(id)).thenReturn(optDb);

        List<EmpleadoDTO> dto = logicaEmpleado.buscarEmpleadoPorID(id);

        Mockito.verify(empleadoRepository).findById(id);
        assertEquals(0, dto.size());
    }

    @Test
    void Dado_empleado_dto_Cuando_logica_crear_Entonces_sube_bd() {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId((int) Math.floor(Math.random() * (999999) + 1));
        dto.setNombre("Alejandro Gonzáles");
        dto.setEdad(34);
        dto.setCorreo("alejandro@correo.com");
        dto.setTelefono(310123123);
        dto.setIdRol(2);
        dto.setDireccion("Zipaquira");
        dto.setCedula(1002586540);

        EmpleadoDB empleadoDB = logicaEmpleado.crearEmpleado(dto);

        assertEquals("Alejandro Gonzáles", empleadoDB.getNombre());
        assertEquals(34, empleadoDB.getEdad());
        assertEquals("alejandro@correo.com", empleadoDB.getCorreo());
        assertEquals(310123123, empleadoDB.getTelefono());
        assertEquals("Zipaquira", empleadoDB.getDireccion());
        assertEquals(1002586540, empleadoDB.getCedula());
        Mockito.verify(empleadoRepository).save(empleadoDB);
    }

    @Test
    void Dado_empleado_db_existente_Cuando_logica_eliminar_Entonces_elimina_bd() {
        int id = (int) Math.floor(Math.random() * (999999) + 1);

        logicaEmpleado.eliminarEmpleado(id);

        Mockito.verify(empleadoRepository).deleteById(id);
    }

    @Test
    void Dado_dto_y_db_existentes_Cuando_logica_modificar_informacion_personal_Entonces_modifica_bd() {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId((int) Math.floor(Math.random() * (999999) + 1));
        dto.setNombre("Alejandro Gonzáles");
        dto.setEdad(34);
        dto.setCorreo("alejandro@correo.com");
        dto.setTelefono(310123123);
        dto.setIdRol(2);
        dto.setDireccion("Zipaquira");
        dto.setCedula(1002586540);

        EmpleadoDB dbAntiguo = new EmpleadoDB();

        when(empleadoRepository.getReferenceById(dto.getId())).thenReturn(dbAntiguo);

        logicaEmpleado.modificarInformacionPersonalEmpleado(dto.getId(), dto);

        assertEquals(dto.getNombre(), dbAntiguo.getNombre());
        assertEquals(dto.getEdad(), dbAntiguo.getEdad());
        assertEquals(dto.getCorreo(), dbAntiguo.getCorreo());
        assertEquals(dto.getTelefono(), dbAntiguo.getTelefono());
        assertEquals(dto.getDireccion(), dbAntiguo.getDireccion());
        assertEquals(dto.getCedula(), dbAntiguo.getCedula());
        Mockito.verify(empleadoRepository).save(dbAntiguo);
    }

    @Test
    void Dado_db_empleado_y_rol_existentes_Cuando_logica_modificar_rol_Entonces_modifica_bd() {
        int idEmpleado = (int) Math.floor(Math.random() * (999999) + 1);
        int idRol = (int) Math.floor(Math.random() * (999999) + 1);

        EmpleadoDB dbAntiguo = new EmpleadoDB();
        RolDB dbRol = new RolDB();
        dbRol.setId(idRol);

        when(rolRepository.getReferenceById(idRol)).thenReturn(dbRol);
        when(empleadoRepository.getReferenceById(idEmpleado)).thenReturn(dbAntiguo);

        logicaEmpleado.modificarRolEmpleado(idEmpleado, idRol);

        assertEquals(idRol, dbAntiguo.getRol().getId());
        Mockito.verify(empleadoRepository).save(dbAntiguo);
    }
}