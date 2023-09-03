package co.edu.unisabana.recursos_humanos.controlador;

import co.edu.unisabana.recursos_humanos.db.EmpleadoDB;
import co.edu.unisabana.recursos_humanos.db.EmpleadoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GestionEmpleadosController {
    private EmpleadoRepository empleadoRepository;

    public GestionEmpleadosController(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @GetMapping(path = "/test")
    public String holaMundo(){
        return "Este mapping esta funcionando";
    }

    @GetMapping(path = "/ver_empleados")
    public List<EmpleadoDB> buscarEmpleados(){
        return empleadoRepository.findAll();
    }
}
