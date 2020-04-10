import Domain.User;
import daos.UserDao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HibernateApplication {
    private final UserDao userDao;
    private final BufferedReader userInputReader;

    public static void main(String[] args) throws Exception {
        EntityManager entityManager = Persistence
                .createEntityManagerFactory("user-unit")
                .createEntityManager();
        UserDao userDao = new UserDao(entityManager);
        BufferedReader userInputReader =
                new BufferedReader(new InputStreamReader(System.in));
        new HibernateApplication(userDao, userInputReader).run();
    }

    public HibernateApplication(UserDao userDao, BufferedReader userInputReader) {
        this.userDao = userDao;
        this.userInputReader = userInputReader;
    }

    private void run() throws IOException {
        System.out.println("Enter an option: "
                + "1) Insert a new user. "
                + "2) Find a user. "
                + "3) Edit a user. "
                + "4) Delete a user.");
        int option = Integer.parseInt(userInputReader.readLine());

        switch (option) {
            case 1:
                persistNewUser();
                break;
            case 2:
                fetchExistingUser();
                break;
            case 3:
                updateExistingUser();
                break;
            case 4:
                removeExistingUser();
                break;
        }
    }

    private void persistNewUser() throws IOException {
        String name = requestStringInput("the name of the user");
        String email = requestStringInput("the email of the user");
        userDao.persist(name, email);
    }

    private void fetchExistingUser() throws IOException {
        int id = requestIntegerInput("the user ID");
        User user = userDao.find(id);
        System.out.print("Name : " + user.getName() + " Email : " + user.getEmail());
    }

    private void updateExistingUser() throws IOException {
        int id = requestIntegerInput("the user ID");
        String name = requestStringInput("the name of the user");
        String email = requestStringInput("the email of the user");
        userDao.update(id, name, email);
    }

    private void removeExistingUser() throws IOException {
        int id = requestIntegerInput("the user ID");
        userDao.remove(id);
    }

    private String requestStringInput(String request) throws IOException {
        System.out.printf("Enter %s: ", request);
        return userInputReader.readLine();
    }

    private int requestIntegerInput(String request) throws IOException {
        System.out.printf("Enter %s: ", request);
        return Integer.parseInt(userInputReader.readLine());
    }

}
