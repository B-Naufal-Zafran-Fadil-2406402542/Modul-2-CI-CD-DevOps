package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.exception.ItemNotFoundException;
import id.ac.ui.cs.advprog.eshop.model.Item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ItemRepository<T extends Item> {
    private List<T> itemData = new ArrayList<>();

    public T create(T item) {

        itemData.add(item);
        return item;
    }

    public Iterator<T> findAll() {
        return itemData.iterator();
    }

    public T getItemById(String itemId) {
        for(T p : itemData) {
            if(p.getId().equals(itemId)) return p;
        }
        throw new ItemNotFoundException("Item id " + itemId + " is not found.");
    }

    public void updateItemData(T updatedItem) {
        Item item = getItemById(updatedItem.getId());
        item.setName(updatedItem.getName());
        item.setQuantity(updatedItem.getQuantity());
    }

    public void deleteItemById(String itemId) {
        itemData.removeIf(p -> p.getId().equals(itemId));
    }
}
