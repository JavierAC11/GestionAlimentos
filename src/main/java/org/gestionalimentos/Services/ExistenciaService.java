package org.gestionalimentos.Services;

import org.gestionalimentos.DTO.existencia.CrearExistenciaDTO;
import org.gestionalimentos.DTO.existencia.ExistenciaDetalleDTO;
import org.gestionalimentos.DTO.existencia.ExistenciaListadoDTO;
import org.gestionalimentos.DTO.existencia.ModificarExistenciaDTO;
import org.gestionalimentos.Entities.Existencia;
import org.gestionalimentos.Entities.Ubicacion;
import org.gestionalimentos.Repositories.AlimentoRepository;
import org.gestionalimentos.Repositories.ExistenciaRepository;
import org.gestionalimentos.Repositories.UbicacionRepository;
import org.gestionalimentos.exceptions.RecursoNoEncontrado;
import org.gestionalimentos.exceptions.UbicacionCompleta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

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
                .orElseThrow(() -> new RecursoNoEncontrado("Existencia no encontrada"));
        return convertirAExistenciaDetalleDTO(existencia);
    }

    public ExistenciaDetalleDTO crearExistencia(CrearExistenciaDTO crearExistenciaDTO){
        Existencia existencia = new Existencia();
        Ubicacion ubicacion = ubicacionRepository.findById(crearExistenciaDTO.getUbicacion())
                .orElseThrow(() -> new RecursoNoEncontrado("Ubicacion no encontrada"));
        Pageable pageable = Pageable.unpaged();
        Page<Existencia> existencias = existenciaRepository.findByUbicacionId(ubicacion.getId(), pageable);

        Integer cantidad = existencias.stream().mapToInt(Existencia::getCantidad).sum();


        if (cantidad >= ubicacion.getCapacidad()) {
            throw new UbicacionCompleta("Ubicacion llena");
        }

        existencia.setCantidad(crearExistenciaDTO.getCantidad());
        existencia.setUbicacion(ubicacionRepository.findById(crearExistenciaDTO.getUbicacion())
                .orElseThrow(() -> new RecursoNoEncontrado("Ubicacion no encontrada")));
        existencia.setAlimento(alimentoRepository.findById(crearExistenciaDTO.getAlimento())
                .orElseThrow(() -> new RecursoNoEncontrado("Alimento no encontrado")));
        existencia.setFechaEntrada(LocalDate.now());
        existenciaRepository.save(existencia);
        return convertirAExistenciaDetalleDTO(existencia);
    }

    public void borrarExistencia(Long id){
        if (!existenciaRepository.existsById(id)) {
            throw new RecursoNoEncontrado("Existencia no encontrada");
        }
        existenciaRepository.deleteById(id);
    }

    @Transactional
    public ExistenciaDetalleDTO moverExistencia(Long id, Long idUbicacion) {
        Existencia existencia = existenciaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("Existencia no encontrada"));
        Ubicacion ubicacion = ubicacionRepository.findById(idUbicacion)
                .orElseThrow(() -> new RecursoNoEncontrado("Ubicacion no encontrada"));

        Existencia existenciaNueva = new Existencia();
        existenciaNueva.setCantidad(existencia.getCantidad());
        existenciaNueva.setUbicacion(ubicacion);
        existenciaNueva.setAlimento(existencia.getAlimento());
        existenciaNueva.setFechaEntrada(LocalDate.now());
        existenciaRepository.save(existenciaNueva);
        return convertirAExistenciaDetalleDTO(existenciaNueva);
    }


    public ExistenciaDetalleDTO actualizarExistencia(Long id, ModificarExistenciaDTO modificarExistenciaDTO){
        Existencia existencia = existenciaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("Existencia no encontrada"));
        if (modificarExistenciaDTO.getCantidad() != null) existencia.setCantidad(modificarExistenciaDTO.getCantidad());
        if (modificarExistenciaDTO.getUbicacion() != null) existencia.setUbicacion(ubicacionRepository.findById(modificarExistenciaDTO.getUbicacion())
                .orElseThrow(() -> new RecursoNoEncontrado("Ubicacion no encontrada")));
        if (modificarExistenciaDTO.getAlimento() != null) existencia.setAlimento(alimentoRepository.findById(modificarExistenciaDTO.getAlimento())
                .orElseThrow(() -> new RecursoNoEncontrado("Alimento no encontrado")));

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
