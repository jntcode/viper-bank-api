package com.viperbank.api.dto;

import com.viperbank.api.model.News;
import lombok.Data;

@Data
public class NewsDTO {
    private String icon;
    private String description;

    public static NewsDTO fromEntity(News news) {
        NewsDTO dto = new NewsDTO();
        dto.setIcon(news.getIcon());
        dto.setDescription(news.getDescription());
        return dto;
    }

    public News toEntity() {
        return News.builder()
                .icon(this.icon)
                .description(this.description)
                .build();
    }
}
