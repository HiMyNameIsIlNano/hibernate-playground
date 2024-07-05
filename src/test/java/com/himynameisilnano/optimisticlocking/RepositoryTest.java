package com.himynameisilnano.optimisticlocking;

import jakarta.persistence.LockModeType;
import org.assertj.core.api.Assertions;
import org.hibernate.testing.junit4.BaseUnitTestCase;
import org.hibernate.testing.transaction.TransactionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.himynameisilnano.EntityManagerTestUtils.forPersistenceUnitWithProperties;
import static com.himynameisilnano.PersistenceUnitPropertiesHelper.createDb;
import static com.himynameisilnano.PersistenceUnitPropertiesHelper.keepDb;

class RepositoryTest extends BaseUnitTestCase {

    @BeforeEach
    void setUp() {
        TransactionUtil.doInJPA(() -> forPersistenceUnitWithProperties("h2-dev", createDb("db-with-repo")), entityManager -> {
            var repository = new Repository(1L);

            entityManager.persist(repository);
        });
    }

    @Nested
    class SingleUser {
        @Test
        void single_user_can_save_a_repository_with_commit() {
            String uuid = "dcad2a5f-94ef-4543-9e9e-4539693c65db";

            TransactionUtil.doInJPA(() -> forPersistenceUnitWithProperties("h2-dev", keepDb("db-with-repo")), entityManager -> {
                var repository = entityManager.find(Repository.class, 1L, LockModeType.OPTIMISTIC_FORCE_INCREMENT);

                Commit commit = new Commit(UUID.fromString(uuid));
                commit.setRepository(repository);

                entityManager.persist(commit);
            });

            TransactionUtil.doInJPA(() -> forPersistenceUnitWithProperties("h2-dev", keepDb("db-with-repo")), entityManager -> {
                var commit = entityManager.createNativeQuery("select * from COMMIT where HASH = :hash", Commit.class)
                        .setParameter("hash", uuid)
                        .getSingleResult();

                Assertions.assertThat(commit).isNotNull();
            });
        }
    }

    @Nested
    class MultipleUsers {

        @Test
        void auto_increment_version_when_two_users_save_a_repository_with_commit() {
            // The repository version is 0
            Repository repositoryUser1 = TransactionUtil.doInJPA(() -> forPersistenceUnitWithProperties("h2-dev", keepDb("db-with-repo")), entityManager -> {
                return entityManager.find(Repository.class, 1L, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            });

            // User 2 comes first and updates the repository
            TransactionUtil.doInJPA(() -> forPersistenceUnitWithProperties("h2-dev", keepDb("db-with-repo")), entityManager -> {
                var repositoryUser2 = entityManager.find(Repository.class, 1L, LockModeType.OPTIMISTIC_FORCE_INCREMENT);

                Commit commit = new Commit(UUID.fromString("dcad2a5f-94ef-4543-9e9e-4539693c65db"));
                commit.setRepository(repositoryUser2);

                // The repository version is updated and set to 1
                entityManager.persist(commit);
            });

            // User 1 is a bit slower and updates the repository
            TransactionUtil.doInJPA(() -> forPersistenceUnitWithProperties("h2-dev", keepDb("db-with-repo")), entityManager -> {
                Commit commit = new Commit(UUID.fromString("4d1ea86a-6a59-4c61-a81b-2cdce6be54d2"));
                commit.setRepository(repositoryUser1);

                // The repository version is updated and set to 2
                entityManager.persist(commit);
            });

            TransactionUtil.doInJPA(() -> forPersistenceUnitWithProperties("h2-dev", keepDb("db-with-repo")), entityManager -> {
                var repository = entityManager.find(Repository.class, 1L, LockModeType.OPTIMISTIC);

                Assertions.assertThat(repository).isNotNull();
                Assertions.assertThat(repository.getVersion()).isEqualTo(2L);
            });
        }
    }
}