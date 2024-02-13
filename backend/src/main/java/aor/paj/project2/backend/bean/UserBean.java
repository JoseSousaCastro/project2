package aor.paj.project2.backend.bean;

import aor.paj.project2.backend.dto.Task;
import aor.paj.project2.backend.dto.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

@ApplicationScoped
public class UserBean {
    private final String filename = "users.json";
    private ArrayList<User> users;

    public UserBean() {
        File f = new File(filename);
        if (f.exists()) {
            try {
                FileReader filereader = new FileReader(f);
                users = JsonbBuilder.create().fromJson(filereader, new ArrayList<User>() {}.getClass().getGenericSuperclass());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else
            users = new ArrayList<User>();
    }

    public boolean addUser(User user) {

        boolean status = false;
        if (users.add(user)) {
            status = true;
        }
        writeIntoJsonFile();
        return status;
    }

    public User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public ArrayList<User> getUsers() {
        return users;
    }


    public boolean updateUser(User user) {
        boolean status = false;

        for (User a : users) {
            if (a.getUsername().equals(user.getUsername())) {
                a.setPassword(user.getPassword());
                a.setEmail(user.getEmail());
                a.setFirstName(user.getFirstName());
                a.setLastName(user.getLastName());
                a.setPhone(user.getPhone());
                a.setPhotoURL(user.getPhotoURL());
                writeIntoJsonFile();
                status = true;
            }
        }
        return status;
    }

    public boolean isAuthenticated(String username, String password) {
        boolean status = false;

        for (User user : users) {
            if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                status = true;
            }
        }
        return status;
    }

    public boolean isUsernameAvailable(String username) {
        boolean status = true;

        for (User user : users) {
            if(user.getUsername().equals(username)) {
                status = false;
            }
        }
        return status;
    }

    private boolean isEmailFormatValid(String email) {
        boolean status;

        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
            status = true;

        } catch (AddressException ex) {
            status = false;
        }
        return status;
    }

    public boolean isEmailValid(String email) {

        boolean status = true;

        if (isEmailFormatValid(email)) {
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    status = false;
                }
            }
        } else {
            status = false;
        }
        return status;
    }

    public boolean isAnyFieldEmpty(User user) {
        boolean status = false;

        if (user.getUsername().isEmpty() ||
                user.getPassword().isEmpty() ||
                user.getEmail().isEmpty() ||
                user.getFirstName().isEmpty() ||
                user.getLastName().isEmpty() ||
                user.getPhone().isEmpty() ||
                user.getPhotoURL().isEmpty()) {
            status = true;
        }
        return status;
    }

    public boolean isPhoneNumberValid(String phone) {
        boolean status = true;

        while (status) {
            if (phone.length() == 9 ) {
                for (int i = 0; i < phone.length(); i++) {
                    if (!Character.isDigit(phone.charAt(i))) {
                        status = false;
                    }
                }
            } else {
                status = false;
            }
        }
        return status;
    }


    public ArrayList<Task> getUserTasks(String username) {

        ArrayList<Task> userTasks = null;

        for(User user : users) {
            if(user.getUsername().equals(username)) {
                userTasks = user.getUserTasks();
            }
        }
        return userTasks;
    }

    private void writeIntoJsonFile() {
        Jsonb jsonb = JsonbBuilder.create(new
                JsonbConfig().withFormatting(true));
        try {
            jsonb.toJson(users, new FileOutputStream(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);

        }
    }
}