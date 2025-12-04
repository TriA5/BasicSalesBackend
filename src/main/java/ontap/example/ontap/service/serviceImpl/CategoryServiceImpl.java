package ontap.example.ontap.service.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ontap.example.ontap.dto.CategoryDTO;
import ontap.example.ontap.entity.Category;
import ontap.example.ontap.entity.Product;
import ontap.example.ontap.repository.CategoryRepository;
import ontap.example.ontap.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public void createCategory(Category newCategory) {
        categoryRepository.save(newCategory);
    }

    public Category updateCategoryById(UUID idCategory, CategoryDTO dto) {
        Category category = categoryRepository.findById(idCategory)
                .orElseThrow(() -> new RuntimeException("Category không tồn tại"));

        category.setNameCategory(dto.getNameCategory());
        return categoryRepository.save(category);
    }

    public void deleteCategoryById(UUID id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            System.out.println("Category với id này không tồn tại!");
        }

    }

    public Category getCategoryById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category với id " + id + " không tồn tại!"));
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}
