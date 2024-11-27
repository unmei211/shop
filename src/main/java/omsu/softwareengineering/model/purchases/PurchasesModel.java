package omsu.softwareengineering.model.purchases;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchasesModel {
    private String id;
    private String productID;
    private String userID;
    private String paymentTypeID;
    private Timestamp date;
    private String purchaseStatusID;
    private Long price;
}
