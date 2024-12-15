package org.gestionalimentos.Services;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.gestionalimentos.DTO.alimento.AlimentoDetalleDTO;
import org.gestionalimentos.DTO.alimento.AlimentoListadoDTO;
import org.gestionalimentos.DTO.alimento.CrearAlimentoDTO;
import org.gestionalimentos.DTO.alimento.ModificarAlimentoDTO;
import org.gestionalimentos.Entities.Alimento;
import org.gestionalimentos.Entities.Existencia;
import org.gestionalimentos.Repositories.AlimentoRepository;
import org.gestionalimentos.Repositories.ExistenciaRepository;
import org.gestionalimentos.Repositories.UbicacionRepository;
import org.gestionalimentos.exceptions.AlimentoCaducado;
import org.gestionalimentos.exceptions.RecursoNoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Slf4j
@Service
/**
 * Clase que gestiona las operaciones relacionadas con los alimentos en el sistema.
 * Permite realizar acciones como:
 * - Listar alimentos con paginación.
 * - Obtener un alimento por su ID y verificar si ha caducado.
 * - Crear, actualizar y eliminar alimentos.
 * - Obtener alimentos en una ubicación específica o con fecha de caducidad próxima.
 *
 * Esta clase interactúa con los repositorios de alimentos y existencias, y maneja las excepciones
 * correspondientes en caso de errores como alimentos caducados o recursos no encontrados.
 */
public class AlimentoService {

    private final AlimentoRepository alimentoRepository;
    private final ExistenciaRepository existenciaRepository;

    @Autowired
    public AlimentoService(AlimentoRepository alimentoRepository, ExistenciaRepository existenciaRepository) {
        this.alimentoRepository = alimentoRepository;
        this.existenciaRepository = existenciaRepository;
    }

    // Listar todos los alimentos con paginación
    public Page<AlimentoListadoDTO> listarAlimentos(Pageable pageable) {
        Page<Alimento> alimentos = alimentoRepository.findAll(pageable);
        return alimentos.map(this::convertirAAlimentoListadoDTO);
    }

    // Obtener un alimento por su ID y verificar si ha caducado
    public AlimentoDetalleDTO obtenerAlimento(Long id) {
        Alimento alimento = alimentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("Alimento no encontrado"));
        if (alimento.getFechaCaducidad().isBefore(LocalDate.now())) {
            throw new AlimentoCaducado("El alimento ha caducado");
        }
        return convertirAAlimentoDetalleDTO(alimento);
    }

    // Crear un nuevo alimento a partir de un DTO
    public AlimentoDetalleDTO crearAlimento(CrearAlimentoDTO crearAlimentoDTO) {
        Alimento alimento = new Alimento();
        alimento.setNombre(crearAlimentoDTO.getNombre());
        alimento.setTipo(crearAlimentoDTO.getTipo());
        alimento.setEstado(crearAlimentoDTO.getEstado());
        alimento.setFechaCaducidad(LocalDate.parse(crearAlimentoDTO.getFechaCaducidad()));
        alimentoRepository.save(alimento);
        return convertirAAlimentoDetalleDTO(alimento);
    }

    // Eliminar un alimento por su ID
    public void borrarAlimento(Long id) {
        if (!alimentoRepository.existsById(id)) {
            throw new RecursoNoEncontrado("Alimento no encontrado");
        }
        alimentoRepository.deleteById(id);
    }

    // Actualizar un alimento existente con nuevos valores
    public AlimentoDetalleDTO actualizarAlimento(Long id, ModificarAlimentoDTO modificarAlimentoDTO) {
        Alimento alimento = alimentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("Alimento no encontrado"));

        // Actualiza los valores del alimento
        if (modificarAlimentoDTO.getTipo() != null) alimento.setTipo(modificarAlimentoDTO.getTipo());
        if (modificarAlimentoDTO.getEstado() != null) alimento.setEstado(modificarAlimentoDTO.getEstado());
        if (modificarAlimentoDTO.getFechaCaducidad() != null)
            alimento.setFechaCaducidad(LocalDate.parse(modificarAlimentoDTO.getFechaCaducidad()));
        if (modificarAlimentoDTO.getNombre() != null) alimento.setNombre(modificarAlimentoDTO.getNombre());

        alimentoRepository.save(alimento);
        return convertirAAlimentoDetalleDTO(alimento);
    }

    // Listar alimentos según su ubicación (con paginación)
    public Page<AlimentoListadoDTO> listarAlimentosPorUbicacion(Long ubicacionId, Pageable pageable) {
        existenciaRepository.findByUbicacionId(ubicacionId, pageable);
        Page<Existencia> existencias = existenciaRepository.findByUbicacionId(ubicacionId, pageable);

        if (existencias.isEmpty()) {
            throw new RecursoNoEncontrado("No hay alimentos en esta ubicación o no existe la ubicación");
        }
        Page<Alimento> alimentos = existencias.map(Existencia::getAlimento);
        return alimentos.map(this::convertirAAlimentoListadoDTO);
    }

    // Obtener alimentos que caducarán en un plazo de días (con paginación)
    public Page<AlimentoListadoDTO> obtenerAlimentosPorCaducar(Pageable pageable, Integer dias) {
        LocalDate fechaLimite = LocalDate.now().plusDays(dias);
        Page<Alimento> alimentos = alimentoRepository.findByFechaCaducidadBefore(fechaLimite, pageable);
        return alimentos.map(this::convertirAAlimentoListadoDTO);
    }

    // Convertir un Alimento a AlimentoDetalleDTO
    private AlimentoDetalleDTO convertirAAlimentoDetalleDTO(Alimento alimento) {
        AlimentoDetalleDTO dto = new AlimentoDetalleDTO();
        dto.setId(alimento.getId());
        dto.setNombre(alimento.getNombre());
        dto.setTipo(alimento.getTipo());
        dto.setFechaCaducidad(String.valueOf(alimento.getFechaCaducidad()));
        dto.setEstado(alimento.getEstado());
        return dto;
    }

    // Convertir un Alimento a AlimentoListadoDTO
    private AlimentoListadoDTO convertirAAlimentoListadoDTO(Alimento alimento) {
        AlimentoListadoDTO dto = new AlimentoListadoDTO();
        dto.setId(alimento.getId());
        dto.setNombre(alimento.getNombre());
        dto.setTipo(alimento.getTipo());
        dto.setFechaCaducidad(String.valueOf(alimento.getFechaCaducidad()));
        dto.setEstado(alimento.getEstado());
        return dto;
    }
}
