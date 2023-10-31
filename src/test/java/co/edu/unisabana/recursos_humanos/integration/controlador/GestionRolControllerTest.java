package co.edu.unisabana.recursos_humanos.integration.controlador;

import co.edu.unisabana.recursos_humanos.controlador.dto.EmpleadoDTO;
import co.edu.unisabana.recursos_humanos.controlador.dto.Respuesta;
import co.edu.unisabana.recursos_humanos.controlador.dto.RolDTO;
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
class GestionRolControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void Dado_rol_valido_Cuando_hacer_peticion_post_Entonces_guarda_correctamente() {

        RolDTO rol = new RolDTO();
        rol.setId((int) Math.floor(Math.random() * (999999) + 1));
        rol.setResponsabilidades("Cuidar porteria");

        ResponseEntity<Respuesta> respuesta = restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        Assertions.assertEquals("Exitoso", Objects.requireNonNull(respuesta.getBody()).getStatus());
    }

    @Test
    void Dado_rol_invalido_Cuando_hacer_peticion_post_Entonces_lanza_excepcion() {

        RolDTO rol = new RolDTO();

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        ResponseEntity<Respuesta> respuesta = restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        Assertions.assertEquals("Fallido", Objects.requireNonNull(respuesta.getBody()).getStatus());
    }

    @Test
    void Dado_rol_bd_existente_Cuando_hacer_peticion_buscar_por_id_Entonces_recupera_dto(){

        RolDTO rol = new RolDTO();
        int id = (int) Math.floor(Math.random() * (999999) + 1);
        rol.setId(id);
        rol.setResponsabilidades("Lavar los pisos");

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        ResponseEntity<List> lista = restTemplate.getForEntity("/rol/buscar/id?id=" + id, List.class);

        Assertions.assertEquals(1, Objects.requireNonNull(lista.getBody()).size());
    }

    @Test
    void Dado_roles_en_bd_Cuando_hacer_peticion_buscar_todos_Entonces_recupera_dtos(){

        RolDTO rol = new RolDTO();
        int id = (int) Math.floor(Math.random() * (999999) + 1);
        rol.setId(id);
        rol.setResponsabilidades("Entregar cumpotadores");

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        ResponseEntity<List> lista = restTemplate.getForEntity("/rol/buscar/todos" , List.class);

        Assertions.assertFalse(lista.getBody().isEmpty());
    }

    @Test
    void Dado_rol_requisito_db_existentes_Cuando_hacer_peticion_delete_con_id_correcto_Entonces_elimina_rol() {
        int id = (int) Math.floor(Math.random() * (999999) + 1);

        RolDB rol = new RolDB();
        rol.setId(id);

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        ResponseEntity<List> listaExistente = restTemplate.getForEntity("/rol/buscar/id?id="+id, List.class);

        HttpEntity<Object> request = new HttpEntity<>("");

        ResponseEntity<Respuesta> respuesta = restTemplate.exchange("/rol/eliminar/id?id="+id, HttpMethod.DELETE, request, Respuesta.class);
        ResponseEntity<List> listaEliminado = restTemplate.getForEntity("/rol/buscar/id?id=" + id, List.class);

        assertEquals("Exitoso", Objects.requireNonNull(respuesta.getBody()).getStatus());
        assertNotEquals(listaExistente.getBody(),listaEliminado.getBody());
        assertTrue(Objects.requireNonNull(listaEliminado.getBody()).isEmpty());
    }

    @Test
    void Dado_rol_requisito_db_inexistentes_Cuando_hacer_peticion_delete_con_id_Entonces_lanza_excepcion() {
        int id = (int) Math.floor(Math.random() * (999999) + 1);

        HttpEntity<Object> request = new HttpEntity<>("");

        ResponseEntity<Respuesta> respuesta = restTemplate.exchange("/rol/eliminar/id?id="+id, HttpMethod.DELETE, request, Respuesta.class);

        assertEquals("Fallido", Objects.requireNonNull(respuesta.getBody()).getStatus());
    }

    @Test
    void Dado_rol_existente_dto_valido_Cuando_hacer_peticion_put_actualizar_Entonces_actualiza_informacion() {
        int id = (int) Math.floor(Math.random() * (999999) + 1);

        RolDTO rol = new RolDTO();
        rol.setId(id);

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        rol.setResponsabilidades("Limpiar los vidrios del edificio");

        HttpEntity<RolDTO> request = new HttpEntity<>(rol);

        ResponseEntity<Respuesta> respuesta = restTemplate.exchange("/rol/actualizar/", HttpMethod.PUT, request, Respuesta.class);
        ResponseEntity<List> lista = restTemplate.getForEntity("/rol/buscar/id?id=" + id, List.class);

        assertEquals("Exitoso", Objects.requireNonNull(respuesta.getBody()).getStatus());
        Assertions.assertTrue(Objects.requireNonNull(lista.getBody()).get(0).toString().contains("Limpiar los vidrios del edificio"));
    }

    @Test
    void Dado_rol_inexistente_Cuando_hacer_peticion_put_Entonces_lanza_excepcion() {
        RolDTO rol = new RolDTO();

        rol.setId(4);

        HttpEntity<RolDTO> request = new HttpEntity<>(rol);

        ResponseEntity<Respuesta> respuesta = restTemplate.exchange("/rol/actualizar/", HttpMethod.PUT, request, Respuesta.class);

        assertEquals("Fallido", Objects.requireNonNull(respuesta.getBody()).getStatus());
    }
}