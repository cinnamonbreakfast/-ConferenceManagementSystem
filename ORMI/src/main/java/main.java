import Domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class main {

    public static void main(String[]args){
        EntityManager entityManager = Persistence
                .createEntityManagerFactory("user-unit")
                .createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        /* Persist a User entity */
        entityTransaction.begin();
        User user = new User("Alejandro", "alejandro@domain.com");
        entityManager.persist(user);
        entityTransaction.commit();

        /* Fetch a User entity */
        entityManager.find(User.class, 1);

        /* Update a User entity */
        entityTransaction.begin();
        User user2 = entityManager.find(User.class, 1);
        user2.setName("Alex");
        user2.setEmail("alex@domain.com");
        entityManager.merge(user2);
        entityTransaction.commit();

        /* Remove a User entity */
        entityTransaction.begin();
        User user3 = entityManager.find(User.class, 1);
        entityManager.remove(user3);
        entityTransaction.commit();

        entityManager.close();
    }
}
