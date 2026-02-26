package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Item;
import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;

public interface ProductService extends ItemService<Product> {
    public Product create(Product product);
    public List<Product> findAll();
    public Product getItemById(String id);
    public void update(Product updatedProduct);
    public void deleteById(String id);
}
