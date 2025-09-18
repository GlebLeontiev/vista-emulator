package pet.project.vistaapiemulator.util;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pet.project.vistaapiemulator.model.entity.*;
import pet.project.vistaapiemulator.model.enums.FuelCardProvider;
import pet.project.vistaapiemulator.model.enums.FuelCardStatus;
import pet.project.vistaapiemulator.model.enums.TransactionLineCategory;
import pet.project.vistaapiemulator.model.enums.TransactionLineType;
import pet.project.vistaapiemulator.repository.FuelCardRepository;
import pet.project.vistaapiemulator.repository.FuelCardTransactionRepository;
import pet.project.vistaapiemulator.repository.FuelStationRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
public class TestDataGenerator {

    private static final Random random = new Random();
    private static final Logger log = LoggerFactory.getLogger(TestDataGenerator.class);
    private final AtomicLong atomicInteger = new AtomicLong();
    private final FuelCardRepository fuelCardRepository;
    private final FuelCardTransactionRepository transactionRepository;
    private final FuelStationRepository fuelStationRepository;

    @PostConstruct
    public void initAtomicInteger() {
        try {
            String maxRefId = transactionRepository.findMaxRefTransactionId();

            if (StringUtils.isNotBlank(maxRefId)) {
                long refId = Long.parseLong(maxRefId);
                atomicInteger.set(refId);
                atomicInteger.incrementAndGet();
            }
        } catch (Exception e) {
            log.error("Exception in initAtomicInteger()", e);
        }
    }

    public void generateTestDataForPurchaseOrder(PurchaseOrder order) {
        Integer cardsQuantity = order.getCardsQuantity();

        List<FuelCard> fuelCardList = new ArrayList<>();
        for (int i = 0; i < cardsQuantity; i++) {
            FuelCard fuelCard = generateFuelCardByPO(order);
            fuelCardList.add(fuelCard);
        }
        fuelCardRepository.saveAll(fuelCardList);
        log.info("generateTestDataForPurchaseOrder(): generated new cards, size: {}", fuelCardList.size());
    }

    @Transactional
    public void generateTransactionsByFuelCard(List<FuelCard> fuelCards) {
        if (fuelCards == null || fuelCards.isEmpty()) return;

        List<FuelStation> stations = fuelStationRepository.findAll();
        if (stations.isEmpty()) return;

        List<FuelCardTransaction> transactionsToSave = new ArrayList<>();

        for (FuelCard card : fuelCards) {
            FuelStation station = stations.get(random.nextInt(stations.size()));
            FuelCardTransaction tx = buildTransaction(card, station);
            transactionsToSave.add(tx);
        }
        transactionRepository.saveAll(transactionsToSave);
        log.info("generateTransactionsByFuelCard(): generated new transactions, size: {}", transactionsToSave.size());
    }

    private FuelCardTransaction buildTransaction(FuelCard card, FuelStation station) {
        long now = System.currentTimeMillis();
        long refId = atomicInteger.incrementAndGet();

        int linesCount = 1 + random.nextInt(2);
        List<FuelCardTransactionLine> lines = new ArrayList<>();

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal taxTotal = BigDecimal.ZERO;

        for (int i = 0; i < linesCount; i++) {
            TransactionLineType type = TransactionLineType.values()[i];   //random.nextBoolean() ? TransactionLineType.FUEL : TransactionLineType.NON_FUEL;
            TransactionLineCategory category = pickCategory(type);

            BigDecimal quantity = randomBigDecimal(5, 180);
            BigDecimal pricePerUnit = randomBigDecimal(0.5, 5.5);
            BigDecimal lineSubtotal = quantity.multiply(pricePerUnit);
            BigDecimal discountTotalAmount = lineSubtotal.multiply(randomBigDecimal(0.05, 0.20));
            BigDecimal lineTax = lineSubtotal.multiply(BigDecimal.valueOf(0.10));
            BigDecimal lineTotal = lineSubtotal.add(lineTax).subtract(discountTotalAmount);

            FuelCardTransactionLine line = FuelCardTransactionLine.builder()
                    .type(type)
                    .category(category)
                    .quantity(quantity)
                    .pricePerUnit(pricePerUnit)
                    .discountAmount(discountTotalAmount)
                    .tax(lineTax)
                    .build();

            lines.add(line);
            total = total.add(lineTotal);
            taxTotal = taxTotal.add(lineTax);
        }

        FuelCardTransaction tx = FuelCardTransaction.builder()
                .refTransactionId(String.valueOf(refId))
                .datetime(now)
                .card(card)
                .station(station)
                .totalAmount(total)
                .tax(taxTotal)
                .customer(card.getCustomer())
                .lines(lines)
                .build();

        lines.forEach(l -> l.setTransaction(tx));
        return tx;
    }

    private BigDecimal randomBigDecimal(double min, double max) {
        return BigDecimal.valueOf(min + (max - min) * random.nextDouble()).setScale(2, RoundingMode.HALF_UP);
    }

    private TransactionLineCategory pickCategory(TransactionLineType type) {
        if (type == TransactionLineType.FUEL) {
            return random.nextBoolean() ? TransactionLineCategory.OTHER_FUEL : TransactionLineCategory.TRACTOR_FUEL;
        }
        return TransactionLineCategory.OTHER_PRODUCT;
    }

    public FuelCard generateFuelCardByPO(PurchaseOrder order) {
        return FuelCard.builder()
                .cardNumber(generateRandomNumber(16))
                .provider(FuelCardProvider.values()[random.nextInt(FuelCardProvider.values().length)])
                .status(FuelCardStatus.INACTIVE)
                .customer(order.getCustomer())
                .order(order)
                .build();
    }

    public String generateRandomNumber(int amountOfDigits) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < amountOfDigits; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
