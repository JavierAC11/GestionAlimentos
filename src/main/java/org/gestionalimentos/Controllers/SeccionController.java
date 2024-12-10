package org.gestionalimentos.Controllers;

import org.gestionalimentos.DTO.seccion.CrearSeccionDTO;
import org.gestionalimentos.DTO.seccion.SeccionDetalleDTO;
import org.gestionalimentos.DTO.seccion.SeccionListadaDTO;
import org.gestionalimentos.Services.SeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secciones")

public class SeccionController {

    private final SeccionService seccionService;

    @Autowired
    public SeccionController(SeccionService seccionService) {
        this.seccionService = seccionService;
    }

    @GetMapping
    ResponseEntity<Page<SeccionListadaDTO>> listarSecciones(Pageable pageable){
        return ResponseEntity.ok(seccionService.listarSecciones(pageable));
    }

    @GetMapping("/{id}")
    ResponseEntity<SeccionDetalleDTO> obtenerSeccion(@PathVariable Long id){
        return ResponseEntity.ok(seccionService.obtenerSeccion(id));
    }

    @PostMapping
    ResponseEntity<SeccionDetalleDTO> crearSeccion(@RequestBody CrearSeccionDTO seccionDetalleDTO){
        SeccionDetalleDTO nuevaSeccion = seccionService.crearSeccion(seccionDetalleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSeccion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeccionDetalleDTO> actualizarSeccion(
            @PathVariable Long id,
            @RequestBody CrearSeccionDTO seccionDetalleDTO) {
        SeccionDetalleDTO seccionActualizada = seccionService.actualizarSeccion(id, seccionDetalleDTO);
        return ResponseEntity.ok(seccionActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSeccion(@PathVariable Long id) {
        seccionService.borrarSeccion(id);
        return ResponseEntity.noContent().build();
    }

}
