package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    private ProductServiceImpl service;

    @Mock
    private ProductRepository repository;

    @BeforeEach
    void setUp() {
        service = new ProductServiceImpl();
        service.itemRepository = repository;
    }

    @Test
    void testCreate() {
        Product product = new Product();
        when(repository.create(product)).thenReturn(product);
        Product createdProduct = service.create(product);
        assertEquals(product, createdProduct);
    }

    @Test
    void testFindAll() {
        List<Product> products = new ArrayList<>();
        when(repository.findAll()).thenReturn(products.iterator());
        List<Product> foundProducts = service.findAll();
        assertEquals(products, foundProducts);
    }

    @Test
    void testGetProductById() {
        Product product = new Product();
        product.setId("test-id");
        when(repository.getItemById("test-id")).thenReturn(product);
        Product foundProduct = service.getItemById("test-id");
        assertEquals(product, foundProduct);
    }

    @Test
    void testUpdate() {
        Product product = new Product();
        service.update(product);
    }

    @Test
    void testDelete() {
        service.deleteById("test-id");
    }
}
