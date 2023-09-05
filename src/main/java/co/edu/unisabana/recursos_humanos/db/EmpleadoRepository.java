package co.edu.unisabana.recursos_humanos.db;

import co.edu.unisabana.recursos_humanos.db.entidad.EmpleadoDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<EmpleadoDB, Integer> {
}
