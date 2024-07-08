package com.himynameisilnano.optimisticlocking;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import org.hibernate.annotations.NaturalId;

import java.util.Objects;
import java.util.UUID;

/**
 * This class represents the COMMIT table for the optimistilocking domain.
 */
@Entity
@Table(name = "COMMIT")
public class Commit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMIT_ID_GEN")
    @SequenceGenerator(name = "COMMIT_ID_GEN")
    private Long id;

    @NaturalId
    @Column(name = "HASH")
    private UUID hash;

    @ManyToOne
    private Repository repository;

    protected Commit() {
        // Do not remove. For JPA.
    }

    public Commit(UUID hash) {
        this.hash = hash;
    }

    public Long getId() {
        return id;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commit commit)) return false;
        return Objects.equals(id, commit.id) && Objects.equals(hash, commit.hash) && Objects.equals(repository, commit.repository);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hash, repository);
    }
}