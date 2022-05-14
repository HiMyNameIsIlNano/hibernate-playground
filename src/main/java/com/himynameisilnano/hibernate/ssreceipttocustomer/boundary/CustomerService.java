package com.himynameisilnano.hibernate.ssreceipttocustomer.boundary;

import com.himynameisilnano.hibernate.ssreceipttocustomer.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import javax.validation.Valid;
import java.util.Optional;

@Transactional
class CustomerService {

    private final EntityManager entityManager;

    CustomerService(@Valid EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    Customer save(@Valid Customer entity) {
        entityManager.persist(entity);
        return entity;
    }

    Customer update(@Valid Customer entity) {
        return entityManager.merge(entity);
    }

    public Optional<Customer> findCustomerById(int id) {
        Customer entity = entityManager.find(Customer.class, id);
        return Optional.ofNullable(entity);
    }
}