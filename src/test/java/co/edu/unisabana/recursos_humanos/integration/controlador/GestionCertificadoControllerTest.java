package co.edu.unisabana.recursos_humanos.integration.controlador;

import co.edu.unisabana.recursos_humanos.controlador.dto.CertificadoDTO;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
class GestionCertificadoControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void Dado_certificados_en_bd_Cuando_hacer_peticion_buscar_todos_Entonces_recupera_dtos() {
        RolDB rol = new RolDB();
        rol.setId(1);

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        EmpleadoDTO empleado = new EmpleadoDTO();
        empleado.setId(10);
        empleado.setNombre("Alejandro Gonzáles");
        empleado.setEdad(34);
        empleado.setCorreo("alejandro@correo.com");
        empleado.setTelefono(310123123);
        empleado.setIdRol(1);
        empleado.setDireccion("Zipaquira");
        empleado.setCedula(1002586540);

        restTemplate.postForEntity("/empleado/crear", empleado, Respuesta.class);

        CertificadoDTO dto = new CertificadoDTO();
        dto.setId((int) Math.floor(Math.random() * (999999) + 1));
        dto.setDescripcion("Certificado que valida la maestría sobre AWS");
        dto.setTipo("Certificado virtual");
        dto.setFechaExpedicion(LocalDateTime.now());
        dto.setEntidadExpedidora("Amazon");
        dto.setIdEmpleado(10);

        restTemplate.postForEntity("/certificado/subir", dto, Respuesta.class);

        ResponseEntity<List> lista = restTemplate.getForEntity("/certificado/buscar/todos", List.class);

