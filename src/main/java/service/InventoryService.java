package service;

import model.Item;
import model.SearchCriteria;
import model.SearchResult;
import repository.InventoryRepository;

import java.util.*;
import java.util.stream.Collectors;

public class InventoryService {
    private InventoryRepository inventoryRepository;
    private ItemService itemService;

    public InventoryService(ItemService itemService) {
        this.itemService = itemService;
        this.inventoryRepository = new InventoryRepository();
    }


    public void addInventory(String category, String brand, String quantity) {
        try {
            long q = Long.parseLong(quantity);
            Item i = itemService.getItemUsingBrandAndCategoryName(brand, category);
            inventoryRepository.addItemInInventory(i, q);
            System.out.println("Inventory updated successfully");
            inventoryRepository.getInventoryDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getInventoryDetails() {
        inventoryRepository.getInventoryDetails();
    }
    public void removeItem(String category, String brand, long quantity) {
        Item i = itemService.getItemUsingBrandAndCategoryName(brand, category);
        inventoryRepository.removeItemFromInventory(i, quantity);
    }

    public long getQuantityOfItem(Item i) {
        try {
            return inventoryRepository.getQuantityOfItem(i);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void searchItems(List<String> categories, List<String> brands, boolean requireQuantityBasedSorting) {
        try {
            List<Item> items = itemService.getItems()
                    .stream()
                    .filter(item -> categories.size() == 0 || categories.contains(item.getCategory().toLowerCase()))
                    .filter(item -> brands.size() == 0 || brands.contains(item.getBrand().toLowerCase()))
                    .collect(Collectors.toList());;
            List<SearchResult> sortedItemsBasedOnQuantity = new ArrayList<>();
            items.stream().forEach(item -> {
                long quantity = 0;
                try {
                    quantity = inventoryRepository.getQuantityOfItem(item);
                } catch (RuntimeException e) {
                }
                sortedItemsBasedOnQuantity.add(new SearchResult(item, quantity));
            });
            if (requireQuantityBasedSorting) {
                Collections.sort(sortedItemsBasedOnQuantity, (s1, s2) -> s2.getQuantity().compareTo(s1.getQuantity()));
            }
            sortedItemsBasedOnQuantity.forEach(searchResult -> {
                String msg = String.format("item category %s | item brand %s  | item quantity %s", searchResult.getItem().getCategory(), searchResult.getItem().getBrand(), searchResult.getQuantity());
                System.out.println(msg);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
