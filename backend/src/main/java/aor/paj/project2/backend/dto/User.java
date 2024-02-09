package aor.paj.project2.backend.dto;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.net.URL;

@XmlRootElement
public class User {
    @XmlElement
    private String username;
    @XmlElement
    private String password;
    @XmlElement
    private String email;
    @XmlElement
    private String firstName;
    @XmlElement
    private String lastName;
    @XmlElement
    private String phone;
    @XmlElement
    private String photoURL;

    public User() {
    }


    public User(String username, String password, String email, String firstName, String lastName, String phone, String photoURL) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.photoURL = photoURL;
    }


    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        System.out.println(username);
        this.username = username;
    }

    public void setPassword(String password) {
        System.out.println(password);
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}


