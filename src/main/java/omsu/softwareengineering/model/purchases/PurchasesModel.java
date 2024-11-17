package omsu.softwareengineering.model.purchases;

import java.sql.Timestamp;

public record PurchasesModel(String id,
                             String productID,
                             String userID,
                             String paymentTypeID,
                             Timestamp date,
                             String purchaseStatusID) {
}
