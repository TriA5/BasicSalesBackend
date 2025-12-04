package ontap.example.ontap.service.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ontap.example.ontap.dto.ProductDTO;
import ontap.example.ontap.entity.Category;
import ontap.example.ontap.entity.Product;
import ontap.example.ontap.repository.CategoryRepository;
import ontap.example.ontap.repository.ProductRepository;
import ontap.example.ontap.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Product createProduct(ProductDTO productDTO) {
        // Lấy category theo id
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category không tồn tại"));

        //Tạo product và gán giá trị từ DTO
        Product product = new Product();
        product.setNameProduct(productDTO.getNameProduct());
        product.setSoLuong(productDTO.getSoLuong());
        product.setPrice(productDTO.getPrice());
        product.setCategory(category);
        // lưu
        return productRepository.save(product);
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Product updateProduct(ProductDTO dto, UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product không tồn tại"));

        product.setNameProduct(dto.getNameProduct());
        product.setSoLuong(dto.getSoLuong());
        product.setPrice(dto.getPrice());

        // Lấy Category từ DB
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category không tồn tại"));
        product.setCategory(category);

        return productRepository.save(product);
    }

    public void deleteProductById(UUID idProduct){
        if(productRepository.existsById(idProduct)){
            productRepository.deleteById(idProduct);
        }else {
            System.out.println("Product với id này không tồn tại!");
        }
    }
    public Product getProductById(UUID idProduct){
        return productRepository.findById(idProduct)
               .orElseThrow(() -> new RuntimeException("Product không tồn tại"));
    }
}
