package com.restaurant.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Menu Item Modifier group.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemModifier {

    private Integer modifierId;
    private Integer restaurantId;
    private Integer itemId;
    private String name;
    private Boolean isRequired;
    private Integer minSelection;
    private Integer maxSelection;

    @Builder.Default
    private List<ModifierOption> options = new ArrayList<>();
}
