package provider;

import dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserProvider {
    @Value("http://25.139.122.210:8080")
    private String URL;
    private String token = null;
    private LocalDateTime loginTime;
    private UserDTO user = null;

    private final RestTemplate restTemplate;

    public String getURL() {
        return URL;
    }
    public String getToken() {
        return token;
    }
    public UserDTO getUser() { return user; }

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

            this.user = response;

            return response;
        }

        return null;
    }

    public ResponseEntity<HttpStatus> logout() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("SESSION", this.token);

        HttpEntity<Long> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL + "/logout");

        return restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, HttpStatus.class);
    }

    @SuppressWarnings("unchecked")
    public List<String> getMatchUsername(String username) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("SESSION", this.token);

        HttpEntity<String> entity = new HttpEntity<>(username, headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL + "/matchusername");

        ResponseEntity<List> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, List.class);

        if(!responseEntity.getStatusCode().equals(HttpStatus.OK))
        {
            return new ArrayList<>();
        }

        return (List<String>) responseEntity.getBody();
    }

    public UserDTO getByUsername(String username)
    {
        return restTemplate.getForObject(URL + "/get/u/"+username, UserDTO.class);
    }

    public PermissionDTO getPermissions(Long conferenceID) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("SESSION", this.token);

        HttpEntity<Long> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL + "/user/permissions").queryParam("conferenceID", conferenceID);

        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, PermissionDTO.class).getBody();
    }
}