package com.himynameisilnano.optimisticlocking;

import jakarta.persistence.LockModeType;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.RollbackException;
import org.assertj.core.api.Assertions;
import org.hibernate.testing.transaction.TransactionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.himynameisilnano.EntityManagerTestUtils.forPersistenceUnitWithProperties;
import static com.himynameisilnano.PersistenceUnitPropertiesHelper.createDb;
import static com.himynameisilnano.PersistenceUnitPropertiesHelper.keepDb;

class ProductTest {

    @BeforeEach
    void setUp() {
        TransactionUtil.doInJPA(() -> forPersistenceUnitWithProperties("h2-dev", createDb("db-with-product")), entityManager -> {
            var product = new Product(1L, "The best product in the world");

            entityManager.persist(product);
        });
    }

    @Nested
    class SingleUser {
        @Test
        void can_save_product() {
            TransactionUtil.doInJPA(() -> forPersistenceUnitWithProperties("h2-dev", keepDb("db-with-product")), entityManager -> {
                var product = entityManager.find(Product.class, 1L);

                Assertions.assertThat(product.getVersion()).isEqualTo(0L);

                product.setDescription("The best product in the world!");
            });

            TransactionUtil.doInJPA(() -> forPersistenceUnitWithProperties("h2-dev", keepDb("db-with-product")), entityManager -> {
                var product = entityManager.find(Product.class, 1L);

                Assertions.assertThat(product.getVersion()).isEqualTo(1L);
                Assertions.assertThat(product.getDescription()).isEqualTo("The best product in the world!");
            });
        }
    }

    @Nested
    class MultipleUser {
        @Test
        void two_users_cannot_save_same_product_at_the_same_time() {
            // User 1 is retrieving the product
            var productUser1 = TransactionUtil.doInJPA(() -> forPersistenceUnitWithProperties("h2-dev", keepDb("db-with-product")), entityManager -> {
                return entityManager.find(Product.class, 1L, LockModeType.OPTIMISTIC);
            });

            Assertions.assertThat(productUser1.getVersion()).isZero();

            // User 2 retrieves the same product as User 1 and updates its description
            TransactionUtil.doInJPA(() -> forPersistenceUnitWithProperties("h2-dev", keepDb("db-with-product")), entityManager -> {
                var product = entityManager.find(Product.class, 1L, LockModeType.OPTIMISTIC);

                Assertions.assertThat(product.getVersion()).isZero();

                product.setDescription("The best product in the world!");
            });

            // User 1 proceeds with his update and tries to change the description as well
            TransactionUtil.doInJPA(() -> forPersistenceUnitWithProperties("h2-dev", keepDb("db-with-product")), entityManager -> {
                Assertions.assertThat(productUser1.getVersion()).isZero();

                productUser1.setDescription("The best product in the world!!");

                // We need to reattach the product to this entity manager to cause the optimistic lock exception
                Assertions.assertThatThrownBy(() -> entityManager.merge(productUser1))
                        .isInstanceOf(OptimisticLockException.class)
                        .hasMessageContaining("Row was updated or deleted by another transaction");
            });

            TransactionUtil.doInJPA(() -> forPersistenceUnitWithProperties("h2-dev", keepDb("db-with-product")), entityManager -> {
                var productFromDb = entityManager.find(Product.class, 1L, LockModeType.OPTIMISTIC);

                Assertions.assertThat(productFromDb.getVersion()).isEqualTo(1L);
                Assertions.assertThat(productFromDb.getDescription()).isEqualTo("The best product in the world!");
            });
        }

        @Test
        void user_1_saves_product_then_user_2_saves_product() {
            // User 1 is retrieving and updating the product
            TransactionUtil.doInJPA(() -> forPersistenceUnitWithProperties("h2-dev", keepDb("db-with-product")), entityManager -> {
                var product = entityManager.find(Product.class, 1L, LockModeType.OPTIMISTIC);

                Assertions.assertThat(product.getVersion()).isZero();

                product.setDescription("The best product in the world (User 1)!");
            });

            // User 2 retrieves the same product as User 1 and updates its description
            TransactionUtil.doInJPA(() -> forPersistenceUnitWithProperties("h2-dev", keepDb("db-with-product")), entityManager -> {
                var product = entityManager.find(Product.class, 1L, LockModeType.OPTIMISTIC);

                Assertions.assertThat(product.getVersion()).isEqualTo(1L);
                Assertions.assertThat(product.getDescription()).isEqualTo("The best product in the world (User 1)!");

                product.setDescription("The best product in the world (User 2)!");
            });

            TransactionUtil.doInJPA(() -> forPersistenceUnitWithProperties("h2-dev", keepDb("db-with-product")), entityManager -> {
                var productFromDb = entityManager.find(Product.class, 1L, LockModeType.OPTIMISTIC);

                Assertions.assertThat(productFromDb.getVersion()).isEqualTo(2L);
                Assertions.assertThat(productFromDb.getDescription()).isEqualTo("The best product in the world (User 2)!");
            });
        }

