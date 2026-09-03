package com.viperbank.api.dto;

import com.viperbank.api.model.Card;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CardDTO {
    private String number;
    private BigDecimal limit;

    public static CardDTO fromEntity(Card card) {
        CardDTO dto = new CardDTO();
        dto.setNumber(card.getNumber());
        dto.setLimit(card.getLimit());
        return dto;
    }

    public Card toEntity() {
        return Card.builder()
                .number(this.number)
                .limit(this.limit)
                .build();
    }
}
