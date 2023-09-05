package co.edu.unisabana.recursos_humanos.db;

import co.edu.unisabana.recursos_humanos.db.entidad.RolDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<RolDB, Integer> {
}
