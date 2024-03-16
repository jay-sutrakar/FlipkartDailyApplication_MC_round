package service;

import model.Item;
import repository.ItemRepository;

import java.util.List;
import java.util.Optional;

public class ItemService {
    private ItemRepository itemRepository;
    public ItemService() {
        this.itemRepository = new ItemRepository();
    }

    public Item getItemUsingBrandAndCategoryName(String brandName, String categoryName) {
        if (categoryName == null && brandName == null) {
            throw new RuntimeException("For search at least category or brand name is required.");
        }
        List<Item> items = itemRepository.getItems();
        Optional<Item> item = items.stream().filter(i -> {
            boolean isCategoryMatched = false;
            boolean isBrandNameMatched = false;
            if (categoryName == null || categoryName.equalsIgnoreCase(i.getCategory())) {
                isCategoryMatched = true;
            }
            if (brandName == null || brandName.equalsIgnoreCase(i.getBrand())) {
                isBrandNameMatched = true;
            }
            return isCategoryMatched && isBrandNameMatched;
        }).findFirst();
        if (item.isPresent()) {
            return item.get();
        }
        throw new RuntimeException("Unable to locate item.");
    }
    public void addItems(List<Item> items) {
        itemRepository.addItems(items);
    }
    public void addItem(String itemBrand, String itemCategory, String mrp, String price) {
        try {
            long m = Long.parseLong(mrp);
            long p = Long.parseLong(price);
            Item i = new Item(itemBrand, itemCategory, m, p);
            itemRepository.addItem(i);
            System.out.println("Item added successfully");
        } catch (NumberFormatException e) {
            throw new RuntimeException("Items details are in invalid format.");
        }
    }


    public void updateItems(String category, String brand, String price) {
        try {
            Long p = Long.parseLong(price);
            Item i = this.getItemUsingBrandAndCategoryName(brand, category);
            double currentPrice = i.getPrice();
            double changePrice = (currentPrice * Math.abs(p))/100;
            double updatedPrice = currentPrice;
            if (p < 0) {
                updatedPrice = currentPrice - changePrice;
            } else {
                updatedPrice = currentPrice +  changePrice;
            }
            i.setPrice(updatedPrice);
            System.out.println(String.format("Item updated successfully | existingPrice=%s | updatedPrice=%s", currentPrice, updatedPrice));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Item> getItems() {
        return itemRepository.getItems();
    }
}
