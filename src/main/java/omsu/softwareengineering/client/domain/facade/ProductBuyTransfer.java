package omsu.softwareengineering.client.domain.facade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductBuyTransfer {
    private String productName;
    private Long count;
}
