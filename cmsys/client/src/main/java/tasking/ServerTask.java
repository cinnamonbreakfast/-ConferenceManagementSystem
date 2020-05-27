package tasking;

import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class ServerTask<T> extends Task<T> {

    private String token = null;
    private UriComponentsBuilder URL;
    private HttpEntity<?> entity;

    private Class<T> response;

    private HttpMethod method;

    @Autowired
    private RestTemplate restTemplate;

    public void setup(UriComponentsBuilder URL, HttpMethod method, HttpEntity<?> entity, Class<T> response) {
        this.URL = URL;
        this.response = response;
        this.method = method;
        this.entity = entity;
        this.response = response;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected T call() throws Exception {
        Class<T> genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), ServerTask.class);

        return (T) restTemplate.exchange(URL.toUriString(), method, entity, genericType);
    }
}
