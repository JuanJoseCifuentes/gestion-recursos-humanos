package co.edu.unisabana.recursos_humanos.integration.controlador;

import co.edu.unisabana.recursos_humanos.controlador.dto.EmpleadoDTO;
import co.edu.unisabana.recursos_humanos.controlador.dto.PerfilEmpleadoDTO;
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
class GestionPerfilControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void Dado_perfiles_en_bd_Cuando_hacen_peticion_buscar_todos_Entonces_recuperar_dtos() {
        RolDB rol = new RolDB();
        rol.setId(1);

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        EmpleadoDTO empleado = new EmpleadoDTO();
        empleado.setId(1234);
        empleado.setIdRol(1);

        restTemplate.postForEntity("/empleado/crear", empleado, Respuesta.class);

        PerfilEmpleadoDTO dto = new PerfilEmpleadoDTO();
        dto.setId((int) Math.floor(Math.random() * (999999) + 1));
        dto.setIdEmpleado(1234);
        dto.setHabilidades("Autentico");
        dto.setAnosExperiencia(5);

        restTemplate.postForEntity("/perfil/subir", dto, Respuesta.class);

        ResponseEntity<List> lista = restTemplate.getForEntity("/perfil/buscar/todos", List.class);

        Assertions.assertFalse(lista.getBody().isEmpty());
    }

    @Test
    void Dado_perfil_db_existente_Cuando_hacer_peticion_buscar_por_id_Entonces_recupera_dto() {
        RolDB rol = new RolDB();
        rol.setId(1);

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        EmpleadoDTO empleado = new EmpleadoDTO();
        empleado.setId(1234);
        empleado.setIdRol(1);

        restTemplate.postForEntity("/empleado/crear", empleado, Respuesta.class);

        int id = (int) Math.floor(Math.random() * (999999) + 1);

        PerfilEmpleadoDTO dto = new PerfilEmpleadoDTO();
        dto.setId(id);
        dto.setIdEmpleado(1234);
        dto.setHabilidades("Autentico");
        dto.setAnosExperiencia(8);

        restTemplate.postForEntity("/perfil/subir", dto, Respuesta.class);

        ResponseEntity<List> lista = restTemplate.getForEntity("/perfil/buscar/id?id=" + id, List.class);

        Assertions.assertEquals(1, Objects.requireNonNull(lista.getBody().size()));

    }

    @Test
    void Dado_perfil_valido_Cuando_hacer_peticion_post_Entonces_guarda_correctamente() {
        RolDB rol = new RolDB();
        rol.setId(1);

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        EmpleadoDTO empleado = new EmpleadoDTO();
        empleado.setId(1234);
        empleado.setIdRol(1);

        restTemplate.postForEntity("/empleado/crear", empleado, Respuesta.class);

        PerfilEmpleadoDTO dto = new PerfilEmpleadoDTO();
        dto.setId((int) Math.floor(Math.random() * (999999) + 1));
        dto.setIdEmpleado(1234);
        dto.setHabilidades("Autentico");
        dto.setAnosExperiencia(5);

        ResponseEntity<Respuesta> respuesta =  restTemplate.postForEntity("/perfil/subir", dto, Respuesta.class);

        Assertions.assertEquals("Exitoso", Objects.requireNonNull(respuesta.getBody().getStatus()));
    }

    @Test
    void Dado_perfil_invalido_Cuando_hacer_peticion_post_Entonces_lanza_excepcion(){
        PerfilEmpleadoDTO dto = new PerfilEmpleadoDTO();
        dto.setId((int) Math.floor(Math.random() * (999999) + 1));
        dto.setIdEmpleado(1235);
        dto.setHabilidades("Autentico");
        dto.setAnosExperiencia(5);

        ResponseEntity<Respuesta> respuesta =  restTemplate.postForEntity("/perfil/subir", dto, Respuesta.class);

        Assertions.assertEquals("Fallido", Objects.requireNonNull(respuesta.getBody().getStatus()));
    }

    @Test
    void Dado_perfil_existente_dto_valido_Cuando_hacer_peticio_put_Entonces_actualiza_informacion() {
        int id = (int) Math.floor(Math.random() * (999999)* + 1);

        RolDB rol = new RolDB();
        rol.setId(1);

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        EmpleadoDTO empleado = new EmpleadoDTO();
        empleado.setId(1238);
        empleado.setIdRol(1);

        restTemplate.postForEntity("/empleado/crear", empleado, Respuesta.class);

        PerfilEmpleadoDTO dto = new PerfilEmpleadoDTO();
        dto.setId(id);
        dto.setIdEmpleado(1238);
        dto.setHabilidades("Organizado");
        dto.setAnosExperiencia(3);

        restTemplate.postForEntity("/perfil/subir", dto, Respuesta.class);

        dto.setHabilidades("Organizado y puntual");
        HttpEntity<PerfilEmpleadoDTO> request = new HttpEntity<>(dto);

        ResponseEntity<Respuesta> respuesta = restTemplate.exchange("/perfil//actualizar", HttpMethod.PUT, request, Respuesta.class);
        ResponseEntity<List> lista = restTemplate.getForEntity("/perfil/buscar/id?id=" + id, List.class);

        Assertions.assertEquals("Exitoso", Objects.requireNonNull(respuesta.getBody().getStatus()));

        Assertions.assertTrue(Objects.requireNonNull(lista.getBody()).get(0).toString().contains("Organizado y puntual"));

    }

    @Test
    void Dado_perfil_inexistente_Cuando_hacer_peticio_put_informacion_Entonces_lanza_excepcion(){
        int id = (int) Math.floor(Math.random() * (999999)* + 1);

        PerfilEmpleadoDTO dto = new PerfilEmpleadoDTO();
        dto.setId(id);
        dto.setIdEmpleado(1239);
        dto.setHabilidades("Organizado");
        dto.setAnosExperiencia(3);
        HttpEntity<PerfilEmpleadoDTO> request = new HttpEntity<>(dto);

        ResponseEntity<Respuesta> respuesta = restTemplate.exchange("/perfil/actualizar", HttpMethod.PUT, request, Respuesta.class);

        Assertions.assertEquals("Fallido", Objects.requireNonNull(respuesta.getBody().getStatus()));
    }

    @Test
    void eliminarPerfil() {
        int id = (int) Math.floor(Math.random() * (999999)* + 1);

        RolDB rol = new RolDB();
        rol.setId(5);

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        EmpleadoDTO empleado = new EmpleadoDTO();
        empleado.setId(1245);
        empleado.setIdRol(5);

        restTemplate.postForEntity("/empleado/crear", empleado, Respuesta.class);

        PerfilEmpleadoDTO dto = new PerfilEmpleadoDTO();
        dto.setId(id);
        dto.setIdEmpleado(1245);
        dto.setHabilidades("Programador en Java");
        dto.setAnosExperiencia(2);

        restTemplate.postForEntity("/perfil/subir", dto, Respuesta.class);
        ResponseEntity<List> listaExistente = restTemplate.getForEntity("/perfil/buscar/id?id=" + id, List.class);

        HttpEntity<Object> request = new HttpEntity<>("");

        ResponseEntity<Respuesta> respuesta = restTemplate.exchange("/perfil/eliminar?id="+id, HttpMethod.DELETE, request, Respuesta.class);
        ResponseEntity<List> listaEliminado = restTemplate.getForEntity("/perfil/buscar/id?id=" + id, List.class);

        assertEquals("Exitoso", Objects.requireNonNull(respuesta.getBody().getStatus()));
        assertNotEquals(listaExistente.getBody(), listaEliminado.getBody());
        assertTrue(Objects.requireNonNull(listaEliminado.getBody().isEmpty()));
    }

    @Test
    void Dado_perfil_y_requisito_db_inexistentes_Cuando_hacer_peticion_delete_con_id_Entonces_lanza_excepcion(){
        int id = (int) Math.floor(Math.random() * (999999) + 1);

        HttpEntity<Object> request = new HttpEntity<>("");

        ResponseEntity<Respuesta> respuesta = restTemplate.exchange("/perfil/eliminar?id="+id, HttpMethod.DELETE,request, Respuesta.class);

        assertEquals("Fallido", Objects.requireNonNull(respuesta.getBody().getStatus()));
    }
}