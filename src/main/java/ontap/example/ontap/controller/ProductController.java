package ontap.example.ontap.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ontap.example.ontap.dto.ProductDTO;
import ontap.example.ontap.entity.Product;
import ontap.example.ontap.service.ProductService;

@Controller
@CrossOrigin("*")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public Product createProduct(@RequestBody ProductDTO productDTO){
        return productService.createProduct(productDTO);
    }
    @GetMapping
    public List<Product> getAllProduct(){
        return productService.getAllProduct();
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable UUID id, @RequestBody ProductDTO productDTO){
        return productService.updateProduct(productDTO, id);
    }
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable UUID id){
        productService.deleteProductById(id);
    }
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable UUID id){
        return productService.getProductById(id);
    }
}
