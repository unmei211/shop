package omsu.softwareengineering.model.discount;

import java.sql.Timestamp;

public record DiscountModel(String id,
                            String description,
                            Timestamp startDate,
                            Timestamp endDate,
                            Boolean enabled,
                            String discountStrategyID) {
}
