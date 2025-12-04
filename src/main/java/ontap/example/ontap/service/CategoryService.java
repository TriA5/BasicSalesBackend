package ontap.example.ontap.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ontap.example.ontap.dto.CategoryDTO;
import ontap.example.ontap.entity.Category;

// @Service
public interface CategoryService {
    public void createCategory(Category newCategory);

    public Category updateCategoryById(UUID idCategory, CategoryDTO dto);

    public void deleteCategoryById(UUID id);

    public Category getCategoryById(UUID id);

    public List<Category> getAllCategory();

}
