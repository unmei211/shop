package omsu.softwareengineering.model.discount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountModel {
    private String id;
    private String description;
    private Timestamp startDate;
    private Timestamp endDate;
    private Boolean enabled;
    private String discountStrategyID;
}
