package com.viperbank.api.dto;

import com.viperbank.api.model.Account;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDTO {
    private String number;
    private String agency;
    private BigDecimal balance;
    private BigDecimal limit;

    public static AccountDTO fromEntity(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setNumber(account.getNumber());
        dto.setAgency(account.getAgency());
        dto.setBalance(account.getBalance());
        dto.setLimit(account.getLimit());
        return dto;
    }

    public Account toEntity() {
        return Account.builder()
                .number(this.number)
                .agency(this.agency)
                .balance(this.balance)
                .limit(this.limit)
                .build();
    }
}
