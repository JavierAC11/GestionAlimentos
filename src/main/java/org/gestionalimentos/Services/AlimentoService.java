package org.gestionalimentos.Services;

import lombok.Setter;
import org.gestionalimentos.DTO.alimento.AlimentoDetalleDTO;
import org.gestionalimentos.DTO.alimento.AlimentoListadoDTO;
import org.gestionalimentos.DTO.alimento.CrearAlimentoDTO;
import org.gestionalimentos.DTO.alimento.ModificarAlimentoDTO;
import org.gestionalimentos.Entities.Alimento;
import org.gestionalimentos.Repositories.AlimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AlimentoService {
    private final AlimentoRepository alimentoRepository;

    @Autowired
    public AlimentoService(AlimentoRepository alimentoRepository) {
        this.alimentoRepository = alimentoRepository;
    }

    public Page<AlimentoListadoDTO> listarAlimentos (Pageable pageable){
        Page<Alimento> alimentos;
        alimentos = alimentoRepository.findAll(pageable);
        return alimentos.map(this::convertirAAlimentoListadoDTO);
    }



    public AlimentoDetalleDTO obtenerAlimento(Long id) {
        Alimento alimento = alimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alimento no encontrado"));
        return convertirAAlimentoDetalleDTO(alimento);
    }


    public AlimentoDetalleDTO crearAlimento(CrearAlimentoDTO crearAlimentoDTO){
        Alimento alimento = new Alimento();
        alimento.setNombre(crearAlimentoDTO.getNombre());
        alimento.setTipo(crearAlimentoDTO.getTipo());
        alimento.setEstado(crearAlimentoDTO.getEstado());
        alimento.setFechaCaducidad(LocalDate.parse(crearAlimentoDTO.getFechaCaducidad()));
        alimentoRepository.save(alimento);
        return convertirAAlimentoDetalleDTO(alimento);
    }

    public void borrarAlimento(Long id) {
        if (!alimentoRepository.existsById(id)) {
            throw new RuntimeException("Alimento no encontrado");
        }
        alimentoRepository.deleteById(id);
    }

    public AlimentoDetalleDTO actualizarAlimento(Long id, ModificarAlimentoDTO modificarAlimentoDTO) {
        Alimento alimento = alimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alimento no encontrado"));

        if (modificarAlimentoDTO.getTipo() != null) alimento.setTipo(modificarAlimentoDTO.getTipo());
        if (modificarAlimentoDTO.getEstado() != null) alimento.setEstado(modificarAlimentoDTO.getEstado());
        if (modificarAlimentoDTO.getFechaCaducidad() != null) alimento.setFechaCaducidad(LocalDate.parse(modificarAlimentoDTO.getFechaCaducidad()));
        if (modificarAlimentoDTO.getNombre() != null) alimento.setNombre(modificarAlimentoDTO.getNombre());
        alimentoRepository.save(alimento);
        return convertirAAlimentoDetalleDTO(alimento);
    }

    private AlimentoDetalleDTO convertirAAlimentoDetalleDTO(Alimento alimento) {
        AlimentoDetalleDTO dto = new AlimentoDetalleDTO();
        dto.setId(alimento.getId());
        dto.setNombre(alimento.getNombre());
        dto.setTipo(alimento.getTipo());
        dto.setFechaCaducidad(String.valueOf(alimento.getFechaCaducidad()));
        dto.setEstado(alimento.getEstado());
        return dto;
    }

    private AlimentoListadoDTO convertirAAlimentoListadoDTO(Alimento alimento){
        AlimentoListadoDTO dto = new AlimentoListadoDTO();
            dto.setId(alimento.getId());
            dto.setNombre(alimento.getNombre());
            dto.setTipo(alimento.getTipo());
            dto.setFechaCaducidad(String.valueOf(alimento.getFechaCaducidad()));
            dto.setEstado(alimento.getEstado());
        return dto;
    }

}
