package provider;

import dto.*;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Component
public class UserProvider {
    @Value("http://25.139.122.210:8080")
    private String URL;
    private String token = null;
    private LocalDateTime loginTime;

    private final RestTemplate restTemplate;

    public String getURL() {
        return URL;
    }
    public String getToken() {
        return token;
    }

    @Autowired
    public UserProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String register(UserRegisterDTO dto) {

        return restTemplate.postForObject(
                URL + "/register",
                dto,
                String.class
        );
    }

    public UserDTO login(String username, String password) {
        LoginCredentials dto = new LoginCredentials();
        dto.setPassword(password);
        dto.setUsername(username);
        dto.setLoginTime("1");

        UserDTO response = restTemplate.postForObject(
                URL + "/login",
                dto,
                UserDTO.class
        );

        if(response != null)
        {
            this.token = response.getToken();
            this.loginTime = response.getLoginTime();

            return response;
        }

        return null;
    }

    public String testAccessPage() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("SESSION", this.token);

        HttpEntity<Long> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL + "/testAccessPage");

        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class).getBody();
    }

    public void testMakingConference() {
        UserDTO chairDTO = new UserDTO();
        chairDTO.setEmail("candetandrei@gmail.com");
        chairDTO.setFirstName("Candet");
        chairDTO.setLastName("Andrei");
        chairDTO.setUsername("andrewcandet");
        chairDTO.setId(6L);

        ConferenceDTO conferenceDTO = new ConferenceDTO();
        conferenceDTO.setTitle("Test Conference");
        conferenceDTO.setDescription("A cool conference");
        conferenceDTO.setPhase(0);

        System.out.println(conferenceDTO);

        restTemplate.put(URL + "/conference", conferenceDTO, String.class);
    }
}