package co.edu.unisabana.recursos_humanos.db;

import co.edu.unisabana.recursos_humanos.db.entidad.PerfilEmpleadoDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilEmpleadoRepository extends JpaRepository<PerfilEmpleadoDB, Integer> {
}
