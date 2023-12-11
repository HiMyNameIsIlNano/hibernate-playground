package com.himynameisilnano.hibernate.embeddable.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TObjectRef<T> implements Serializable {

    @NotNull
    @Column
    private long dhln;

    @ManyToOne(fetch = FetchType.LAZY)
    private T tEntity;

    protected TObjectRef() {
        // Do not remove. For JPA.
    }

    private TObjectRef(T tEntity) {
        Objects.requireNonNull(tEntity);

        this.tEntity = tEntity;
    }

    public static <T> TObjectRef<T> of(T t) {
        return new TObjectRef<>(t);
    }

    public T getTEntity() {
        return tEntity;
    }

    public long getDhln() {
        return dhln;
    }
}
