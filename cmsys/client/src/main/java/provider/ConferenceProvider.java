package provider;

import dto.ConferencesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@Component
public class ConferenceProvider {
    @Value("http://25.139.122.210:8080")
    private String URL;
    private String token = null;

    private final RestTemplate restTemplate;

    public void setToken(String token)
    {
        this.token = token;
    }

    @Autowired
    public ConferenceProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ConferencesDTO getMyConferences()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.set("SESSION", this.token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        ConferencesDTO probe = new ConferencesDTO();
        probe.setSize(10);

        HttpEntity<ConferencesDTO> entity = new HttpEntity<>(probe, headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL + "/conference/get");

        return restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, ConferencesDTO.class).getBody();
    }
}
