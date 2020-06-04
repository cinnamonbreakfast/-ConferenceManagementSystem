import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import provider.UserProvider;

public class ClientMS {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "setup"
                );

        UserProvider userProvider = context.getBean(UserProvider.class);
//        System.out.println(userProvider.register());
        System.out.println(userProvider.login());
        System.out.println(userProvider.testAccessPage());


//        userProvider.testMakingConference();

    }
}
