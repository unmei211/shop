package omsu.softwareengineering.model.product_discount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDiscountModel {
    private String id;
    private String productID;
    private String discountID;
}
