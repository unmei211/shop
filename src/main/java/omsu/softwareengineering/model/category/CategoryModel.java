package omsu.softwareengineering.model.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CategoryModel {
    private String id;
    private String name;
};
