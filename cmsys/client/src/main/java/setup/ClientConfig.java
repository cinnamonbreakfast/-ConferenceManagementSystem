package setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import provider.UserProvider;
import ui.StartWindow;

@Configuration
@ComponentScan({"provider", "ui"})
public class ClientConfig {
    @Bean("RestTemplate")
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    StartWindow startWindow()
    {
        return new StartWindow();
    }
}
