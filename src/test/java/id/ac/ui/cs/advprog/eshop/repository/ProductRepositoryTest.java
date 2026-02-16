package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.exception.ProductNotFoundException;
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
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct1 = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct1.getProductId());
        assertTrue(productIterator.hasNext());
        Product savedProduct2 = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct2.getProductId());
    }

    @Test
    void testGetProductByIdSuccess() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Product foundProduct = productRepository.getProductById("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        assertEquals(product2, foundProduct);
    }

    @Test
    void testUpdateProductSuccess() {
        // Skenario Positif: Berhasil mengupdate data produk yang ada
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Usep");
        updatedProduct.setProductQuantity(50);
        productRepository.updateProductData(updatedProduct); // Menyesuaikan nama method

        Product result = productRepository.getProductById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals("Sampo Cap Usep", result.getProductName());
        assertEquals(50, result.getProductQuantity());
    }

    @Test
    void testUpdateProductNotFound() {
        // Skenario Negatif: Gagal mengupdate karena ID tidak ditemukan
        Product updatedProduct = new Product();
        updatedProduct.setProductId("id-salah");
        updatedProduct.setProductName("Produk Ghoib");
        updatedProduct.setProductQuantity(0);

        assertThrows(ProductNotFoundException.class, () ->
                productRepository.updateProductData(updatedProduct)
        );
    }

    @Test
    void testDeleteProductSuccess() {
        // Skenario Positif: Berhasil menghapus produk
        Product product = new Product();
        product.setProductId("id-hapus");
        product.setProductName("Produk Hapus");
        productRepository.create(product);

        productRepository.deleteProductById("id-hapus"); // Menyesuaikan nama method

        // Memastikan produk sudah benar-benar hilang
        assertThrows(ProductNotFoundException.class, () ->
                productRepository.getProductById("id-hapus")
        );
    }

    @Test
    void testGetProductByIdNotFound() {
        // Skenario Negatif: Mencari ID yang tidak ada
        assertThrows(ProductNotFoundException.class, () ->
                productRepository.getProductById("id-acak")
        );
    }

    @Test
    void testGetProductByIdWhenEmpty() {
        assertThrows(ProductNotFoundException.class, () ->
                productRepository.getProductById("id-acak")
        );
    }
}