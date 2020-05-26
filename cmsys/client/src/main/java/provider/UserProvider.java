package provider;

import dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@Component("comp")
public class UserProvider {
    @Value("http://localhost:8080")
    private String URL;
    private String token = null;
    private LocalDateTime loginTime;

    private final RestTemplate restTemplate;

    @Autowired
    public UserProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String register() {
        UserRegisterDTO dto = new UserRegisterDTO();
        dto.setEmail("candetandrei@gmail.com");
        dto.setFirstName("Candet");
        dto.setLastName("Andrei");
        dto.setUsername("andrew");
        dto.setPassword("12345678");

        return restTemplate.postForObject(
                URL + "/register",
                dto,
                String.class
        );
    }

    public UserDTO login() {
        LoginCredentials dto = new LoginCredentials();
        dto.setPassword("12345678");
        dto.setUsername("andrew");
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
        }

        return response;
    }

    public String testAccessPage() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("SESSION", this.token);

        HttpEntity<Long> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL + "/testAccessPage")
                .queryParam("conferenceID", 1L);

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

        conferenceDTO.setChair(chairDTO);

        System.out.println(conferenceDTO);

        restTemplate.put(URL + "/conference", conferenceDTO, String.class);
    }
}