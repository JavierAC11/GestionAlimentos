package org.gestionalimentos.Controllers;

import org.gestionalimentos.DTO.existencia.CrearExistenciaDTO;
import org.gestionalimentos.DTO.existencia.ExistenciaDetalleDTO;
import org.gestionalimentos.DTO.existencia.ExistenciaListadoDTO;
import org.gestionalimentos.DTO.existencia.ModificarExistenciaDTO;
import org.gestionalimentos.Services.ExistenciaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gestionar las existencias de alimentos en el sistema.
 * Permite realizar operaciones CRUD sobre las existencias, como listar,
 * obtener detalles, crear, actualizar, mover y eliminar existencias.
 */
@RestController
@RequestMapping("/existencias")
public class ExistenciasController {

    private final ExistenciaService existenciaService;

    /**
     * Constructor que inyecta el servicio de existencias en el controlador.
     *
     * @param existenciaService Servicio que maneja la lógica de negocio de las existencias.
     */
    public ExistenciasController(ExistenciaService existenciaService) {
        this.existenciaService = existenciaService;
    }

    /**
     * Endpoint para listar las existencias con paginación.
     *
     * @param pageable Parámetro que define la paginación de la consulta.
     * @return Un ResponseEntity que contiene la lista de existencias paginada.
     */
    @GetMapping
    public ResponseEntity<Page<ExistenciaListadoDTO>> listarExistencias(Pageable pageable){
        // Llama al servicio para obtener la lista paginada de existencias.
        return ResponseEntity.ok(existenciaService.listarExistencias(pageable));
    }

    /**
     * Endpoint para obtener los detalles de una existencia específica.
     *
     * @param id Identificador único de la existencia.
     * @return Un ResponseEntity que contiene los detalles de la existencia solicitada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExistenciaDetalleDTO> obtenerExistencia(@PathVariable Long id){
        // Llama al servicio para obtener los detalles de la existencia por su ID.
        return ResponseEntity.ok(existenciaService.obtenerExistencia(id));
    }

    /**
     * Endpoint para crear una nueva existencia de un alimento.
     *
     * @param crearExistenciaDTO Objeto que contiene la información de la existencia a crear.
     * @return Un ResponseEntity que contiene los detalles de la nueva existencia creada.
     */
    @PostMapping
    public ResponseEntity<ExistenciaDetalleDTO> crearExistencia(@RequestBody CrearExistenciaDTO crearExistenciaDTO) {
        // Llama al servicio para crear una nueva existencia y devolver sus detalles.
        ExistenciaDetalleDTO nuevaExistencia = existenciaService.crearExistencia(crearExistenciaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaExistencia);
    }

    /**
     * Endpoint para actualizar una existencia existente.
     *
     * @param id Identificador único de la existencia que se desea actualizar.
     * @param modificarExistenciaDTO Objeto que contiene la información a actualizar de la existencia.
     * @return Un ResponseEntity que contiene los detalles de la existencia actualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExistenciaDetalleDTO> actualizarExistencia(
            @PathVariable Long id,
            @RequestBody ModificarExistenciaDTO modificarExistenciaDTO) {
        // Llama al servicio para actualizar la existencia por su ID y devolver los detalles actualizados.
        ExistenciaDetalleDTO existenciaActualizada = existenciaService.actualizarExistencia(id, modificarExistenciaDTO);
        return ResponseEntity.ok(existenciaActualizada);
    }

    /**
     * Endpoint para mover una existencia a una nueva ubicación.
     *
     * @param id Identificador único de la existencia a mover.
     * @param idUbicacion Identificador de la nueva ubicación donde se moverá la existencia.
     * @return Un ResponseEntity que contiene los detalles de la existencia después de ser movida.
     */
    @PutMapping("/{id}/mover")
    public ResponseEntity<ExistenciaDetalleDTO> moverExistencia(
            @PathVariable Long id,
            @RequestBody Long idUbicacion) {
        // Llama al servicio para mover la existencia a la nueva ubicación y devolver los detalles actualizados.
        ExistenciaDetalleDTO existenciaActualizada = existenciaService.moverExistencia(id, idUbicacion);
        return ResponseEntity.ok(existenciaActualizada);
    }

    /**
     * Endpoint para eliminar una existencia específica.
     *
     * @param id Identificador único de la existencia a eliminar.
     * @return Un ResponseEntity con un código de estado 204 (No Content) indicando que la existencia fue eliminada.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarExistencia(@PathVariable Long id) {
        // Llama al servicio para eliminar la existencia por su ID.
        existenciaService.borrarExistencia(id);
        return ResponseEntity.noContent().build();
    }

}
