package com.viperbank.api.dto;

import com.viperbank.api.model.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private AccountDTO account;
    private List<FeatureDTO> features;
    private CardDTO card;
    private List<NewsDTO> news;

    public static UserDTO fromEntity(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        if (user.getAccount() != null) {
            dto.setAccount(AccountDTO.fromEntity(user.getAccount()));
        }
        if (user.getFeatures() != null) {
            dto.setFeatures(user.getFeatures().stream()
                    .map(FeatureDTO::fromEntity)
                    .collect(Collectors.toList()));
        }
        if (user.getCard() != null) {
            dto.setCard(CardDTO.fromEntity(user.getCard()));
        }
        if (user.getNews() != null) {
            dto.setNews(user.getNews().stream()
                    .map(NewsDTO::fromEntity)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public User toEntity() {
        User user = User.builder()
                .name(this.name)
                .build();
        if (this.account != null) {
            user.setAccount(this.account.toEntity());
        }
        if (this.features != null) {
            user.setFeatures(this.features.stream()
                    .map(FeatureDTO::toEntity)
                    .collect(Collectors.toList()));
        }
        if (this.card != null) {
            user.setCard(this.card.toEntity());
        }
        if (this.news != null) {
            user.setNews(this.news.stream()
                    .map(NewsDTO::toEntity)
                    .collect(Collectors.toList()));
        }
        return user;
    }
}
