package de.excellence.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.security.Principal;
import java.util.Objects;

@Entity
@Table(name = "`user`")
@NamedQuery(name = "excellence.User.byName",
        query = "SELECT u FROM User u WHERE u.username = ?1")
public class User implements Principal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(defaultValue = "0")
    private long id;
    @Column(name = "username", nullable = false)
    @Length(min = 5, max = 100)
    private String username;
    @Column(name = "hashedPassword", nullable = false)
    @JsonIgnore
    private String hashedPassword;

    protected User() {

    }

    public User(String username, String hashedPassword) {
        this.hashedPassword = hashedPassword;
        this.username = username;
    }

    @Override
    @JsonIgnore
    public String getName() {
        return username;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String password) {
        this.hashedPassword = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && username.equals(user.username) && hashedPassword.equals(user.hashedPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, hashedPassword);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
