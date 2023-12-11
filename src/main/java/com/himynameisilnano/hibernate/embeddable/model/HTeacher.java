package com.himynameisilnano.hibernate.embeddable.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import org.hibernate.annotations.NaturalId;

/**
 * This class represents the H_TEACHER table for the embeddable domain.
 */
@Entity
@Table(name = "H_TEACHER", indexes = {
        @Index(name = "H_TEACHER_NATURAL_ID_IDX", columnList = "NATURAL_ID")
})
public class HTeacher implements TObjectReader<TTeacher> {

    @EmbeddedId
    private TObjectRef<TTeacher> ref;

    @NaturalId
    @Column(name = "NATURAL_ID")
    private String naturalId;


    @Override
    public TTeacher getTObject() {
        return ref.getTEntity();
    }

    @Deprecated
    protected HTeacher() {
        // Do not remove. For JPA.
    }

    public HTeacher(TTeacher tTeacher, String naturalId) {
        this.ref = TObjectRef.of(tTeacher);
        this.naturalId = naturalId;
    }
}