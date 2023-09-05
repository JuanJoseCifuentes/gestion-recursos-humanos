package co.edu.unisabana.recursos_humanos.db;

import co.edu.unisabana.recursos_humanos.db.entidad.CertificadoDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificadoRepository extends JpaRepository<CertificadoDB, Integer> {
}
