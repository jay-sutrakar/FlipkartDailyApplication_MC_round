import model.Item;
import service.InventoryService;
import service.ItemService;
import service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FlipKartDailyApplication {
    private static ItemService itemService;
    private static UserService userService;
    private static InventoryService inventoryService;
    private static Pattern pattern = Pattern.compile("[0-9]+");
    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        itemService = new ItemService();
        inventoryService = new InventoryService(itemService);
        userService = new UserService(itemService, inventoryService);
        insertDummyData();
        System.out.println("=======================================");
        System.out.println("FlipkartDaily service is running.");
        System.out.println("Please enter the commands.");

        while (true) {
            try {
            String sc = bufferedReader.readLine();
            String[] commands = sc.split(" ");
            switch (commands[0]) {
                case "add_user":
                    handleAddUser(commands);
                    break;
                case "get_user":
                    if (commands.length < 2) {
                        System.out.println("Enough info is not present to get user details.");
                    } else {
                        String userName = commands[1];
                        userService.getUser(userName);
                    }
                    break;
                case "add_item":
                    handleAddItem(commands);
                    break;
                case "print_items":
                    handlePrintItems();
                    break;
                case "add_to_cart":
                    handleAddToCart(commands);
                    break;
                case "add_inventory":
                    handleAddItemInInventory(commands);
                    break;
                case "get_inventory":
                    inventoryService.getInventoryDetails();
                    break;
                case "get_cart":
                    handleGetCart(commands);
                    break;
                case "update_item":
                    handleUpdateItem(commands);
                    break;
                case "search_items":
                    handleSearchItem(commands);
                    break;
                case "exit":
                    System.exit(1);
                    break;
                default:
                    System.out.println("Invalid command.");
                    break;
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void handlePrintItems() {
        List<Item> items = itemService.getItems();
        if (items.size() == 0) {
            System.out.println("Items are not available.");
        } else {
            items.stream().forEach(item -> {
                String msg = String.format("category -> %s | brand -> %s | price -> %s", item.getCategory(), item.getBrand(), item.getPrice());
                System.out.println(msg);
            });
        }
    }

    private static void handleSearchItem(String[] commands) {
        if (commands.length < 2) {
            System.out.println("Not enough information for performing search functionality.");
            return;
        }
        List<String> categories = parseSearchFields(commands[1]);
        List<String> brands = new ArrayList<>();
        if (commands.length > 2) {
            brands = parseSearchFields(commands[2]);
        }
        boolean isSortingRequired = false;
        if (commands.length > 3 && commands[3].toLowerCase().contains("orderby")) {
            isSortingRequired = true;
        }
        inventoryService.searchItems(categories, brands, isSortingRequired);
    }

    private static List<String> parseSearchFields(String command) {
        command = command.replaceAll("[\\[\\](){}]", "");
        String[] str = command.split(",");
        List<String> fields = new ArrayList<>();
        for (String s : str) {
            if (s.length() > 0) {
                fields.add(s.toLowerCase());
            }
        }
        return fields;
    }

    private static void insertDummyData() {
        Item item1 = new Item("milk", "amul", 120, 100);
        Item item2 = new Item("milk", "Nestle", 140, 110);
        Item item3 = new Item("curd", "amul", 50, 40);
        Item item4 = new Item("curd", "Nestle", 80, 100);
        Item item5 = new Item("Oats", "Tata", 450, 390);
        Item item6 = new Item("Oats", "Kellogs", 500, 450);
        itemService.addItems(Arrays.asList(item1, item2, item3, item4, item5, item6));
        inventoryService.addInventory("milk", "amul", "100");
        inventoryService.addInventory("milk", "Nestle", "200");
        inventoryService.addInventory("curd", "amul", "50");
        inventoryService.addInventory("curd", "Nestle", "110");
        inventoryService.addInventory("Oats", "Tata", "70");
        inventoryService.addInventory("Oats", "Kellogs", "70");


    }

    private static void handleUpdateItem(String[] commands) {
        if (commands.length < 4) {
            System.out.println("Enough info is not provide to update the item.");
            return;
        }
        String category = commands[1];
        String brand = commands[2];
        String price = getPricingOption(commands[3]);
        itemService.updateItems(category, brand, price);
    }

    private static String getPricingOption(String command) {
       Matcher matcher = pattern.matcher(command);
       if (matcher.find()) {
           if (command.toLowerCase().contains("off")) {
               return "-" + matcher.group();
           } else {
               return matcher.group();
           }
       }
       return "0";
    }

    private static void handleAddToCart(String[] commands) {
        if (commands.length < 5) {
            return;
        }
        String username = commands[1];
        String category = commands[2];
        String brand = commands[3];
        String quantity = commands[4];
        userService.addToCart(username, category, brand, quantity);
    }

    private static void handleAddItemInInventory(String[] commands) {
        if (commands.length < 4) {
            System.out.println("Not enough info to add the item in inventory information");
            return;
        }
        String category = commands[1];
        String brand = commands[2];
        String quantity = commands[3];
        inventoryService.addInventory(category, brand, quantity);
    }

    private static void handleGetCart(String[] commands) {
        if (commands.length < 2) {
            System.out.println("Not enough info to get the cart information");
            return;
        }
        String userName = commands[1];
        userService.getCart(userName);
    }

    private static void handleAddUser(String[] commands) {
        if (commands.length < 4) {
            System.out.println("Enough info is not present to add user");
            return;
        }
        String name = commands[1];
        String email = commands[2];
        String amount = commands[3];
        userService.addUser(name, email, amount);
    }

    private static void handleAddItem(String[] commands) {
        if (commands.length < 5) {
            System.out.println("Enough info is not present to add item");
            return;
        }
        String category = commands[1];
        String brand = commands[2];
        String mrp = commands[3];
        String price = commands[4];
        itemService.addItem(brand, category, mrp, price);
    }
}
