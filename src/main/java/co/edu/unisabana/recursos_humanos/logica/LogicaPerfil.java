package co.edu.unisabana.recursos_humanos.logica;

import co.edu.unisabana.recursos_humanos.db.entidad.PerfilEmpleadoDB;
import co.edu.unisabana.recursos_humanos.db.PerfilEmpleadoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogicaPerfil {
    private final PerfilEmpleadoRepository perfilEmpleadoRepository;

    public LogicaPerfil(PerfilEmpleadoRepository perfilEmpleadoRepository) {
        this.perfilEmpleadoRepository = perfilEmpleadoRepository;
    }

    public void crearPerfil(PerfilEmpleadoDB perfil){
        perfil.setFechaActualizacion(LocalDateTime.now());
        perfil.setFechaCreacion(LocalDateTime.now());
        perfilEmpleadoRepository.save(perfil);
    }
}
