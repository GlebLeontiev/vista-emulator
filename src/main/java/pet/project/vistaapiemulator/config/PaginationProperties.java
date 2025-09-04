package pet.project.vistaapiemulator.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "pagination")
@Getter
@Setter
public class PaginationProperties {
    private Integer defaultPage;
    private Integer defaultLimit;
}
