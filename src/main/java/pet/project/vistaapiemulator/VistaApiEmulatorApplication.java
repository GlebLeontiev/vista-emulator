package pet.project.vistaapiemulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class VistaApiEmulatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(VistaApiEmulatorApplication.class, args);
    }

}
