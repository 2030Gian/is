package com.example.tutoraibecore.tema.dominio;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.Type;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.validation.constraints.Min;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.example.tutoraibecore.subtemas.dominio.Subtema;
import java.time.OffsetDateTime;



@Entity
@Table(name = "tema", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"class_id", "order_index"}),
        @UniqueConstraint(columnNames = {"class_id", "title"})
})

public class Tema {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_tema", nullable = false)
    private UUID id;

    @Column(name = "class_id", nullable = false)
    private UUID classId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String description;

    @Min(1)
    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @Type(JsonBinaryType.class)
    @Column(name = "sources_json", columnDefinition = "jsonb")
    private String sourcesJson = "[]";

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;


    @OneToMany(
            mappedBy = "tema",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Subtema> subtemas = new ArrayList<>();

    public  Tema() {
    }

    public Tema(OffsetDateTime updatedAt, OffsetDateTime createdAt, UUID createdBy, String sourcesJson, String description, Integer orderIndex, String title, UUID classId, UUID id, List<Subtema> subtemas) {
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.sourcesJson = sourcesJson;
        this.description = description;
        this.orderIndex = orderIndex;
        this.title = title;
        this.classId = classId;
        this.id = id;
        this.subtemas = subtemas;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getClassId() {
        return classId;
    }

    public void setClassId(UUID classId) {
        this.classId = classId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getSourcesJson() {
        return sourcesJson;
    }

    public void setSourcesJson(String sourcesJson) {
        this.sourcesJson = sourcesJson;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Subtema> getSubtemas() {
        return subtemas;
    }

    public void setSubtemas(List<Subtema> subtemas) {
        this.subtemas = subtemas;
    }



}
