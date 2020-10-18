import dto.UserDTO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ClientMS {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "setup"
                );
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("test");
        System.out.println(userDTO);
    }
}
