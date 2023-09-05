package co.edu.unisabana.recursos_humanos.logica;

import co.edu.unisabana.recursos_humanos.db.entidad.RolDB;
import co.edu.unisabana.recursos_humanos.db.RolRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogicaRol {
    private final RolRepository rolRepository;

    public LogicaRol(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public void crearRol(RolDB rol){
        rol.setFechaActualizacion(LocalDateTime.now());
        rol.setFechaCreacion(LocalDateTime.now());
        rolRepository.save(rol);
    }
}
