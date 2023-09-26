package co.edu.unisabana.recursos_humanos.integration.controlador;

import co.edu.unisabana.recursos_humanos.controlador.dto.EmpleadoDTO;
import co.edu.unisabana.recursos_humanos.controlador.dto.Respuesta;
import co.edu.unisabana.recursos_humanos.db.entidad.RolDB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
class GestionEmpleadoControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void Dado_empleados_en_bd_Cuando_hacer_peticion_buscar_todos_Entonces_recupera_dtos(){
        RolDB rol = new RolDB();
        rol.setId(1);

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId((int) Math.floor(Math.random() * (999999) + 1));
        dto.setNombre("Alejandro Gonzáles");
        dto.setEdad(34);
        dto.setCorreo("alejandro@correo.com");
        dto.setTelefono(310123123);
        dto.setIdRol(1);
        dto.setDireccion("Zipaquira");
        dto.setCedula(1002586540);

        restTemplate.postForEntity("/empleado/crear", dto, Respuesta.class);

        ResponseEntity<List> lista = restTemplate.getForEntity("/empleado/buscar/todos", List.class);

        Assertions.assertFalse(lista.getBody().isEmpty());
    }

    @Test
    public void Dado_empleado_bd_existente_Cuando_hacer_peticion_buscar_por_id_Entonces_recupera_dto(){
        RolDB rol = new RolDB();
        rol.setId(1);

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        int id = (int) Math.floor(Math.random() * (999999) + 1);

        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId(id);
        dto.setNombre("Alejandro Gonzáles");
        dto.setEdad(34);
        dto.setCorreo("alejandro@correo.com");
        dto.setTelefono(310123123);
        dto.setIdRol(1);
        dto.setDireccion("Zipaquira");
        dto.setCedula(1002586540);

        restTemplate.postForEntity("/empleado/crear", dto, Respuesta.class);

        ResponseEntity<List> lista = restTemplate.getForEntity("/empleado/buscar/id?id=" + id, List.class);

        Assertions.assertEquals(1, Objects.requireNonNull(lista.getBody()).size());
    }

    @Test
    public void Dado_empleado_valido_Cuando_hacer_peticion_post_Entonces_guarda_correctamente() {
        RolDB rol = new RolDB();
        rol.setId(1);

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId((int) Math.floor(Math.random() * (999999) + 1));
        dto.setNombre("Alejandro Gonzáles");
        dto.setEdad(34);
        dto.setCorreo("alejandro@correo.com");
        dto.setTelefono(310123123);
        dto.setIdRol(1);
        dto.setDireccion("Zipaquira");
        dto.setCedula(1002586540);

        ResponseEntity<Respuesta> respuesta = restTemplate.postForEntity("/empleado/crear", dto, Respuesta.class);

        Assertions.assertEquals("Exitoso", Objects.requireNonNull(respuesta.getBody()).getStatus());
    }

    @Test
    public void Dado_empleado_invalido_Cuando_hacer_peticion_post_Entonces_lanza_excepcion() {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId((int) Math.floor(Math.random() * (999999) + 1));
        dto.setNombre("Alejandro Gonzáles");
        dto.setEdad(34);
        dto.setCorreo("alejandro@correo.com");
        dto.setTelefono(310123123);
        dto.setIdRol(1);
        dto.setDireccion("Zipaquira");
        dto.setCedula(1002586540);

        ResponseEntity<Respuesta> respuesta = restTemplate.postForEntity("/empleado/crear", dto, Respuesta.class);

        Assertions.assertEquals("Fallido", Objects.requireNonNull(respuesta.getBody()).getStatus());
    }
}