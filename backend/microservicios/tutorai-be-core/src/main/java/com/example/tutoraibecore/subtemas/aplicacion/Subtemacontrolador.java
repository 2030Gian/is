package com.example.tutoraibecore.subtemas.aplicacion;
import com.example.tutoraibecore.subtemas.dominio.Subtema;
import com.example.tutoraibecore.subtemas.dominio.SubtemasRepositorio;
import com.example.tutoraibecore.tema.dominio.Tema;
import com.example.tutoraibecore.tema.dominio.TemaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
@RestController
@RequestMapping("/api/v1/classes/{classId}/themes/{themeId}/subthemes")
public class Subtemacontrolador {
    @Autowired
    private SubtemasRepositorio subtemasRepositorio;

    @Autowired
    private TemaRepositorio temaRepositorio;


    @PostMapping
    public ResponseEntity<Subtema> createSubtema(
            @PathVariable UUID classId,
            @PathVariable UUID themeId,
            @RequestBody Subtema subtemaRequest
    ) {
        // debo recordarme que cuando tenga que unir el microservicio usuario tengo que aquí realizar
        // esta validación
        UUID userId = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");
        subtemaRequest.setCreatedBy(userId);

        Tema temaPadre = temaRepositorio.findById(themeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tema no encontrado con id: " + themeId));

        if (!temaPadre.getClassId().equals(classId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tema con id '" + themeId + "' no pertenece a la clase con id '" + classId + "'");
        }
        subtemaRequest.setTema(temaPadre);
        Subtema nuevoSubtema = subtemasRepositorio.save(subtemaRequest);
        return new ResponseEntity<>(nuevoSubtema, HttpStatus.CREATED);
    }

}










