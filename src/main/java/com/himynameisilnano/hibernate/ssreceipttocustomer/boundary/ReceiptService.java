package com.himynameisilnano.hibernate.ssreceipttocustomer.boundary;

import com.himynameisilnano.hibernate.ssreceipttocustomer.entity.Receipt;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Transactional
class ReceiptService {

    private final EntityManager entityManager;

    ReceiptService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    Receipt save(@Valid Receipt entity) {
        entityManager.persist(entity);
        return entity;
    }

    Receipt update(@Valid Receipt entity) {
        return entityManager.merge(entity);
    }

    public Optional<Receipt> findReceiptById(int id) {
        Receipt entity = entityManager.find(Receipt.class, id);
        return Optional.ofNullable(entity);
    }

    public List<Receipt> findAllReceipts() {
        return entityManager.createQuery("select r from Receipt r", Receipt.class)
                .getResultList();
    }
}