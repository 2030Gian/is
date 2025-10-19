package com.example.tutoraibecore.tema.dominio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TemaRepositorio extends JpaRepository<Tema, UUID> {


    Page<Tema> findByClassIdAndTitleContainingIgnoreCase(UUID classId, String termino, Pageable pageable);

}