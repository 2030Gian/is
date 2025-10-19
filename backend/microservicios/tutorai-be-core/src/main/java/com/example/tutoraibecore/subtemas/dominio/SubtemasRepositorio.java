package com.example.tutoraibecore.subtemas.dominio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubtemasRepositorio extends JpaRepository<Subtema, UUID> {
}
