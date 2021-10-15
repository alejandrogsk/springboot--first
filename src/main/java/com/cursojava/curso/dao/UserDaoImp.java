package com.cursojava.curso.dao;

import com.cursojava.curso.models.User;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getUsers() {
        String query = "FROM User";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void delete(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public void createUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getUsersCredentials(User user) {
        String query = "FROM User WHERE email = :email";
        List<User> data = entityManager.createQuery(query)
                .setParameter("email", user.getEmail())
                .getResultList();

        if(data.isEmpty()){
            return null;
        }

        String storedPassword = data.get(0).getPassword();

        String loginPassword = user.getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        boolean isCorrect = argon2.verify(storedPassword, loginPassword);

        if(!isCorrect){
            return null;
        }

        return data.get(0);
    }
}



















