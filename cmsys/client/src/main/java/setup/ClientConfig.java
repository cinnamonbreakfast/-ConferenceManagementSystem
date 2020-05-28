package setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import provider.UserProvider;
import ui.MainWindow;
import ui.StartWindow;
import ui.conferences.Hello;
import ui.conferences.NewConference;

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

    @Bean
    MainWindow mainWindow()
    {
        return new MainWindow();
    }

    @Bean
    Hello conferencesHello()
    {
        return new Hello();
    }

    @Bean
    NewConference conferencesNew()
    {
        return new NewConference();
    }
}
