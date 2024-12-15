package org.gestionalimentos.Controllers;

import org.gestionalimentos.DTO.alimento.AlimentoDetalleDTO;
import org.gestionalimentos.DTO.alimento.AlimentoListadoDTO;
import org.gestionalimentos.DTO.alimento.CrearAlimentoDTO;
import org.gestionalimentos.DTO.alimento.ModificarAlimentoDTO;
import org.gestionalimentos.Services.AlimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gestionar los alimentos en el sistema.
 * Permite realizar operaciones CRUD sobre los alimentos, como listar,
 * obtener detalles, crear, actualizar y eliminar alimentos.
 */
@RestController
@RequestMapping("/alimentos")
public class AlimentoController {

    private final AlimentoService alimentoService;

    /**
     * Constructor que inyecta el servicio de alimentos en el controlador.
     *
     * @param alimentoService Servicio que maneja la lógica de negocio de los alimentos.
     */
    @Autowired
    public AlimentoController(AlimentoService alimentoService) {
        this.alimentoService = alimentoService;
    }

    /**
     * Endpoint para listar los alimentos con paginación.
     *
     * @param pageable Parámetro que define la paginación de la consulta.
     * @return Un ResponseEntity que contiene la lista de alimentos paginada.
     */
    @GetMapping
    ResponseEntity<Page<AlimentoListadoDTO>> listarAlimentos(Pageable pageable){
        // Llama al servicio para obtener la lista paginada de alimentos.
        return ResponseEntity.ok(alimentoService.listarAlimentos(pageable));
    }

    /**
     * Endpoint para obtener los detalles de un alimento específico.
     *
     * @param id Identificador único del alimento.
     * @return Un ResponseEntity que contiene los detalles del alimento solicitado.
     */
    @GetMapping("/{id}")
    ResponseEntity<AlimentoDetalleDTO> obtenerAlimento (@PathVariable Long id){
        // Llama al servicio para obtener los detalles del alimento por su ID.
        return ResponseEntity.ok(alimentoService.obtenerAlimento(id));
    }

    /**
     * Endpoint para listar los alimentos que están a punto de caducar.
     *
     * @param dias Número de días antes de la caducidad.
     * @param pageable Parámetro de paginación para la consulta.
     * @return Un ResponseEntity con la lista paginada de alimentos que van a caducar.
     */
    @GetMapping("/caducados")
    ResponseEntity<Page<AlimentoListadoDTO>> listarAlimentosCaducados(@RequestParam Integer dias, Pageable pageable){
        // Llama al servicio para obtener los alimentos que están próximos a caducar.
        return ResponseEntity.ok(alimentoService.obtenerAlimentosPorCaducar(pageable, dias));
    }

    /**
     * Endpoint para listar los alimentos de una ubicación específica.
     *
     * @param id Identificador de la ubicación.
     * @param pageable Parámetro de paginación.
     * @return Un ResponseEntity con la lista paginada de alimentos en la ubicación solicitada.
     */
    @GetMapping("/ubicacion/{id}")
    ResponseEntity<Page<AlimentoListadoDTO>> listarAlimentosPorUbicacion(@PathVariable Long id, Pageable pageable){
        // Llama al servicio para obtener los alimentos de una ubicación específica.
        return ResponseEntity.ok(alimentoService.listarAlimentosPorUbicacion(id, pageable));
    }

    /**
     * Endpoint para crear un nuevo alimento.
     *
     * @param crearAlimentoDTO Objeto que contiene la información del alimento a crear.
     * @return Un ResponseEntity que contiene los detalles del nuevo alimento creado.
     */
    @PostMapping
    ResponseEntity<AlimentoDetalleDTO> crearAlimento (@RequestBody CrearAlimentoDTO crearAlimentoDTO){
        // Llama al servicio para crear un nuevo alimento y devolver sus detalles.
        AlimentoDetalleDTO nuevoAlimento = alimentoService.crearAlimento(crearAlimentoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAlimento);
    }

    /**
     * Endpoint para actualizar un alimento existente.
     *
     * @param id Identificador único del alimento que se desea actualizar.
     * @param modificarAlimentoDTO Objeto que contiene la información a actualizar del alimento.
     * @return Un ResponseEntity que contiene los detalles del alimento actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AlimentoDetalleDTO> actualizarAlimento(
            @PathVariable Long id,
            @RequestBody ModificarAlimentoDTO modificarAlimentoDTO) {
        // Llama al servicio para actualizar el alimento por su ID y devolver los detalles actualizados.
        AlimentoDetalleDTO alimentoActualizado = alimentoService.actualizarAlimento(id, modificarAlimentoDTO);
        return ResponseEntity.ok(alimentoActualizado);
    }

    /**
     * Endpoint para eliminar un alimento específico.
     *
     * @param id Identificador único del alimento a eliminar.
     * @return Un ResponseEntity con un código de estado 204 (No Content) indicando que el alimento fue eliminado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlimento(@PathVariable Long id) {
        // Llama al servicio para eliminar el alimento por su ID.
        alimentoService.borrarAlimento(id);
        return ResponseEntity.noContent().build();
    }
}
