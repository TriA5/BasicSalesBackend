package ontap.example.ontap.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class CategoryDTO {
    private UUID id;

    private String nameCategory;
}
