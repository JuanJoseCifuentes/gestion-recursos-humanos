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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

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

        Assertions.assertFalse(Objects.requireNonNull(lista.getBody()).isEmpty());
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

        assertEquals(1, Objects.requireNonNull(lista.getBody()).size());
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

        assertEquals("Exitoso", Objects.requireNonNull(respuesta.getBody()).getStatus());
    }

    @Test
    public void Dado_empleado_invalido_Cuando_hacer_peticion_post_Entonces_lanza_excepcion() {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId((int) Math.floor(Math.random() * (999999) + 1));
        dto.setNombre("Alejandro Gonzáles");
        dto.setEdad(34);
        dto.setCorreo("alejandro@correo.com");
        dto.setTelefono(310123123);
        dto.setIdRol(100);
        dto.setDireccion("Zipaquira");
        dto.setCedula(1002586540);

        ResponseEntity<Respuesta> respuesta = restTemplate.postForEntity("/empleado/crear", dto, Respuesta.class);

        assertEquals("Fallido", Objects.requireNonNull(respuesta.getBody()).getStatus());
    }

    @Test
    public void Dado_empleado_existente_dto_valido_Cuando_hacer_peticion_put_informacion_personal_Entonces_actualiza_informacion() {
        int id = (int) Math.floor(Math.random() * (999999) + 1);

        RolDB rol = new RolDB();
        rol.setId(1);

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

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

        dto.setNombre("Nicolas Fuentes");
        HttpEntity<EmpleadoDTO> request = new HttpEntity<>(dto);

        ResponseEntity<Respuesta> respuesta = restTemplate.exchange("/empleado/actualizar/informacion_personal", HttpMethod.PUT, request, Respuesta.class);
        ResponseEntity<List> lista = restTemplate.getForEntity("/empleado/buscar/id?id=" + id, List.class);

        assertEquals("Exitoso", Objects.requireNonNull(respuesta.getBody()).getStatus());
        Assertions.assertTrue(Objects.requireNonNull(lista.getBody()).get(0).toString().contains("Nicolas Fuentes"));
    }

    @Test
    public void Dado_empleado_inexistente_Cuando_hacer_peticion_put_informacion_personal_Entonces_lanza_excepcion() {
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
        HttpEntity<EmpleadoDTO> request = new HttpEntity<>(dto);

        ResponseEntity<Respuesta> respuesta = restTemplate.exchange("/empleado/actualizar/informacion_personal", HttpMethod.PUT, request, Respuesta.class);

        assertEquals("Fallido", Objects.requireNonNull(respuesta.getBody()).getStatus());
    }

    @Test
    public void Dado_empleado_y_roles_db_existentes_Cuando_hacer_peticion_put_rol_Entonces_actualiza_rol() {
        int id = (int) Math.floor(Math.random() * (999999) + 1);

        RolDB rol = new RolDB();
        rol.setId(1);

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        rol.setId(2);

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

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

        HttpEntity<Object> request = new HttpEntity<>("");

        ResponseEntity<Respuesta> respuesta = restTemplate.exchange("/empleado/actualizar/rol?id="+id+"&idNuevoRol="+2, HttpMethod.PUT, request, Respuesta.class);
        ResponseEntity<List> lista = restTemplate.getForEntity("/empleado/buscar/id?id=" + id, List.class);

        assertEquals("Exitoso", Objects.requireNonNull(respuesta.getBody()).getStatus());
        Assertions.assertTrue(Objects.requireNonNull(lista.getBody()).get(0).toString().contains("idRol=2"));
    }

    @Test
    public void Dado_empleado_y_roles_db_inexistentes_Cuando_hacer_peticion_put_rol_Entonces_lanza_excepcion() {
        int id = (int) Math.floor(Math.random() * (999999) + 1);

        RolDB rol = new RolDB();
        rol.setId(1);

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

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

        HttpEntity<Object> request = new HttpEntity<>("");

        ResponseEntity<Respuesta> respuesta = restTemplate.exchange("/empleado/actualizar/rol?id="+id+"&idNuevoRol="+500, HttpMethod.PUT, request, Respuesta.class);
        ResponseEntity<List> lista = restTemplate.getForEntity("/empleado/buscar/id?id=" + id, List.class);

        assertEquals("Fallido", Objects.requireNonNull(respuesta.getBody()).getStatus());
        Assertions.assertFalse(Objects.requireNonNull(lista.getBody()).get(0).toString().contains("idRol=2"));
    }

    @Test
    public void Dado_empleado_y_requisito_db_existentes_Cuando_hacer_peticion_delete_con_id_correcto_Entonces_elimina_empleado() {
        int id = (int) Math.floor(Math.random() * (999999) + 1);

        RolDB rol = new RolDB();
        rol.setId(20);

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId(id);
        dto.setNombre("Alejandro Gonzáles");
        dto.setEdad(34);
        dto.setCorreo("alejandro@correo.com");
        dto.setTelefono(310123123);
        dto.setIdRol(20);
        dto.setDireccion("Zipaquira");
        dto.setCedula(1002586540);

        restTemplate.postForEntity("/empleado/crear", dto, Respuesta.class);
        ResponseEntity<List> listaExistente = restTemplate.getForEntity("/empleado/buscar/id?id="+id, List.class);

        HttpEntity<Object> request = new HttpEntity<>("");

        ResponseEntity<Respuesta> respuesta = restTemplate.exchange("/empleado/eliminar/id?id="+id, HttpMethod.DELETE, request, Respuesta.class);
        ResponseEntity<List> listaEliminado = restTemplate.getForEntity("/empleado/buscar/id?id=" + id, List.class);

        assertEquals("Exitoso", Objects.requireNonNull(respuesta.getBody()).getStatus());
        assertNotEquals(listaExistente.getBody(),listaEliminado.getBody());
        assertTrue(Objects.requireNonNull(listaEliminado.getBody()).isEmpty());
    }

    @Test
    public void Dado_empleado_y_requisito_db_inexistentes_Cuando_hacer_peticion_delete_con_id_Entonces_lanza_excepcion() {
        int id = (int) Math.floor(Math.random() * (999999) + 1);

        HttpEntity<Object> request = new HttpEntity<>("");

        ResponseEntity<Respuesta> respuesta = restTemplate.exchange("/empleado/eliminar/id?id="+id, HttpMethod.DELETE, request, Respuesta.class);

        assertEquals("Fallido", Objects.requireNonNull(respuesta.getBody()).getStatus());
    }
}