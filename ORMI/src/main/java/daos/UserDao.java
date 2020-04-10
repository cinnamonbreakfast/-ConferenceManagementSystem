package daos;

import Domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class UserDao {

    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    public UserDao(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.entityTransaction = this.entityManager.getTransaction();
    }

    public void persist(String name, String email) {
        beginTransaction();
        User user = new User(name, email);
        entityManager.persist(user);
        commitTransaction();
    }

    public User find(int id) {
        return entityManager.find(User.class, id);
    }

    public void update(int id, String name, String email) {
        beginTransaction();
        User user = entityManager.find(User.class, id);
        user.setName(name);
        user.setEmail(email);
        entityManager.merge(user);
        commitTransaction();
    }

    public void remove(int id) {
        beginTransaction();
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
        commitTransaction();
    }

    private void beginTransaction() {
        try {
            entityTransaction.begin();
        } catch (IllegalStateException e) {
            rollbackTransaction();
        }
    }

    private void commitTransaction() {
        try {
            entityTransaction.commit();
            entityManager.close();
        } catch (IllegalStateException e) {
            rollbackTransaction();
        }
    }

    private void rollbackTransaction() {
        try {
            entityTransaction.rollback();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }


}
