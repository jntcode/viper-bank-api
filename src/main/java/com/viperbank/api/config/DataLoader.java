package com.viperbank.api.config;

import com.viperbank.api.model.*;
import com.viperbank.api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                User user = User.builder()
                        .name("Sieve")
                        .account(Account.builder()
                                .number("12345-6")
                                .agency("0001")
                                .balance(new BigDecimal("2500.00"))
                                .limit(new BigDecimal("5000.00"))
                                .build())
                        .card(Card.builder()
                                .number("1111 2222 3333 4444")
                                .limit(new BigDecimal("10000.00"))
                                .build())
                        .features(List.of(
                                Feature.builder().icon("transfer").description("Transferência PIX").build(),
                                Feature.builder().icon("payment").description("Pagamento de contas").build(),
                                Feature.builder().icon("invest").description("Investimentos").build()
                        ))
                        .news(List.of(
                                News.builder().icon("rocket").description("Nova funcionalidade: Pix automatico!").build(),
                                News.builder().icon("gift").description("Indique um amigo e ganhe R$50!").build()
                        ))
                        .build();
                repository.save(user);
                System.out.println(">>> Usuário de exemplo criado com sucesso!");
            }
        };
    }
}
