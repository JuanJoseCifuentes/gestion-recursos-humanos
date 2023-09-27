package co.edu.unisabana.recursos_humanos.integration.controlador;

import co.edu.unisabana.recursos_humanos.controlador.dto.Respuesta;
import co.edu.unisabana.recursos_humanos.controlador.dto.RolDTO;
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
class GestionRolControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void Dado_rol_valido_Cuando_hacer_peticion_post_Entonces_guarda_correctamente() {

        RolDTO rol = new RolDTO();
        rol.setId((int) Math.floor(Math.random() * (999999) + 1));
        rol.setResponsabilidades("Cuidar porteria");

        ResponseEntity<Respuesta> respuesta = restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        Assertions.assertEquals("Exitoso", Objects.requireNonNull(respuesta.getBody()).getStatus());
    }

    @Test
    public void Dado_rol_invalido_Cuando_hacer_peticion_post_Entonces_lanza_excepcion() {

        RolDTO rol = new RolDTO();

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        ResponseEntity<Respuesta> respuesta = restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        Assertions.assertEquals("Fallido", Objects.requireNonNull(respuesta.getBody()).getStatus());
    }

    @Test
    public void Dado_rol_bd_existente_Cuando_hacer_peticion_buscar_por_id_Entonces_recupera_dto(){

        RolDTO rol = new RolDTO();
        int id = (int) Math.floor(Math.random() * (999999) + 1);
        rol.setId(id);
        rol.setResponsabilidades("Lavar los pisos");

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        ResponseEntity<List> lista = restTemplate.getForEntity("/rol/buscar/id?id=" + id, List.class);

        Assertions.assertEquals(1, Objects.requireNonNull(lista.getBody()).size());
    }

    @Test
    public void Dado_roles_en_bd_Cuando_hacer_peticion_buscar_todos_Entonces_recupera_dtos(){

        RolDTO rol = new RolDTO();
        int id = (int) Math.floor(Math.random() * (999999) + 1);
        rol.setId(id);
        rol.setResponsabilidades("Entregar cumpotadores");

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        ResponseEntity<List> lista = restTemplate.getForEntity("/rol/buscar/todos" , List.class);

        Assertions.assertFalse(lista.getBody().isEmpty());
    }
}