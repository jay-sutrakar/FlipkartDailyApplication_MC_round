package repository;

import model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemRepository {
    private List<Item> items;

    public ItemRepository() {
        this.items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItems(List<Item> newItems) {
        items.addAll(newItems);
    }
    public void addItem(Item newItem) {
        Optional<Item> item = items.stream().filter(i -> i.getBrand().equalsIgnoreCase(newItem.getBrand()) && i.getCategory().equalsIgnoreCase(newItem.getCategory())).findFirst();
        if (item.isPresent()) {
            throw new RuntimeException("Item already exists.");
        }
        items.add(newItem);
        System.out.println("new item added successfully");
    }

    public void updateItem(Item updatedItem) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getBrand().equalsIgnoreCase(updatedItem.getBrand()) && items.get(i).getCategory().equalsIgnoreCase(updatedItem.getCategory())) {
                items.add(i, updatedItem);
                return;
            }
        }
        System.out.println("Couldn't locate the item. Update item operation failed");
    }
}
