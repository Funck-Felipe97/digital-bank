package com.funck.digitalbank.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "informacoes-banco")
@Data
public class BancoConfig {

    private String numero = "300";
    private String email = "banco@gmail.com";
    private Long tokenExpiration = 300L;

}
