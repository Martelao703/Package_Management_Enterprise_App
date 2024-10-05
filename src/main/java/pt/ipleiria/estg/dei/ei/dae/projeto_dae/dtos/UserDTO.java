package pt.ipleiria.estg.dei.ei.dae.projeto_dae.dtos;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {
    @NotNull
    protected String username;
    @NotNull
    protected String name;
    @NotNull
    protected String password;
    @NotNull
    private String role;

    public UserDTO() {
    }

    public UserDTO(String username, String name, String role) {
        this.username = username;
        this.name = name;
        this.role = role;
    }

    public static UserDTO from(User user){
        return new UserDTO(
                user.getUsername(),
                user.getName(),
                Hibernate.getClass(user).getSimpleName()
        );
    }

    public static List<UserDTO> from(List<User> users) {
        return users.stream().map(UserDTO::from).collect(Collectors.toList());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
