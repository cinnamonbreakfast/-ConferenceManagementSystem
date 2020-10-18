package provider;

import dto.CommentDTO;
import dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Component()
public class CommentProvider {
    @Value("http://localhost:8080")
    private String URL;

    private final RestTemplate restTemplate;

    @Autowired
    public CommentProvider(RestTemplate restTemplate){this.restTemplate=restTemplate;}

    public void testadd(){
        UserDTO reviewer= new UserDTO();
        reviewer.setEmail("candetandrei@gmail.com");
        reviewer.setFirstName("Candet");
        reviewer.setLastName("Andrei");
        reviewer.setUsername("andrew");
        reviewer.setId(1L);

        CommentDTO comment=new CommentDTO();
        comment.setAuthor(reviewer);
        comment.setText("branzica e buna");
        comment.setUpvotes(5);
        comment.setPosttime(LocalDateTime.now());

        System.out.println(comment);
        restTemplate.put(URL + "/comment",comment,String.class);


    }

}
