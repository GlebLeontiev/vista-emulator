package pet.project.vistaapiemulator.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pet.project.vistaapiemulator.model.entity.FuelCard;
import pet.project.vistaapiemulator.model.enums.FuelCardStatus;
import pet.project.vistaapiemulator.repository.FuelCardRepository;
import pet.project.vistaapiemulator.util.TestDataGenerator;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TestDataCreatorProcessor {

    private final FuelCardRepository fuelCardRepository;
    private final TestDataGenerator testDataGenerator;

   @Scheduled(initialDelayString = "${scheduler.initial-delay}", fixedRateString = "${scheduler.fixed-rate}")
    public void createTestTransactionForActiveCards() {
       log.info("TestDataCreatorProcessor - START");
       try {
           List<FuelCard> fuelCardByStatus = fuelCardRepository.findFuelCardByStatus(FuelCardStatus.ACTIVE);
           log.info("TestDataCreatorProcessor - fetch fuelCards list, size: {}", fuelCardByStatus.size());

           testDataGenerator.generateTransactionsByFuelCard(fuelCardByStatus);
           log.info("TestDataCreatorProcessor - finish generating transactions");
       } catch (Exception e) {
           log.error("Exception in createTestTransactionForActiveCards() ", e);
           throw new RuntimeException(e);
       }
   }
}
