package org.gestionalimentos.Services;

import org.gestionalimentos.DTO.ubicacion.CrearUbicacionDTO;
import org.gestionalimentos.DTO.ubicacion.ModificarUbicacionDTO;
import org.gestionalimentos.DTO.ubicacion.UbicacionDetalleDTO;
import org.gestionalimentos.DTO.ubicacion.UbicacionListadoDTO;
import org.gestionalimentos.Entities.Ubicacion;
import org.gestionalimentos.Repositories.UbicacionRepository;
import org.gestionalimentos.exceptions.RecursoNoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UbicacionService {

    private final UbicacionRepository ubicacionRepository;

    @Autowired
    public UbicacionService(UbicacionRepository ubicacionRepository) {
        this.ubicacionRepository = ubicacionRepository;
    }

    public Page<UbicacionListadoDTO> listarUbicaciones(Pageable pageable) {
        Page<Ubicacion> ubicaciones;
        ubicaciones = ubicacionRepository.findAll(pageable);
        return ubicaciones.map(this::convertirAUbicacionListadoDTO);
    }

    public UbicacionDetalleDTO obtenerUbicacion(Long id) {
        Ubicacion ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("Ubicacion no encontrada"));
        return convertirAUbicacionDetalleDTO(ubicacion);
    }

    public UbicacionDetalleDTO crearUbicacion(CrearUbicacionDTO crearUbicacionDTO) {
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setNombre(crearUbicacionDTO.getNombre());
        ubicacion.setDescripcion(crearUbicacionDTO.getDescripcion());
        ubicacion.setCapacidad(crearUbicacionDTO.getCapacidad());
        ubicacion.setTipoUbicacion(crearUbicacionDTO.getTipoUbicacion());
        ubicacionRepository.save(ubicacion);
        return convertirAUbicacionDetalleDTO(ubicacion);
    }

    public void borrarUbicacion(Long id) {
        if (!ubicacionRepository.existsById(id)) {
            throw new RecursoNoEncontrado("Ubicacion no encontrada");
        }
        ubicacionRepository.deleteById(id);
    }

    public UbicacionDetalleDTO actualizarUbicacion(Long id, ModificarUbicacionDTO modificarUbicacionDTO) {
        Ubicacion ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("Ubicacion no encontrada"));
        ubicacion.setNombre(modificarUbicacionDTO.getNombre());
        ubicacion.setDescripcion(modificarUbicacionDTO.getDescripcion());
        ubicacion.setCapacidad(modificarUbicacionDTO.getCapacidad());
        ubicacion.setTipoUbicacion(modificarUbicacionDTO.getTipoUbicacion());
        ubicacionRepository.save(ubicacion);
        return convertirAUbicacionDetalleDTO(ubicacion);
    }

    private UbicacionListadoDTO convertirAUbicacionListadoDTO(Ubicacion ubicacion) {
        UbicacionListadoDTO ubicacionListadoDTO = new UbicacionListadoDTO();
        ubicacionListadoDTO.setId(ubicacion.getId());
        ubicacionListadoDTO.setNombre(ubicacion.getNombre());
        ubicacionListadoDTO.setDescripcion(ubicacion.getDescripcion());
        ubicacionListadoDTO.setCapacidad(ubicacion.getCapacidad());
        ubicacionListadoDTO.setTipoUbicacion(ubicacion.getTipoUbicacion());
        return ubicacionListadoDTO;
    }

    private UbicacionDetalleDTO convertirAUbicacionDetalleDTO(Ubicacion ubicacion) {
        UbicacionDetalleDTO ubicacionDetalleDTO = new UbicacionDetalleDTO();
        ubicacionDetalleDTO.setId(ubicacion.getId());
        ubicacionDetalleDTO.setNombre(ubicacion.getNombre());
        ubicacionDetalleDTO.setDescripcion(ubicacion.getDescripcion());
        ubicacionDetalleDTO.setCapacidad(ubicacion.getCapacidad());
        ubicacionDetalleDTO.setTipoUbicacion(ubicacion.getTipoUbicacion());
        return ubicacionDetalleDTO;
    }

}