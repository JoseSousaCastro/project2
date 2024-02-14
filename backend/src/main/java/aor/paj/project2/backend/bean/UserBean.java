package aor.paj.project2.backend.bean;

import aor.paj.project2.backend.dto.Task;
import aor.paj.project2.backend.dto.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
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
                System.out.println("Users: " + users);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else {
            users = new ArrayList<>();
        }
    }

    public ArrayList<User> getUsers() {
        return users;
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
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                status = true;
            }
        }
        return status;
    }

    public boolean isUsernameAvailable(String username) {
        boolean status = true;

        for (User user : users) {
            if (user.getUsername().equals(username)) {
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

    public ArrayList<Task> getUserAndHisTasks(String username) {
                ArrayList<Task> userTasks = null;

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                userTasks = user.getUserTasks();
            }
        }
        return userTasks;
    }

    public void addTaskToUser(String username, Task temporaryTask) {
        TaskBean taskBean = new TaskBean();
        taskBean.newTask(temporaryTask);
        getUserAndHisTasks(username).add(temporaryTask);
        writeIntoJsonFile();
    }

    public boolean updateTask(String username, Task task) {
        TaskBean taskBean = new TaskBean();
         boolean updated = false;

         if (taskBean.editTask(task, getUserAndHisTasks(username))) {
             writeIntoJsonFile();
             updated = true;
         }
         return updated;
    }

    public boolean removeTask(String username, String id) {
        TaskBean taskBean = new TaskBean();
        boolean removed = false;

        if(taskBean.removeTask(id, getUserAndHisTasks(username))) {
            writeIntoJsonFile();
            removed = true;
        }

        return removed;
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