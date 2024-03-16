package service;

import exception.InvalidUserException;
import model.Item;
import model.User;
import repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserService {
    private UserRepository userRepository;
    private ItemService itemService;
    private InventoryService inventoryService;
    public UserService(ItemService itemService, InventoryService inventoryService) {
        this.userRepository = new UserRepository();
        this.inventoryService = inventoryService;
        this.itemService = itemService;
    }

    public void addUser(String name, String email, String walletAmount) {
        //TODO can add user details validation
        try {
            long wa = Long.parseLong(walletAmount);
            User user = new User(name, email, wa);
            userRepository.addUser(user);
        } catch (NumberFormatException e) {
            throw  new InvalidUserException("Invalid amount value");
        }
    }

    public void addToCart(String userName , String category, String brand, String quantity) {
        try {
            long q = Long.parseLong(quantity);
            User u = userRepository.getUserByName(userName);
            Map<Item, Long> existingItemsInCart = u.getCart();
            Item i = itemService.getItemUsingBrandAndCategoryName(brand, category);
            long quantityInInventory = inventoryService.getQuantityOfItem(i);
            if (quantityInInventory < q) {
                System.out.println("Items quantity in the inventory is not enough for this order.");
                return;
            }
            if (u.getWalletAmount() >= i.getPrice() * q) {
                Item item = new Item(i);
                existingItemsInCart.put(item, q);
                double currentAmount = u.getWalletAmount();
                u.setWalletAmount(currentAmount - i.getPrice() * q);
                System.out.println("Item added to cart");
                inventoryService.removeItem(category, brand, q);
            } else {
                System.out.println("User doesn't have enough money");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCart(String userName) {
        List<User> userList = userRepository.getUsers();
        Optional<User> user = userList.stream().filter(u -> u.getName().equalsIgnoreCase(userName)).findFirst();
        if (user.isPresent()) {
            Map<Item, Long> itemInCart = user.get().getCart();
            if (itemInCart.isEmpty()) {
                System.out.println("Cart is empty");
                return;
            }
            for (Map.Entry<Item, Long> entry : itemInCart.entrySet()) {
                Item item = entry.getKey();
                Long quantity = entry.getValue();
                String msg = String.format("ItemBrand : %s -> ItemCategory :%s -> quantity : %s -> totalAmount : %s", item.getBrand(), item.getCategory(), quantity, item.getPrice() * quantity);
                System.out.println(msg);
            }
        } else {
            throw new RuntimeException("Unable to user locate user with username :" + userName);
        }
    }

    public void getUser(String username) {
        try {
            User u = userRepository.getUserByName(username);
            System.out.println(String.format("user name : %s | wallet %s ", u.getName(), u.getWalletAmount()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 }
