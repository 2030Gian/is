package com.example.tutoraibecore.tema.aplicacion;

import java.util.Optional;
import com.example.tutoraibecore.tema.dominio.Tema;
import com.example.tutoraibecore.tema.dominio.TemaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/classes/{classId}/themes")
public class TemaControlador {

    @Autowired
    private TemaRepositorio temaRepositorio;

    @PostMapping
    public ResponseEntity<String> crearTema(@RequestBody Tema tema) {
        temaRepositorio.save(tema);
        return ResponseEntity.status(201).body("Created");
    }

    @GetMapping
    public ResponseEntity<Page<Tema>> listarTemas(
            @PathVariable UUID classId,
            @RequestParam(name = "search", required = false, defaultValue = "") String searchTerm,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "20") int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("orderIndex").ascending());
        Page<Tema> temasPaginados = temaRepositorio.findByClassIdAndTitleContainingIgnoreCase(classId, searchTerm, pageable);
        return ResponseEntity.ok(temasPaginados);
    }

    @GetMapping("/{themeId}")
    public ResponseEntity<Tema> obtenerTemaPorId(
            @PathVariable UUID classId,
            @PathVariable UUID themeId) {

        Optional<Tema> temaOptional = temaRepositorio.findById(themeId);
        return temaOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{themeId}")
    public ResponseEntity<Tema> actualizarTema(
            @PathVariable UUID classId,
            @PathVariable UUID themeId,
            @RequestBody Tema temaActualizacion) {

        Optional<Tema> temaExistenteOptional = temaRepositorio.findById(themeId);

        if (temaExistenteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Tema temaExistente = temaExistenteOptional.get();

        if (temaActualizacion.getTitle() != null) {
            temaExistente.setTitle(temaActualizacion.getTitle());
        }
        if (temaActualizacion.getDescription() != null) {
            temaExistente.setDescription(temaActualizacion.getDescription());
        }
        if (temaActualizacion.getOrderIndex() != null) {
            temaExistente.setOrderIndex(temaActualizacion.getOrderIndex());
        }
        if (temaActualizacion.getSourcesJson() != null) {
            temaExistente.setSourcesJson(temaActualizacion.getSourcesJson());
        }

        Tema temaActualizado = temaRepositorio.save(temaExistente);
        return ResponseEntity.ok(temaActualizado);
    }
    @DeleteMapping("/{themeId}")
    public ResponseEntity<Void> eliminarTema(
            @PathVariable UUID classId,
            @PathVariable UUID themeId) {

        if (!temaRepositorio.existsById(themeId)) {
            return ResponseEntity.notFound().build();
        }
        temaRepositorio.deleteById(themeId);
        return ResponseEntity.noContent().build();
    }



}