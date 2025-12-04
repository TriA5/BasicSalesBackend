package ontap.example.ontap.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class ProductDTO {
    private String nameProduct;

    private int soLuong;
    
    private UUID categoryId; // chỉ gửi id category

    private double price;
}

