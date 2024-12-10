package org.gestionalimentos.Services;

import lombok.Setter;
import org.gestionalimentos.DTO.alimento.AlimentoDetalleDTO;
import org.gestionalimentos.DTO.alimento.AlimentoListadoDTO;
import org.gestionalimentos.DTO.alimento.CrearAlimentoDTO;
import org.gestionalimentos.DTO.alimento.ModificarAlimentoDTO;
import org.gestionalimentos.Entities.Alimento;
import org.gestionalimentos.Entities.Recipiente;
import org.gestionalimentos.Enums.CategoriaSelect;
import org.gestionalimentos.Enums.EstadoSelect;
import org.gestionalimentos.Repositories.AlimentoRepository;
import org.gestionalimentos.Repositories.RecipienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AlimentoService {
    private final AlimentoRepository alimentoRepository;
    private final RecipienteRepository recipienteRepository;

    @Autowired
    public AlimentoService(AlimentoRepository alimentoRepository, RecipienteRepository recipienteRepository) {
        this.alimentoRepository = alimentoRepository;
        this.recipienteRepository = recipienteRepository;
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
        alimento.setPerecedero(crearAlimentoDTO.getPerecedero());
        alimento.setAbierto(crearAlimentoDTO.getAbierto());
        alimento.setTamanio(crearAlimentoDTO.getTamanio());
        alimento.setFechaCaducidad(LocalDate.parse(crearAlimentoDTO.getFechaCaducidad()));
        alimento.setCategoria(CategoriaSelect.valueOf(crearAlimentoDTO.getCategoria()));
        alimento.setEstado(EstadoSelect.valueOf(crearAlimentoDTO.getEstado()));
        alimento.setRecipiente(recipienteRepository.findById(crearAlimentoDTO.getRecipienteId())
            .orElseThrow(() -> new RuntimeException("Recipiente no encontrado")));
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

        if (modificarAlimentoDTO.getCategoria() != null) alimento.setCategoria(CategoriaSelect.valueOf(modificarAlimentoDTO.getCategoria()));
        if (modificarAlimentoDTO.getEstado() != null) alimento.setEstado(EstadoSelect.valueOf(modificarAlimentoDTO.getEstado()));
        if (modificarAlimentoDTO.getFechaCaducidad() != null) alimento.setFechaCaducidad(LocalDate.parse(modificarAlimentoDTO.getFechaCaducidad()));
        if (modificarAlimentoDTO.getNombre() != null) alimento.setNombre(modificarAlimentoDTO.getNombre());
        if (modificarAlimentoDTO.getPerecedero() != null) alimento.setPerecedero(modificarAlimentoDTO.getPerecedero());
        if (modificarAlimentoDTO.getAbierto() != null) alimento.setAbierto(modificarAlimentoDTO.getAbierto());
        if (modificarAlimentoDTO.getTamanio() != null) alimento.setTamanio(modificarAlimentoDTO.getTamanio());
        if (modificarAlimentoDTO.getRecipienteId() != null) {
            alimento.setRecipiente(recipienteRepository.findById(modificarAlimentoDTO.getRecipienteId())
                    .orElseThrow(() -> new RuntimeException("Recipiente no encontrado")));
        }
        alimentoRepository.save(alimento);
        return convertirAAlimentoDetalleDTO(alimento);
    }

    private AlimentoDetalleDTO convertirAAlimentoDetalleDTO(Alimento alimento) {
        AlimentoDetalleDTO dto = new AlimentoDetalleDTO();
        dto.setId(alimento.getId());
        dto.setNombre(alimento.getNombre());
        dto.setPerecedero(alimento.getPerecedero());
        dto.setAbierto(alimento.getAbierto());
        dto.setTamanio(alimento.getTamanio());
        dto.setFechaCaducidad(alimento.getFechaCaducidad());
        dto.setCategoria(alimento.getCategoria());
        dto.setEstado(alimento.getEstado());
        dto.setIdRecipiente(alimento.getRecipiente().getId());
        return dto;
    }

    private AlimentoListadoDTO convertirAAlimentoListadoDTO(Alimento alimento){
        AlimentoListadoDTO dto = new AlimentoListadoDTO();
            dto.setId(alimento.getId());
            dto.setNombre(alimento.getNombre());
            dto.setPerecedero(alimento.getPerecedero());
            dto.setAbierto(alimento.getAbierto());
            dto.setTamanio(alimento.getTamanio());
            dto.setFechaCaducidad(alimento.getFechaCaducidad());
            dto.setCategoria(alimento.getCategoria());
            dto.setEstado(alimento.getEstado());
            dto.setIdRecipiente(alimento.getRecipiente().getId());
        return dto;
    }

}
