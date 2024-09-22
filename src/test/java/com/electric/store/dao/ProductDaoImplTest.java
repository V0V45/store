package com.electric.store.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.electric.store.model.Product;

@SpringBootTest(
    properties = {"jdbcUrl=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1"}
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductDaoImplTest {
    @Autowired
    private ProductDao productDao;

    @BeforeEach
    void beforeEach() {
        productDao.deleteAll();
    }

    @Test
    void contextCreated() {
    }

    @Test
    void saveSavesDataToDbAndReturnsEntityWithId() {
        Product product = productDao.save(new Product("product name", 123));
        Assertions.assertThat(!product.getId().isBlank());
        Assertions.assertThat(productDao.findAll())
            .extracting("id").containsExactly(product.getId());
    }

    @Test
    void deleteAllDeletesAllData() {
        productDao.save(new Product("productAbc", 123));
        Assertions.assertThat(productDao.findAll()).isNotEmpty();
        productDao.deleteAll();
        Assertions.assertThat(productDao.findAll()).isEmpty();
    }

    @Test
    void findAllReturnsAllProducts() {
        Assertions.assertThat(productDao.findAll()).isEmpty();
        productDao.save(new Product("productAbc", 123));
        Assertions.assertThat(productDao.findAll()).isNotEmpty();
    }

    @Test
    void getByIdThrowsRuntimeExceptionIfProductWasNotFound() {
        Assertions.assertThatThrownBy(() -> productDao.getById("1")).isInstanceOf(RuntimeException.class);
    }

    @Test
    void getByIdReturnsCorrectProduct() {
        Product product = productDao.save(new Product("productAbc", 123));
        productDao.save(new Product("productDef", 789));
        Assertions.assertThat(
            productDao.getById(product.getId())
        ).isNotNull().extracting("name").isEqualTo(product.getName());
    }

    @Test
    void updateUpdatesDataInDb() {
        Product product = productDao.save(new Product("productAbc", 123));
        product.setName("new name");
        productDao.update(product);
        Assertions.assertThat(productDao.getById(product.getId()).getName()).isEqualTo("new name");
    }

    @Test
    void updateThrowsExceptionOnUpdatingNotSavedProduct() {
        Assertions.assertThatThrownBy(() -> productDao.update(new Product("productAbc", 123)))
        .isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteDeletesCorrectData() {
        Product productToKeep = productDao.save(new Product("productAbc", 123));
        Product productToDelete = productDao.save(new Product("productDef", 789));
        productDao.delete(productToDelete);
        Assertions.assertThat(productDao.getById(productToKeep.getId())).isNotNull();
        Assertions.assertThatThrownBy(() -> productDao.getById(productToDelete.getId())).isInstanceOf(RuntimeException.class);
    }
}
