package org.gestionalimentos.Controllers;

import org.gestionalimentos.DTO.alimento.AlimentoDetalleDTO;
import org.gestionalimentos.DTO.alimento.AlimentoListadoDTO;
import org.gestionalimentos.DTO.alimento.CrearAlimentoDTO;
import org.gestionalimentos.DTO.alimento.ModificarAlimentoDTO;
import org.gestionalimentos.Entities.Alimento;
import org.gestionalimentos.Services.AlimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alimentos")
public class AlimentoController {

    private final AlimentoService alimentoService;

    @Autowired
    public AlimentoController(AlimentoService alimentoService) {
        this.alimentoService = alimentoService;
    }

    @GetMapping
    ResponseEntity<Page<AlimentoListadoDTO>> listarAlimentos(Pageable pageable){
        return ResponseEntity.ok(alimentoService.listarAlimentos(pageable));
    }

    @GetMapping("/{id}")
    ResponseEntity<AlimentoDetalleDTO> obtenerAlimento (@PathVariable Long id){
        return ResponseEntity.ok(alimentoService.obtenerAlimento(id));
    }

    @PostMapping
    ResponseEntity<AlimentoDetalleDTO> crearAlimento (@RequestBody CrearAlimentoDTO crearAlimentoDTO){
        AlimentoDetalleDTO nuevoAlimento = alimentoService.crearAlimento(crearAlimentoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAlimento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlimentoDetalleDTO> actualizarAlimento(
            @PathVariable Long id,
            @RequestBody ModificarAlimentoDTO modificarAlimentoDTO) {
        AlimentoDetalleDTO alimentoActualizado = alimentoService.actualizarAlimento(id, modificarAlimentoDTO);
        return ResponseEntity.ok(alimentoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlimento(@PathVariable Long id) {
        alimentoService.borrarAlimento(id);
        return ResponseEntity.noContent().build();
    }
}
