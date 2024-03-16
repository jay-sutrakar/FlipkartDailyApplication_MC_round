package repository;

import model.Item;

import java.util.HashMap;
import java.util.Map;

public class InventoryRepository {
   private Map<Item, Long> itemsQuantity;
   public InventoryRepository() {
       this.itemsQuantity = new HashMap<>();
   }
   public long getQuantityOfItem(Item item) {
       if (itemsQuantity.containsKey(item)) {
           return itemsQuantity.get(item);
       }
       throw new RuntimeException("item is not present in inventory");
   }
   public void getInventoryDetails() {
       if (itemsQuantity.size() == 0) {
           System.out.println("Inventory is empty !");
       } else {
           for (Map.Entry<Item, Long> entry : itemsQuantity.entrySet()) {
               Item i = entry.getKey();
               Long quantity = entry.getValue();
               String msg = String.format("Item category %s | item brand %s | quantity : %s", i.getCategory(), i.getBrand(), quantity);
               System.out.println(msg);
           }
       }
   }
   public void addItemInInventory(Item i, long quantity) {
       if (itemsQuantity.containsKey(i)) {
           //TODO eiter we can add the qunatity or replace it
          long existingQuantity =  itemsQuantity.get(i);
          itemsQuantity.put(i, existingQuantity + quantity);
       } else {
           itemsQuantity.put(i, quantity);
       }
   }

   public void removeItemFromInventory(Item i, long quantity) {
       if (itemsQuantity.containsKey(i) && itemsQuantity.get(i) >= quantity) {
           long existingQuantity = itemsQuantity.get(i);
           itemsQuantity.put(i, existingQuantity - quantity);
       }
   }
}
