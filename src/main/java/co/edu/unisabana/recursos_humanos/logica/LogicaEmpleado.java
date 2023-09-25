package co.edu.unisabana.recursos_humanos.logica;

import co.edu.unisabana.recursos_humanos.controlador.dto.EmpleadoDTO;
import co.edu.unisabana.recursos_humanos.db.EmpleadoRepository;
import co.edu.unisabana.recursos_humanos.db.RolRepository;
import co.edu.unisabana.recursos_humanos.db.entidad.EmpleadoDB;
import co.edu.unisabana.recursos_humanos.db.entidad.RolDB;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LogicaEmpleado {
    private final EmpleadoRepository empleadoRepository;
    private final RolRepository rolRepository;

    public LogicaEmpleado(EmpleadoRepository empleadoRepository, RolRepository rolRepository) {
        this.empleadoRepository = empleadoRepository;
        this.rolRepository = rolRepository;
    }

    public List<EmpleadoDTO> buscarEmpleadosTodos() {
        List<EmpleadoDB> empleados = empleadoRepository.findAll();
        List<EmpleadoDTO> listaRespuesta = new ArrayList<>();
        for (EmpleadoDB empleadoDB : empleados) {
            listaRespuesta.add(transformarToDTO(empleadoDB));
        }

        return listaRespuesta;
    }

    public List<EmpleadoDTO> buscarEmpleadoPorID(int id) {
        Optional<EmpleadoDB> empleado = empleadoRepository.findById(id);
        if (empleado.isPresent()) {
            List<EmpleadoDTO> respuesta = new ArrayList<>();
            respuesta.add(transformarToDTO(empleado.get()));
            return respuesta;
        } else {
            return new ArrayList<>();
        }
    }

    public EmpleadoDB crearEmpleado(EmpleadoDTO empleadoDTO) {
        RolDB rol = rolRepository.getReferenceById(empleadoDTO.getIdRol());

        LocalDateTime now = LocalDateTime.now();

        EmpleadoDB empleado = new EmpleadoDB();
        empleado.setId(empleadoDTO.getId());
        empleado.setNombre(empleadoDTO.getNombre());
        empleado.setEdad(empleadoDTO.getEdad());
        empleado.setCorreo(empleadoDTO.getCorreo());
        empleado.setTelefono(empleadoDTO.getTelefono());
        empleado.setRol(rol);
        empleado.setDireccion(empleadoDTO.getDireccion());
        empleado.setCedula(empleadoDTO.getCedula());
        empleado.setFechaActualizacion(now);
        empleado.setFechaCreacion(now);
        empleadoRepository.save(empleado);

        return empleado;
    }

    public void eliminarEmpleado(int id) {
        empleadoRepository.deleteById(id);
    }

    public void modificarInformacionPersonalEmpleado(int id, EmpleadoDTO nuevoEmpleado) {
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
        RolDB rol = rolRepository.getReferenceById(idNuevoRol);

        EmpleadoDB empleadoActualizar = empleadoRepository.getReferenceById(id);
        empleadoActualizar.setRol(rol);
        empleadoActualizar.setFechaActualizacion(LocalDateTime.now());
        empleadoRepository.save(empleadoActualizar);
    }

    private static EmpleadoDTO transformarToDTO(EmpleadoDB empleado) {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setId(empleado.getId());
        empleadoDTO.setNombre(empleado.getNombre());
        empleadoDTO.setEdad(empleado.getEdad());
        empleadoDTO.setCorreo(empleado.getCorreo());
        empleadoDTO.setTelefono(empleado.getTelefono());
        empleadoDTO.setIdRol(empleado.getRol().getId());
        empleadoDTO.setDireccion(empleado.getDireccion());
        empleadoDTO.setCedula(empleado.getCedula());
        return empleadoDTO;
    }
}
