package com.himynameisilnano.hibernate.embeddable.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents the id for the embeddable domain.
 */
/*@Embeddable
public record LeftToRightId<T1, T2>(@ManyToOne(fetch = FetchType.EAGER)
                                    @JoinColumn(name = "left_id")
                                    T1 left,
                                    @ManyToOne(fetch = FetchType.EAGER)
                                    @JoinColumn(name = "right_id")
                                    T2 right) implements Serializable {
}*/

/**
 * This class represents the BAR table for the embeddable domain.
 */
@Embeddable
public final class LeftToRightId<T1, T2> implements Serializable {

    @Serial
    private static final long serialVersionUID = 0L;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "left_id")
    private T1 left;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "right_id")
    private T2 right;

    public LeftToRightId(T1 left, T2 right) {
        this.left = left;
        this.right = right;
    }

    public LeftToRightId() {

    }

    public T1 left() {
        return left;
    }

    public T2 right() {
        return right;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (LeftToRightId) obj;
        return Objects.equals(this.left, that.left) &&
                Objects.equals(this.right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public String toString() {
        return "LeftToRightId[" +
                "left=" + left + ", " +
                "right=" + right + ']';
    }
}
