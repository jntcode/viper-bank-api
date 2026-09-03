package com.viperbank.api.dto;

import com.viperbank.api.model.Feature;
import lombok.Data;

@Data
public class FeatureDTO {
    private String icon;
    private String description;

    public static FeatureDTO fromEntity(Feature feature) {
        FeatureDTO dto = new FeatureDTO();
        dto.setIcon(feature.getIcon());
        dto.setDescription(feature.getDescription());
        return dto;
    }

    public Feature toEntity() {
        return Feature.builder()
                .icon(this.icon)
                .description(this.description)
                .build();
    }
}
