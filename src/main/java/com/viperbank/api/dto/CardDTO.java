package com.viperbank.api.dto;

import com.viperbank.api.model.Card;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CardDTO {
    private String number;
    private BigDecimal creditLimit;

    public static CardDTO fromEntity(Card card) {
        CardDTO dto = new CardDTO();
        dto.setNumber(card.getNumber());
        dto.setCreditLimit(card.getCreditLimit());
        return dto;
    }

    public Card toEntity() {
        return Card.builder()
                .number(this.number)
                .creditLimit(this.creditLimit)
                .build();
    }
}
