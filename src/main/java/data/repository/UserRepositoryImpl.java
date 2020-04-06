package data.repository;

import data.models.User;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private final EntityManager entityManager;
    private final ModelMapper mapper;

    @Inject
    public UserRepositoryImpl(EntityManager entityManager, ModelMapper mapper) {
        this.entityManager = entityManager;
        this.mapper = mapper;
    }

    @Override
    public void saveUser(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public User findUser(String username, String password) {
        List<User> users = entityManager.createQuery("select u from User u where u.username=:username " +
                "and u.password = :password", User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList();
        if (users.isEmpty()){
            return null;
        }
        return users.get(0);
    }

    @Override
    public List<User> allUsers() {
        return entityManager.createQuery("select u from User u", User.class)
                .getResultList();
    }

    @Override
    public User findUserByUsername(String username) {
        return entityManager.createQuery("select u from User u where u.username=:username", User.class)
                .setParameter("username", username)
                .getResultList().get(0);
    }

    @Override
    public void update(User user) {
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }
}
