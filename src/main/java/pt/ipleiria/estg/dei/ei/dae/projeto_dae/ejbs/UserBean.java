package pt.ipleiria.estg.dei.ei.dae.projeto_dae.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.User;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.security.Hasher;
import java.util.logging.Logger;
@Stateless
public class UserBean {

    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private Hasher hasher;


    public void create(String username, String name, String password) {
        User user = entityManager.find(User.class, username);

        if (user != null) {
            // If user already exists, log an error or handle accordingly
            System.out.println("User already exists with username: " + username);
            return;
        }

        // Create a new user instance and persist it
        User newUser = new User(username, name, hasher.hash(password));
        entityManager.persist(newUser);
    }


    public User findOrFail(String username){
        return entityManager.getReference(User.class,username);

    }

    public User find(String username) {
        return entityManager.find(User.class, username);
    }

    public boolean canLogin(String username, String password) {
        var user = find(username);
        return user != null && user.getPassword().equals(hasher.hash(password));
    }

}
