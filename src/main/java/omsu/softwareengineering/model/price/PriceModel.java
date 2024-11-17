package omsu.softwareengineering.model.price;

import java.sql.Timestamp;

public record PriceModel
        (String id,
         Long price,
         Timestamp startDate,
         Timestamp endDate,
         String productID) {
}
