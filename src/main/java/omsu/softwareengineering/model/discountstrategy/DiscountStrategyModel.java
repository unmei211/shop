package omsu.softwareengineering.model.discountstrategy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscountStrategyModel {
    private String id;
    private String description;
    private String method;
}