        @Test
        void optimistic_lock_does_not_increment_version() {
            // User 1 is retrieving the product
            var productUser1 = TransactionUtil.doInJPA(() -> forPersistenceUnitWithProperties("h2-dev", keepDb("db-with-product")), entityManager -> {
                // OPTIMISTIC_FORCE_INCREMENT always increments the version
                return entityManager.find(Product.class, 1L, LockModeType.OPTIMISTIC);
            });

            // User 1 is retrieving the product
            var productUser2 = TransactionUtil.doInJPA(() -> forPersistenceUnitWithProperties("h2-dev", keepDb("db-with-product")), entityManager -> {
                // OPTIMISTIC_FORCE_INCREMENT always increments the version
                return entityManager.find(Product.class, 1L, LockModeType.OPTIMISTIC);
            });

            Assertions.assertThat(productUser1.getVersion()).isZero();
            Assertions.assertThat(productUser2.getVersion()).isZero();
        }

        @Test
        void optimistic_force_increment_lock_increments_version_when_transaction_is_committed() {
            // User 1 is retrieving the product
            var productTransaction1 = TransactionUtil.doInJPA(() -> forPersistenceUnitWithProperties("h2-dev", keepDb("db-with-product")), entityManager -> {
                // OPTIMISTIC_FORCE_INCREMENT always increments the version when the transaction is committed
                Product product = entityManager.find(Product.class, 1L, LockModeType.OPTIMISTIC_FORCE_INCREMENT);

                Assertions.assertThat(product.getVersion()).isZero();

                return product;
            });

            // User 2 is retrieving the product
            var productTransaction2 = TransactionUtil.doInJPA(() -> forPersistenceUnitWithProperties("h2-dev", keepDb("db-with-product")), entityManager -> {
                // OPTIMISTIC_FORCE_INCREMENT increments the version when the transaction is committed
                Product product = entityManager.find(Product.class, 1L, LockModeType.OPTIMISTIC_FORCE_INCREMENT);

                // The version has been incremented at the end of the previous _doInJpa_ block
                Assertions.assertThat(product.getVersion()).isEqualTo(1L);

                return product;
            });

            Assertions.assertThat(productTransaction1.getVersion()).isEqualTo(1L);
            Assertions.assertThat(productTransaction2.getVersion()).isEqualTo(2L);
        }

        @Test
        void slow_user_cannot_save_product_as_his_version_is_stale() {
            // User 1 is retrieving the product
            Assertions.assertThatThrownBy(() -> TransactionUtil.doInJPA(() -> forPersistenceUnitWithProperties("h2-dev", keepDb("db-with-product")), entityManagerT1 -> {
                Product productT1 = entityManagerT1.find(Product.class, 1L, LockModeType.OPTIMISTIC_FORCE_INCREMENT);

                Assertions.assertThat(productT1.getVersion()).isZero();

                // User 2 is retrieving the product
                var productTransaction2 = TransactionUtil.doInJPA(() -> forPersistenceUnitWithProperties("h2-dev", keepDb("db-with-product")), entityManagerT2 -> {
                    Product productT2 = entityManagerT2.find(Product.class, 1L, LockModeType.OPTIMISTIC_FORCE_INCREMENT);

                    // The version has not been incremented yet as the previous transaction has not been committed
                    Assertions.assertThat(productT2.getVersion()).isZero();

                    return productT2;
                });

                Assertions.assertThat(productTransaction2.getVersion()).isEqualTo(1L);
                return productT1;

                // Transaction 1 is committed when returning from this block
            })).isInstanceOf(RollbackException.class);
        }
    }

}