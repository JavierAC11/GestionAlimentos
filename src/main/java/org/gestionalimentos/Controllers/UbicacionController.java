package org.gestionalimentos.Controllers;

import org.gestionalimentos.DTO.ubicacion.CrearUbicacionDTO;
import org.gestionalimentos.DTO.ubicacion.ModificarUbicacionDTO;
import org.gestionalimentos.DTO.ubicacion.UbicacionDetalleDTO;
import org.gestionalimentos.DTO.ubicacion.UbicacionListadoDTO;
import org.gestionalimentos.Services.UbicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gestionar las ubicaciones de los alimentos en el sistema.
 * Permite realizar operaciones CRUD sobre las ubicaciones, como listar,
 * obtener detalles, crear, actualizar y eliminar ubicaciones.
 */
@RestController
@RequestMapping("/ubicaciones")
public class UbicacionController {

    private final UbicacionService ubicacionService;

    /**
     * Constructor que inyecta el servicio de ubicaciones en el controlador.
     *
     * @param ubicacionService Servicio que maneja la lógica de negocio de las ubicaciones.
     */
    @Autowired
    public UbicacionController(UbicacionService ubicacionService) {
        this.ubicacionService = ubicacionService;
    }

    /**
     * Endpoint para listar todas las ubicaciones con paginación.
     *
     * @param pageable Parámetro que define la paginación de la consulta.
     * @return Un ResponseEntity que contiene la lista paginada de ubicaciones.
     */
    @GetMapping
    public ResponseEntity<Page<UbicacionListadoDTO>> listarUbicaciones(Pageable pageable){
        // Llama al servicio para obtener la lista paginada de ubicaciones.
        return ResponseEntity.ok(ubicacionService.listarUbicaciones(pageable));
    }

    /**
     * Endpoint para obtener los detalles de una ubicación específica.
     *
     * @param id Identificador único de la ubicación.
     * @return Un ResponseEntity que contiene los detalles de la ubicación solicitada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UbicacionDetalleDTO> obtenerUbicacion(@PathVariable Long id){
        // Llama al servicio para obtener los detalles de la ubicación por su ID.
        return ResponseEntity.ok(ubicacionService.obtenerUbicacion(id));
    }

    /**
     * Endpoint para crear una nueva ubicación en el sistema.
     *
     * @param crearUbicacionDTO Objeto que contiene la información de la ubicación a crear.
     * @return Un ResponseEntity que contiene los detalles de la nueva ubicación creada.
     */
    @PostMapping
    public ResponseEntity<UbicacionDetalleDTO> crearUbicacion(@RequestBody CrearUbicacionDTO crearUbicacionDTO) {
        // Llama al servicio para crear una nueva ubicación y devolver sus detalles.
        UbicacionDetalleDTO nuevaUbicacion = ubicacionService.crearUbicacion(crearUbicacionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaUbicacion);
    }

    /**
     * Endpoint para actualizar una ubicación existente.
     *
     * @param id Identificador único de la ubicación que se desea actualizar.
     * @param modificarUbicacionDTO Objeto que contiene la nueva información de la ubicación.
     * @return Un ResponseEntity que contiene los detalles de la ubicación actualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UbicacionDetalleDTO> actualizarUbicacion(
            @PathVariable Long id,
            @RequestBody ModificarUbicacionDTO modificarUbicacionDTO) {
        // Llama al servicio para actualizar la ubicación por su ID y devolver los detalles actualizados.
        UbicacionDetalleDTO ubicacionActualizada = ubicacionService.actualizarUbicacion(id, modificarUbicacionDTO);
        return ResponseEntity.ok(ubicacionActualizada);
    }

    /**
     * Endpoint para eliminar una ubicación específica.
     *
     * @param id Identificador único de la ubicación a eliminar.
     * @return Un ResponseEntity con un código de estado 204 (No Content) indicando que la ubicación fue eliminada correctamente.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUbicacion(@PathVariable Long id) {
        // Llama al servicio para eliminar la ubicación por su ID.
        ubicacionService.borrarUbicacion(id);
        return ResponseEntity.noContent().build();
    }
}
