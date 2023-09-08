package co.edu.unisabana.recursos_humanos.logica;

import co.edu.unisabana.recursos_humanos.controlador.dto.EmpleadoDTO;
import co.edu.unisabana.recursos_humanos.controlador.dto.PerfilEmpleadoDTO;
import co.edu.unisabana.recursos_humanos.db.EmpleadoRepository;
import co.edu.unisabana.recursos_humanos.db.PerfilEmpleadoRepository;
import co.edu.unisabana.recursos_humanos.db.entidad.EmpleadoDB;
import co.edu.unisabana.recursos_humanos.db.entidad.PerfilEmpleadoDB;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LogicaPerfil {
    private final PerfilEmpleadoRepository perfilEmpleadoRepository;
    private final EmpleadoRepository empleadoRepository;

    public LogicaPerfil(PerfilEmpleadoRepository perfilEmpleadoRepository, EmpleadoRepository empleadoRepository) {
        this.perfilEmpleadoRepository = perfilEmpleadoRepository;
        this.empleadoRepository = empleadoRepository;
    }

    public List<PerfilEmpleadoDTO> buscarPerfilTodos() {
        List<PerfilEmpleadoDB> perfiles = perfilEmpleadoRepository.findAll();
        List<PerfilEmpleadoDTO> listaRespuesta = new ArrayList<>();
        for (PerfilEmpleadoDB perfil : perfiles) {
            listaRespuesta.add(transformarToDTO(perfil));
        }
        return listaRespuesta;
    }

    public List<PerfilEmpleadoDTO> buscarPerfilPorID(int id) {
        Optional<PerfilEmpleadoDB> perfil = perfilEmpleadoRepository.findById(id);
        if(perfil.isPresent()){
            List<PerfilEmpleadoDTO> respuesta = new ArrayList<>();
            respuesta.add(transformarToDTO(perfil.get()));
            return respuesta;
        } else {
            return new ArrayList<>();
        }
    }

    public void crearPerfil(PerfilEmpleadoDTO perfilEmpleadoDTO){
        PerfilEmpleadoDB perfil = new PerfilEmpleadoDB();
        EmpleadoDB empleado = empleadoRepository.getReferenceById(perfilEmpleadoDTO.getIdEmpleado());

        perfil.setId(perfilEmpleadoDTO.getId());
        perfil.setEmpleado(empleado);
        perfil.setHabilidades(perfilEmpleadoDTO.getHabilidades());
        perfil.setAnosExperiencia(perfilEmpleadoDTO.getAnosExperiencia());
        perfil.setFechaActualizacion(LocalDateTime.now());
        perfil.setFechaCreacion(LocalDateTime.now());
        perfilEmpleadoRepository.save(perfil);
    }

    public void actualizarPerfil (int id, PerfilEmpleadoDTO nuevoPerfil){
        PerfilEmpleadoDB actualizarPerfilEmpleado = perfilEmpleadoRepository.getReferenceById(id);
        EmpleadoDB empleado = empleadoRepository.getReferenceById(nuevoPerfil.getIdEmpleado());

        actualizarPerfilEmpleado.setEmpleado(empleado);
        actualizarPerfilEmpleado.setHabilidades(nuevoPerfil.getHabilidades());
        actualizarPerfilEmpleado.setAnosExperiencia(nuevoPerfil.getAnosExperiencia());
        actualizarPerfilEmpleado.setFechaActualizacion(LocalDateTime.now());
        perfilEmpleadoRepository.save(actualizarPerfilEmpleado);
    }

    public void eliminarPerfil (int id){
        perfilEmpleadoRepository.deleteById(id);
    }

    private static PerfilEmpleadoDTO transformarToDTO (PerfilEmpleadoDB perfil){
        PerfilEmpleadoDTO perfilDTO = new PerfilEmpleadoDTO();
        perfilDTO.setId(perfil.getId());
        perfilDTO.setIdEmpleado(perfil.getEmpleado().getId());
        perfilDTO.setHabilidades(perfil.getHabilidades());
        perfilDTO.setAnosExperiencia(perfil.getAnosExperiencia());
        return perfilDTO;
    }
}
