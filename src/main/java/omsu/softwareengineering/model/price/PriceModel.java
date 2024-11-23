package omsu.softwareengineering.model.price;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PriceModel {
    private String id;
    private Long price;
    private Timestamp startDate;
    private Timestamp endDate;
    private String productID;
}