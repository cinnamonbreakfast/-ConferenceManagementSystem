package provider;

import dto.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

@Component
public class ConferenceProvider {
    @Value("http://25.134.143.82:8080")
    private String URL;
    private String token = null;

    @Getter
    @Setter
    public ConferenceDTO loggedConference;

    @Getter
    @Setter
    private PermissionDTO loggPermission;

    @Getter
    @Setter
    private String permissionOn;

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

    @SuppressWarnings("unchecked")
    public PapersDTO getMyPapersFromConference()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.set("SESSION", this.token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL + "/papers/get");
        builder.queryParam("id", loggedConference.getId());

        return (restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, PapersDTO.class).getBody());
    }

    public ResponseEntity<String> makeConference(ConferenceDTO setup)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.set("SESSION", this.token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<ConferenceDTO> entity = new HttpEntity<>(setup, headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL + "/conference");

        return restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);
    }
}
