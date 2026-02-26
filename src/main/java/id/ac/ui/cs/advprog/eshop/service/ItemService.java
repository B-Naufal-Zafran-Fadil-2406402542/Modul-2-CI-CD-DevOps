package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Item;

import java.util.List;

public interface ItemService<T extends Item> {
    public T create(T item);

    public List<T> findAll();

    public T getItemById(String id);

    public void update(T item);

    public void deleteById(String itemId);
}
