package omsu.softwareengineering.model.paymenttype;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PaymentTypeModel {
    private String id;
    private String type;
}
