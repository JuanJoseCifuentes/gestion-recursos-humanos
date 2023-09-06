package co.edu.unisabana.recursos_humanos.logica;

import co.edu.unisabana.recursos_humanos.controlador.dto.RolDTO;
import co.edu.unisabana.recursos_humanos.db.entidad.RolDB;
import co.edu.unisabana.recursos_humanos.db.RolRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LogicaRol {
    private final RolRepository rolRepository;

    public LogicaRol(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public List<RolDB> buscarRolTodos(){
        return rolRepository.findAll();
    }

    public String buscarRolPorID(int id) {
        Optional<RolDB> rol = rolRepository.findById(id);
        if(rol.isPresent()){
            return rol.toString();
        } else {
            return "El usuario no existe.";
        }
    }

    public void crearRol(RolDTO rolDTO){
        RolDB rol = new RolDB();
        rol.setId(rolDTO.getId());
        rol.setResponsabilidades(rolDTO.getResponsabilidades());
        rol.setFechaCreacion(LocalDateTime.now());
        rol.setFechaActualizacion(LocalDateTime.now());
        rolRepository.save(rol);

    }

    public void eliminarRol(int id){
        rolRepository.deleteById(id);
    }

    public void actualizarRol(int id, RolDTO nuevoRol){
        RolDB rolActualizar = rolRepository.getReferenceById(id);
        rolActualizar.setId(nuevoRol.getId());
        rolActualizar.setResponsabilidades(nuevoRol.getResponsabilidades());
        rolActualizar.setFechaActualizacion(LocalDateTime.now());
        rolRepository.save(rolActualizar);
    }
}
