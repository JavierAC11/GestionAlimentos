package org.gestionalimentos.Services;

import org.gestionalimentos.DTO.existencia.CrearExistenciaDTO;
import org.gestionalimentos.DTO.existencia.ExistenciaDetalleDTO;
import org.gestionalimentos.DTO.existencia.ExistenciaListadoDTO;
import org.gestionalimentos.DTO.existencia.ModificarExistenciaDTO;
import org.gestionalimentos.Entities.Existencia;
import org.gestionalimentos.Repositories.AlimentoRepository;
import org.gestionalimentos.Repositories.ExistenciaRepository;
import org.gestionalimentos.Repositories.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExistenciaService {

    private final ExistenciaRepository existenciaRepository;
    private final AlimentoRepository alimentoRepository;
    private final UbicacionRepository ubicacionRepository;

    @Autowired
    public ExistenciaService(ExistenciaRepository existenciaRepository, AlimentoRepository alimentoRepository, UbicacionRepository ubicacionRepository) {
        this.existenciaRepository = existenciaRepository;
        this.alimentoRepository = alimentoRepository;
        this.ubicacionRepository = ubicacionRepository;

    }

    public Page<ExistenciaListadoDTO> listarExistencias(Pageable pageable){
        Page<Existencia> existencias;
        existencias = existenciaRepository.findAll(pageable);
        return existencias.map(this::convertirAExistenciaListadoDTO);
    }

    public ExistenciaDetalleDTO obtenerExistencia(Long id){
        Existencia existencia = existenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Existencia no encontrada"));
        return convertirAExistenciaDetalleDTO(existencia);
    }

    public ExistenciaDetalleDTO crearExistencia(CrearExistenciaDTO crearExistenciaDTO){
        Existencia existencia = new Existencia();
        existencia.setCantidad(crearExistenciaDTO.getCantidad());
        existencia.setUbicacion(ubicacionRepository.findById(crearExistenciaDTO.getUbicacion())
                .orElseThrow(() -> new RuntimeException("Ubicacion no encontrada")));
        existencia.setAlimento(alimentoRepository.findById(crearExistenciaDTO.getAlimento())
                .orElseThrow(() -> new RuntimeException("Alimento no encontrado")));
        existenciaRepository.save(existencia);
        return convertirAExistenciaDetalleDTO(existencia);
    }

    public void borrarExistencia(Long id){
        if (!existenciaRepository.existsById(id)) {
            throw new RuntimeException("Existencia no encontrada");
        }
        existenciaRepository.deleteById(id);
    }

    public ExistenciaDetalleDTO actualizarExistencia(Long id, ModificarExistenciaDTO modificarExistenciaDTO){
        Existencia existencia = existenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Existencia no encontrada"));
        if (modificarExistenciaDTO.getCantidad() != null) existencia.setCantidad(modificarExistenciaDTO.getCantidad());
        if (modificarExistenciaDTO.getUbicacion() != null) existencia.setUbicacion(ubicacionRepository.findById(modificarExistenciaDTO.getUbicacion())
                .orElseThrow(() -> new RuntimeException("Ubicacion no encontrada")));
        if (modificarExistenciaDTO.getAlimento() != null) existencia.setAlimento(alimentoRepository.findById(modificarExistenciaDTO.getAlimento())
                .orElseThrow(() -> new RuntimeException("Alimento no encontrado")));

        existenciaRepository.save(existencia);
        return convertirAExistenciaDetalleDTO(existencia);
    }

    private ExistenciaDetalleDTO convertirAExistenciaDetalleDTO(Existencia existencia) {
        ExistenciaDetalleDTO dto = new ExistenciaDetalleDTO();
        dto.setId(existencia.getId());
        dto.setCantidad(existencia.getCantidad());
        dto.setAlimento(existencia.getAlimento().getNombre());
        dto.setUbicacion(existencia.getUbicacion().getNombre());
        dto.setFechaEntrada(existencia.getFechaEntrada().toString());
        return dto;
    }

    private ExistenciaListadoDTO convertirAExistenciaListadoDTO(Existencia existencia){
        ExistenciaListadoDTO dto = new ExistenciaListadoDTO();
        dto.setId(existencia.getId());
        dto.setCantidad(existencia.getCantidad());
        dto.setAlimento(existencia.getAlimento().getNombre());
        dto.setUbicacion(existencia.getUbicacion().getNombre());
        dto.setFechaEntrada(existencia.getFechaEntrada().toString());
        return dto;
    }


}