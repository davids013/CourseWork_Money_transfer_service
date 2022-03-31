package ru.netology.money_transfer_service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.money_transfer_service.domain.Amount;
import ru.netology.money_transfer_service.domain.requests.ConfirmRequest;
import ru.netology.money_transfer_service.domain.requests.TransferRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers(disabledWithoutDocker = true)
class MoneyTransferServiceApplicationTests {
    private static final int PORT = 5500;
    private static int counter = 0;

    @Autowired
    TestRestTemplate restTemplate;
    public static GenericContainer<?> container = new GenericContainer<>("money_transfer_service")
            .withExposedPorts(PORT);

    @BeforeAll
    public static void setUp() {
        container.start();
    }

    @Test
    void transferTest() {
        final TransferRequest request = new TransferRequest(
                "1111111111111111",
                "11/28",
                "333",
                "2222222222222222",
                new Amount(33000, "RUR"));
        final String message = "{\"id\":\"" + (++counter) + "\"}";
        testContainer(request, message, "/transfer");
    }

    @Test
    void confirmTest() {
        ConfirmRequest request = new ConfirmRequest(String.valueOf(counter), "0000");
        String message = "{\"id\":\"" + counter + "\"}";
        testContainer(request, message, "/confirmOperation");
        request = new ConfirmRequest(null, "0000");
        message = "{\"message\":\"Транзакция не обнаружена или выполнена ранее\"," +
                "\"id\":" + counter + "}";
        testContainer(request, message, "/confirmOperation");
    }

    @Test
    void invalidInputTest() {
        TransferRequest request = new TransferRequest(
                "11111111111111110",
                "11/28",
                "333",
                "2222222222222222",
                new Amount(33000, "RUR"));
        final String message = "{\"message\":\"Некорректные данные карт\"," +
                "\"id\":-1}";
        testContainer(request, message, "/transfer");
    }

    private void testContainer(Object request, String message, String endpoint) {
        final ResponseEntity<String> forEntity =
                restTemplate.postForEntity("http://localhost:" +
                        container.getMappedPort(PORT) + endpoint, request, String.class);
        System.out.println("Request: " + request);
        final String response = forEntity.getBody();
        System.out.println("Response: " + response);
        Assertions.assertEquals(response, message);
    }
}
