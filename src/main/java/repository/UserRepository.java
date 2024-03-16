package repository;

import exception.InvalidUserException;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private List<User> users;

    public UserRepository() {
        users = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User newUser) {
        for (User u : users) {
            if (u.getName().equalsIgnoreCase(newUser.getName()) && u.getEmail().equalsIgnoreCase(newUser.getEmail())) {
                System.out.println("user already exists.");
                return;
            }
        }
        users.add(newUser);
        System.out.println("user added successfully");
    }

    public User getUserByName(String userName) {
       Optional<User> user =  users.stream().filter(u -> u.getName().equalsIgnoreCase(userName)).findFirst();
       if (user.isPresent()) {
           return user.get();
       }
       throw new InvalidUserException("user is not available.");
    }


}
