package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Item;
import id.ac.ui.cs.advprog.eshop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractItemService<T extends Item> implements ItemService<T>{
    @Autowired
    protected ItemRepository<T> itemRepository;

    public T create(T item) {
        itemRepository.create(item);
        return item;
    }
    
    public List<T> findAll() {
        Iterator<T> itemIterator = itemRepository.findAll();
        List<T> allItem = new ArrayList<>();
        itemIterator.forEachRemaining(allItem::add);
        return allItem;
    }
    
    public T getItemById(String id) {
        return itemRepository.getItemById(id);
    }
    
    public void update(T item) {
        itemRepository.updateItemData(item);
    }
    
    public void deleteById(String itemId) {
        itemRepository.deleteItemById(itemId);
    }

}
