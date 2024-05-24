package com.example.fix_application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import quickfix.*;

import java.io.InputStream;

@Configuration
public class FixConfig {

    @Bean
    public Application fixApp() {
        return new FixApp();
    }

    @Bean
    public SessionSettings sessionSettings() {
        try (InputStream inputStream = getClass().getResourceAsStream("/quickfixj.cfg")) {
            assert inputStream != null;
            return new SessionSettings(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Error loading QuickFIX/J settings", e);
        }
    }

    @Bean
    public MessageStoreFactory messageStoreFactory(SessionSettings settings) {
        return new FileStoreFactory(settings);
    }

    @Bean
    public LogFactory logFactory(SessionSettings settings) {
        return new FileLogFactory(settings);
    }

    @Bean
    public MessageFactory messageFactory() {
        return new DefaultMessageFactory();
    }

    @Bean
    public SocketInitiator socketInitiator(Application application,
                                           MessageStoreFactory messageStoreFactory,
                                           SessionSettings settings,
                                           LogFactory logFactory,
                                           MessageFactory messageFactory) throws ConfigError {
        return new SocketInitiator(application, messageStoreFactory, settings, logFactory, messageFactory);
    }
}
