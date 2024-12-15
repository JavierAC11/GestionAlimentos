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
/**
 * Clase que gestiona las operaciones relacionadas con las existencias de alimentos en el sistema.
 * Permite realizar acciones como:
 * - Listar existencias con paginación.
 * - Obtener detalles de una existencia por su ID.
 * - Crear nuevas existencias de alimentos en una ubicación específica,
 *   verificando que la ubicación no esté llena.
 * - Eliminar existencias de alimentos.
 * - Mover existencias entre ubicaciones.
 * - Actualizar la cantidad o ubicación de las existencias de alimentos.
 *
 * Esta clase interactúa con los repositorios de Existencia, Alimento y Ubicación,
 * y maneja excepciones como cuando una existencia no se encuentra, o cuando una ubicación
 * alcanza su capacidad máxima.
 */
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

    // Listar todas las existencias con paginación
    public Page<ExistenciaListadoDTO> listarExistencias(Pageable pageable){
        Page<Existencia> existencias = existenciaRepository.findAll(pageable);
        return existencias.map(this::convertirAExistenciaListadoDTO);
    }

    // Obtener los detalles de una existencia por su ID
    public ExistenciaDetalleDTO obtenerExistencia(Long id){
        Existencia existencia = existenciaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("Existencia no encontrada"));
        return convertirAExistenciaDetalleDTO(existencia);
    }

    // Crear una nueva existencia en una ubicación, verificando que no esté llena
    public ExistenciaDetalleDTO crearExistencia(CrearExistenciaDTO crearExistenciaDTO){
        Existencia existencia = new Existencia();
        Ubicacion ubicacion = ubicacionRepository.findById(crearExistenciaDTO.getUbicacion())
                .orElseThrow(() -> new RecursoNoEncontrado("Ubicacion no encontrada"));
        Pageable pageable = Pageable.unpaged();
        Page<Existencia> existencias = existenciaRepository.findByUbicacionId(ubicacion.getId(), pageable);

        // Verificar si la cantidad total de existencias en la ubicación supera su capacidad
        Integer cantidad = existencias.stream().mapToInt(Existencia::getCantidad).sum();
        if (cantidad >= ubicacion.getCapacidad()) {
            throw new UbicacionCompleta("Ubicacion llena");
        }

        // Crear y guardar la nueva existencia
        existencia.setCantidad(crearExistenciaDTO.getCantidad());
        existencia.setUbicacion(ubicacion);
        existencia.setAlimento(alimentoRepository.findById(crearExistenciaDTO.getAlimento())
                .orElseThrow(() -> new RecursoNoEncontrado("Alimento no encontrado")));
        existencia.setFechaEntrada(LocalDate.now());
        existenciaRepository.save(existencia);
        return convertirAExistenciaDetalleDTO(existencia);
    }

    // Eliminar una existencia por su ID
    public void borrarExistencia(Long id){
        if (!existenciaRepository.existsById(id)) {
            throw new RecursoNoEncontrado("Existencia no encontrada");
        }
        existenciaRepository.deleteById(id);
    }

    // Mover una existencia a una nueva ubicación
    @Transactional
    public ExistenciaDetalleDTO moverExistencia(Long id, Long idUbicacion) {
        Existencia existencia = existenciaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("Existencia no encontrada"));
        Ubicacion ubicacion = ubicacionRepository.findById(idUbicacion)
                .orElseThrow(() -> new RecursoNoEncontrado("Ubicacion no encontrada"));

        // Crear una nueva existencia en la nueva ubicación
        Existencia existenciaNueva = new Existencia();
        existenciaNueva.setCantidad(existencia.getCantidad());
        existenciaNueva.setUbicacion(ubicacion);
        existenciaNueva.setAlimento(existencia.getAlimento());
        existenciaNueva.setFechaEntrada(LocalDate.now());
        existenciaRepository.save(existenciaNueva);
        return convertirAExistenciaDetalleDTO(existenciaNueva);
    }

    // Actualizar los detalles de una existencia
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

    // Convertir una existencia a su DTO de detalle
    private ExistenciaDetalleDTO convertirAExistenciaDetalleDTO(Existencia existencia) {
        ExistenciaDetalleDTO dto = new ExistenciaDetalleDTO();
        dto.setId(existencia.getId());
        dto.setCantidad(existencia.getCantidad());
        dto.setAlimento(existencia.getAlimento().getNombre());
        dto.setUbicacion(existencia.getUbicacion().getNombre());
        dto.setFechaEntrada(existencia.getFechaEntrada().toString());
        return dto;
    }

    // Convertir una existencia a su DTO para listado
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
