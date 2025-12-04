package ontap.example.ontap.service;

import java.util.List;
import java.util.UUID;

import ontap.example.ontap.dto.ProductDTO;
import ontap.example.ontap.entity.Product;

public interface ProductService {
    public Product createProduct(ProductDTO dto);

    public List<Product> getAllProduct();

    public Product updateProduct(ProductDTO dto, UUID productId);

    public void deleteProductById(UUID idProduct);

    public Product getProductById(UUID idProduct);
}