        Assertions.assertFalse(Objects.requireNonNull(lista.getBody()).isEmpty());
    }

    @Test
    void Dado_certificado_bd_existente_Cuando_hacer_peticion_buscar_por_id_Entonces_recupera_dto() {
        RolDB rol = new RolDB();
        rol.setId(1);

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        EmpleadoDTO empleado = new EmpleadoDTO();
        empleado.setId(10);
        empleado.setNombre("Alejandro Gonzáles");
        empleado.setEdad(34);
        empleado.setCorreo("alejandro@correo.com");
        empleado.setTelefono(310123123);
        empleado.setIdRol(1);
        empleado.setDireccion("Zipaquira");
        empleado.setCedula(1002586540);

        restTemplate.postForEntity("/empleado/crear", empleado, Respuesta.class);

        int id = (int) Math.floor(Math.random() * (999999) + 1);

        CertificadoDTO dto = new CertificadoDTO();
        dto.setId(id);
        dto.setDescripcion("Certificado que valida la maestría sobre AWS");
        dto.setTipo("Certificado virtual");
        dto.setFechaExpedicion(LocalDateTime.now());
        dto.setEntidadExpedidora("Amazon");
        dto.setIdEmpleado(10);

        restTemplate.postForEntity("/certificado/subir", dto, Respuesta.class);

        ResponseEntity<List> lista = restTemplate.getForEntity("/certificado/buscar/id_certificado?id="+id, List.class);

        assertEquals(1, Objects.requireNonNull(lista.getBody()).size());
    }

    @Test
    void Dado_certificado_dto_valido_Cuando_hacer_peticion_post_Entonces_guarda_correctamente() {
        RolDB rol = new RolDB();
        rol.setId(1);

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        EmpleadoDTO empleado = new EmpleadoDTO();
        empleado.setId(10);
        empleado.setNombre("Alejandro Gonzáles");
        empleado.setEdad(34);
        empleado.setCorreo("alejandro@correo.com");
        empleado.setTelefono(310123123);
        empleado.setIdRol(1);
        empleado.setDireccion("Zipaquira");
        empleado.setCedula(1002586540);

        restTemplate.postForEntity("/empleado/crear", empleado, Respuesta.class);

        int id = (int) Math.floor(Math.random() * (999999) + 1);

        CertificadoDTO dto = new CertificadoDTO();
        dto.setId(id);
        dto.setDescripcion("Certificado que valida la maestría sobre AWS");
        dto.setTipo("Certificado virtual");
        dto.setFechaExpedicion(LocalDateTime.now());
        dto.setEntidadExpedidora("Amazon");
        dto.setIdEmpleado(10);

        ResponseEntity<Respuesta> respuesta = restTemplate.postForEntity("/certificado/subir", dto, Respuesta.class);

        assertEquals("Exitoso", Objects.requireNonNull(respuesta.getBody()).getStatus());
    }

    @Test
    void Dado_certificado_dto_invalido_Cuando_hacer_peticion_post_Entonces_lanza_excepcion() {
        int id = (int) Math.floor(Math.random() * (999999) + 1);

        CertificadoDTO dto = new CertificadoDTO();
        dto.setId(id);
        dto.setDescripcion("Certificado que valida la maestría sobre AWS");
        dto.setTipo("Certificado virtual");
        dto.setFechaExpedicion(LocalDateTime.now());
        dto.setEntidadExpedidora("Amazon");
        dto.setIdEmpleado(100);

        ResponseEntity<Respuesta> respuesta = restTemplate.postForEntity("/certificado/subir", dto, Respuesta.class);

        assertEquals("Fallido", Objects.requireNonNull(respuesta.getBody()).getStatus());
    }

    @Test
    void Dado_certificado_y_requisitos_db_existentes_Cuando_hacer_peticion_delete_con_id_correcto_Entonces_elimina_certificado() {
        int id = (int) Math.floor(Math.random() * (999999) + 1);

        RolDB rol = new RolDB();
        rol.setId(30);

        restTemplate.postForEntity("/rol/crear", rol, Respuesta.class);

        EmpleadoDTO empleado = new EmpleadoDTO();
        empleado.setId(50);
        empleado.setNombre("Alejandro Gonzáles");
        empleado.setEdad(34);
        empleado.setCorreo("alejandro@correo.com");
        empleado.setTelefono(310123123);
        empleado.setIdRol(30);
        empleado.setDireccion("Zipaquira");
        empleado.setCedula(1002586540);

        restTemplate.postForEntity("/empleado/crear", empleado, Respuesta.class);

        CertificadoDTO dto = new CertificadoDTO();
        dto.setId(id);
        dto.setDescripcion("Certificado que valida la maestría sobre AWS");
        dto.setTipo("Certificado virtual");
        dto.setFechaExpedicion(LocalDateTime.now());
        dto.setEntidadExpedidora("Amazon");
        dto.setIdEmpleado(50);

        restTemplate.postForEntity("/certificado/subir", dto, Respuesta.class);
        ResponseEntity<List> listaExistente = restTemplate.getForEntity("/certificado/buscar/id_certificado?id="+id, List.class);

        HttpEntity<Object> request = new HttpEntity<>("");

        ResponseEntity<Respuesta> respuesta = restTemplate.exchange("/certificado/eliminar/id?id="+id, HttpMethod.DELETE, request, Respuesta.class);
        ResponseEntity<List> listaEliminado = restTemplate.getForEntity("/certificado/buscar/id_certificado?id=" + id, List.class);

        assertEquals("Exitoso", Objects.requireNonNull(respuesta.getBody()).getStatus());
        assertNotEquals(listaExistente.getBody(),listaEliminado.getBody());
        assertTrue(Objects.requireNonNull(listaEliminado.getBody()).isEmpty());
    }

    @Test
    void Dado_certificado_y_requisitos_db_inexistentes_Cuando_hacer_peticion_delete_con_id_Entonces_lanza_excepcion() {
        int id = (int) Math.floor(Math.random() * (999999) + 1);

        HttpEntity<Object> request = new HttpEntity<>("");

        ResponseEntity<Respuesta> respuesta = restTemplate.exchange("/certificado/eliminar/id?id="+id, HttpMethod.DELETE, request, Respuesta.class);

        assertEquals("Fallido", Objects.requireNonNull(respuesta.getBody()).getStatus());
    }
}