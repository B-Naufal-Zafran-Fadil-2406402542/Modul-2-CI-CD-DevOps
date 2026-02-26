package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.exception.ItemNotFoundException;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setName("Sampo Cap Usep");
        product2.setQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct1 = productIterator.next();
        assertEquals(product1.getId(), savedProduct1.getId());
        assertTrue(productIterator.hasNext());
        Product savedProduct2 = productIterator.next();
        assertEquals(product2.getId(), savedProduct2.getId());
    }

    @Test
    void testgetByIdSuccess() {
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setName("Sampo Cap Usep");
        product2.setQuantity(50);
        productRepository.create(product2);

        Product foundProduct = productRepository.getItemById("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        assertEquals(product2, foundProduct);
    }

    @Test
    void testUpdateProductSuccess() {
        // Skenario Positif: Berhasil mengupdate data produk yang ada
        Product product = new Product();
        product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setName("Sampo Cap Bambang");
        product.setQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setName("Sampo Cap Usep");
        updatedProduct.setQuantity(50);
        productRepository.updateItemData(updatedProduct); // Menyesuaikan nama method

        Product result = productRepository.getItemById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals("Sampo Cap Usep", result.getName());
        assertEquals(50, result.getQuantity());
    }

    @Test
    void testUpdateProductNotFound() {
        // Skenario Negatif: Gagal mengupdate karena ID tidak ditemukan
        Product updatedProduct = new Product();
        updatedProduct.setId("id-salah");
        updatedProduct.setName("Produk Ghoib");
        updatedProduct.setQuantity(0);

        assertThrows(ItemNotFoundException.class, () ->
                productRepository.updateItemData(updatedProduct)
        );
    }

    @Test
    void testDeleteProductSuccess() {
        // Skenario Positif: Berhasil menghapus produk
        Product product = new Product();
        product.setId("id-hapus");
        product.setName("Produk Hapus");
        productRepository.create(product);

        productRepository.deleteItemById("id-hapus"); // Menyesuaikan nama method

        // Memastikan produk sudah benar-benar hilang
        assertThrows(ItemNotFoundException.class, () ->
                productRepository.getItemById("id-hapus")
        );
    }

    @Test
    void testgetByIdNotFound() {
        // Skenario Negatif: Mencari ID yang tidak ada
        assertThrows(ItemNotFoundException.class, () ->
                productRepository.getItemById("id-acak")
        );
    }

    @Test
    void testgetByIdWhenEmpty() {
        assertThrows(ItemNotFoundException.class, () ->
                productRepository.getItemById("id-acak")
        );
    }
}