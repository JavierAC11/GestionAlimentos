package org.gestionalimentos.Controllers;

import org.gestionalimentos.DTO.ubicacion.CrearUbicacionDTO;
import org.gestionalimentos.DTO.ubicacion.ModificarUbicacionDTO;
import org.gestionalimentos.DTO.ubicacion.UbicacionDetalleDTO;
import org.gestionalimentos.DTO.ubicacion.UbicacionListadoDTO;
import org.gestionalimentos.Services.UbicacionService;
import org.gestionalimentos.exceptions.RecursoNoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ubicaciones")
public class UbicacionController {

    private final UbicacionService ubicacionService;

    @Autowired
    public UbicacionController(UbicacionService ubicacionService) {
        this.ubicacionService = ubicacionService;
    }

    @GetMapping
    public ResponseEntity<Page<UbicacionListadoDTO>> listarUbicaciones(Pageable pageable){
        return ResponseEntity.ok(ubicacionService.listarUbicaciones(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UbicacionDetalleDTO> obtenerUbicacion(@PathVariable Long id){
        try {
            return ResponseEntity.ok(ubicacionService.obtenerUbicacion(id));
        }
        catch (RecursoNoEncontrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @PostMapping
    public ResponseEntity<UbicacionDetalleDTO> crearUbicacion(@RequestBody CrearUbicacionDTO crearUbicacionDTO) {
        UbicacionDetalleDTO nuevaUbicacion = ubicacionService.crearUbicacion(crearUbicacionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaUbicacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UbicacionDetalleDTO> actualizarUbicacion(
            @PathVariable Long id,
            @RequestBody ModificarUbicacionDTO modificarUbicacionDTO) {
        UbicacionDetalleDTO ubicacionActualizada = ubicacionService.actualizarUbicacion(id, modificarUbicacionDTO);
        return ResponseEntity.ok(ubicacionActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUbicacion(@PathVariable Long id) {
        ubicacionService.borrarUbicacion(id);
        return ResponseEntity.noContent().build();
    }


}
