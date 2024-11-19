package omsu.softwareengineering.model.product;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {
    private String id;
    private Long amount;
    private String categoryID;
    private String name;
}
