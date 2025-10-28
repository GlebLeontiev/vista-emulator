package pet.project.vistaapiemulator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pet.project.vistaapiemulator.exceptions.UnauthorizedException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private static final Map<String, LocalDateTime> clientIdToExpirationDateMap = new ConcurrentHashMap<>();


    public void validateClientId(String clientId) {
        LocalDateTime expirationDate = clientIdToExpirationDateMap.get(clientId);
        if (expirationDate == null || expirationDate.isBefore(LocalDateTime.now())) {
            throw new UnauthorizedException();
        }
    }

    public String login(String username, String password) {
        // TODO check db for user
        String clientId = createClientId();
        log.info("User {} successfully authenticated, client id {} was generated", username, clientId);
        return clientId;
    }

    public String createClientId() {
        String clientId = randomStringGenerator();
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(1);
        clientIdToExpirationDateMap.put(clientId, expirationDate);
        return clientId;
    }

    public void invalidateClientId(String clientId) {
        LocalDateTime remove = clientIdToExpirationDateMap.remove(clientId);
        if (remove != null){
            log.info("Client id {} was invalidated", clientId);
        } else {
            log.info("Client id {} failed to be invalidated", clientId);
        }
    }

    @Scheduled(cron = "* * 1 * * *")
    public void removeExpiredClientIds() {
        LocalDateTime now = LocalDateTime.now();
        clientIdToExpirationDateMap.entrySet().removeIf(entry -> entry.getValue().isBefore(now));
    }

    private String randomStringGenerator() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
