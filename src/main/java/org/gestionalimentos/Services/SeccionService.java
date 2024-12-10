package org.gestionalimentos.Services;

import org.gestionalimentos.DTO.seccion.CrearSeccionDTO;
import org.gestionalimentos.DTO.seccion.SeccionDetalleDTO;
import org.gestionalimentos.DTO.seccion.SeccionListadaDTO;
import org.gestionalimentos.Entities.Seccion;
import org.gestionalimentos.Repositories.AlmacenRepository;
import org.gestionalimentos.Repositories.RecipienteRepository;
import org.gestionalimentos.Repositories.SeccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SeccionService {
    private final SeccionRepository seccionRepository;
    private final AlmacenRepository almacenRepository;
    private final RecipienteRepository recipienteRepository;

    @Autowired
    public SeccionService(SeccionRepository seccionRepository, AlmacenRepository almacenRepository,
                          RecipienteRepository recipienteRepository)
    {
        this.seccionRepository = seccionRepository;
        this.almacenRepository = almacenRepository;
        this.recipienteRepository = recipienteRepository;
    }

    public Page<SeccionListadaDTO> listarSecciones(Pageable pageable){
        Page<Seccion> secciones;
        secciones = seccionRepository.findAll(pageable);
        return secciones.map(
                this::convertirASeccionListadaDTO);
    }

    public SeccionDetalleDTO obtenerSeccion(Long id){
        Seccion seccion = seccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seccion no encontrada"));
        return convertirASeccionDetalleDTO(seccion);
    }

    public SeccionDetalleDTO crearSeccion(CrearSeccionDTO seccionDetalleDTO){
        Seccion seccion = new Seccion();
        seccion.setNombre(seccionDetalleDTO.getNombre());
        seccion.setLimite(seccionDetalleDTO.getLimite());
        seccion.setAccesibilidad(seccionDetalleDTO.getAccesibilidad());
        seccion.setAlmacen(almacenRepository.findById(seccionDetalleDTO.getAlmacenId())
                .orElseThrow(() -> new RuntimeException("Almacen no encontrado")));
        seccion.setRecipientes(seccionDetalleDTO.getRecipientesId().stream()
            .map(recipienteId -> recipienteRepository.findById(recipienteId)
                .orElseThrow(() -> new RuntimeException("Recipiente no encontrado")))
            .toList());
        seccionRepository.save(seccion);
        return convertirASeccionDetalleDTO(seccion);
    }

    public SeccionDetalleDTO actualizarSeccion(Long id, CrearSeccionDTO seccionDetalleDTO){
        Seccion seccion = seccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seccion no encontrada"));
        seccion.setNombre(seccionDetalleDTO.getNombre());
        seccion.setLimite(seccionDetalleDTO.getLimite());
        seccion.setAccesibilidad(seccionDetalleDTO.getAccesibilidad());
        seccion.setAlmacen(almacenRepository.findById(seccionDetalleDTO.getAlmacenId())
                .orElseThrow(() -> new RuntimeException("Almacen no encontrado")));
        seccion.setRecipientes(seccionDetalleDTO.getRecipientesId().stream()
            .map(recipienteId -> recipienteRepository.findById(recipienteId)
                .orElseThrow(() -> new RuntimeException("Recipiente no encontrado")))
            .toList());
        seccionRepository.save(seccion);
        return convertirASeccionDetalleDTO(seccion);
    }

    public void borrarSeccion(Long id){
        if (!seccionRepository.existsById(id)) {
            throw new RuntimeException("Seccion no encontrada");
        }
        seccionRepository.deleteById(id);
    }

    private SeccionDetalleDTO convertirASeccionDetalleDTO(Seccion seccion) {
        SeccionDetalleDTO seccionDetalleDTO = new SeccionDetalleDTO();
        seccionDetalleDTO.setId(seccion.getId());
        seccionDetalleDTO.setLimite(seccion.getLimite());
        seccionDetalleDTO.setNombre(seccion.getNombre());
        seccionDetalleDTO.setAccesibilidad(seccion.getAccesibilidad());
        seccionDetalleDTO.setAlmacenId(seccion.getAlmacen().getId());
        seccionDetalleDTO.setRecipientesId(seccion.getRecipientes().stream().map(recipiente -> recipiente.getId()).toList());
        return seccionDetalleDTO;
    }

    public SeccionListadaDTO convertirASeccionListadaDTO(Seccion seccion){
        SeccionListadaDTO seccionListadaDTO = new SeccionListadaDTO();
        seccionListadaDTO.setId(seccion.getId());
        seccionListadaDTO.setLimite(seccion.getLimite());
        seccionListadaDTO.setNombre(seccion.getNombre());
        seccionListadaDTO.setAccesibilidad(seccion.getAccesibilidad());
        seccionListadaDTO.setAlmacenId(seccion.getAlmacen().getId());
        seccionListadaDTO.setRecipientesId(seccion.getRecipientes().stream().map(recipiente -> recipiente.getId()).toList());
        return seccionListadaDTO;
    }
}
