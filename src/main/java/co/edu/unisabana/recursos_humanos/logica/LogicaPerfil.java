package co.edu.unisabana.recursos_humanos.logica;

import co.edu.unisabana.recursos_humanos.controlador.dto.PerfilEmpleadoDTO;
import co.edu.unisabana.recursos_humanos.db.EmpleadoRepository;
import co.edu.unisabana.recursos_humanos.db.PerfilEmpleadoRepository;
import co.edu.unisabana.recursos_humanos.db.entidad.EmpleadoDB;
import co.edu.unisabana.recursos_humanos.db.entidad.PerfilEmpleadoDB;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public List<PerfilEmpleadoDB> buscarPerfilTodos() {
        return perfilEmpleadoRepository.findAll();
    }

    public String buscarPerfilPorID(int id) {
        Optional<PerfilEmpleadoDB> perfil = perfilEmpleadoRepository.findById(id);
        if(perfil.isPresent()){
            return perfil.toString();
        } else {
            return "El usuario no existe.";
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
}
