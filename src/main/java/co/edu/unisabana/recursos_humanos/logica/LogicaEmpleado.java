package co.edu.unisabana.recursos_humanos.logica;

import co.edu.unisabana.recursos_humanos.db.EmpleadoDB;
import co.edu.unisabana.recursos_humanos.db.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LogicaEmpleado {
    private final EmpleadoRepository empleadoRepository;

    public LogicaEmpleado(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public List<EmpleadoDB> buscarEmpleadosTodos() {
        return empleadoRepository.findAll();
    }

    public String buscarEmpleadoPorID(int id) {
        Optional<EmpleadoDB> empleado = empleadoRepository.findById(id);
        if(empleado.isPresent()){
            return empleado.toString();
        } else {
            return "El usuario no existe.";
        }
    }

    public void crearEmpleado(EmpleadoDB empleado) {
        empleado.setFechaActualizacion(LocalDateTime.now());
        empleado.setFechaCreacion(LocalDateTime.now());
        empleadoRepository.save(empleado);
    }

    public void eliminarEmpleado(int id) {
        empleadoRepository.deleteById(id);
    }

    public void modificarInformacionPersonalEmpleado(int id, EmpleadoDB nuevoEmpleado) {
        EmpleadoDB empleadoActualizar = empleadoRepository.getReferenceById(id);
        empleadoActualizar.setNombre(nuevoEmpleado.getNombre());
        empleadoActualizar.setEdad(nuevoEmpleado.getEdad());
        empleadoActualizar.setCedula(nuevoEmpleado.getCedula());
        empleadoActualizar.setCorreo(nuevoEmpleado.getCorreo());
        empleadoActualizar.setDireccion(nuevoEmpleado.getDireccion());
        empleadoActualizar.setTelefono(nuevoEmpleado.getTelefono());
        empleadoActualizar.setFechaActualizacion(LocalDateTime.now());
        empleadoRepository.save(empleadoActualizar);
    }

    public void modificarRolEmpleado(int id, int idNuevoRol) {
        EmpleadoDB empleadoActualizar = empleadoRepository.getReferenceById(id);
        empleadoActualizar.setIdRol(idNuevoRol);
        empleadoActualizar.setFechaActualizacion(LocalDateTime.now());
        empleadoRepository.save(empleadoActualizar);
    }
}
