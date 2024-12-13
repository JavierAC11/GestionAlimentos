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

@RestController
@RequestMapping("/existencias")
public class ExistenciasController {

    private final ExistenciaService existenciaService;

    public ExistenciasController(ExistenciaService existenciaService) {
        this.existenciaService = existenciaService;
    }

    @GetMapping
    public ResponseEntity<Page<ExistenciaListadoDTO>> listarExistencias(Pageable pageable){
        return ResponseEntity.ok(existenciaService.listarExistencias(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExistenciaDetalleDTO> obtenerExistencia(@PathVariable Long id){
        return ResponseEntity.ok(existenciaService.obtenerExistencia(id));
    }

    @PostMapping
    public ResponseEntity<ExistenciaDetalleDTO> crearExistencia(@RequestBody CrearExistenciaDTO crearExistenciaDTO) {
        ExistenciaDetalleDTO nuevaExistencia = existenciaService.crearExistencia(crearExistenciaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaExistencia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExistenciaDetalleDTO> actualizarExistencia(
            @PathVariable Long id,
            @RequestBody ModificarExistenciaDTO modificarExistenciaDTO) {
        ExistenciaDetalleDTO existenciaActualizada = existenciaService.actualizarExistencia(id, modificarExistenciaDTO);
        return ResponseEntity.ok(existenciaActualizada);
    }

    @PutMapping("/{id}/mover")
    public ResponseEntity<ExistenciaDetalleDTO> moverExistencia(
            @PathVariable Long id,
            @RequestBody Long idUbicacion) {
        ExistenciaDetalleDTO existenciaActualizada = existenciaService.moverExistencia(id, idUbicacion);
        return ResponseEntity.ok(existenciaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarExistencia(@PathVariable Long id) {
        existenciaService.borrarExistencia(id);
        return ResponseEntity.noContent().build();
    }


}
