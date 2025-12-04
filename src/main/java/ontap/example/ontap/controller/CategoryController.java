package ontap.example.ontap.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ontap.example.ontap.dto.CategoryDTO;
// import lombok.AllArgsConstructor;
// import lombok.NoArgsConstructor;
import ontap.example.ontap.entity.Category;
import ontap.example.ontap.service.CategoryService;

@Controller
@CrossOrigin("*")
@RestController
@RequestMapping("/api/categorys")
// @AllArgsConstructor
// @NoArgsConstructor
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategory(){
        return categoryService.getAllCategory();
    }

    @PostMapping
    public void createCategory(@RequestBody Category newCategory){
        categoryService.createCategory(newCategory);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable UUID id ,@RequestBody CategoryDTO categoryDTO){
         return categoryService.updateCategoryById(id, categoryDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCategoryById(@PathVariable UUID id){
        categoryService.deleteCategoryById(id);
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable UUID id){
        return categoryService.getCategoryById(id);
    }
}
