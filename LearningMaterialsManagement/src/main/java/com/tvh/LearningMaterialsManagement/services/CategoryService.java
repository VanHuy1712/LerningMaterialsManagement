/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.services;

import com.tvh.LearningMaterialsManagement.models.Category;
import com.tvh.LearningMaterialsManagement.repositories.CategoryRepository;
import com.tvh.LearningMaterialsManagement.resourceException.ResourceNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Huy
 */
@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository cateRepo;

    // Chức năng thêm (Create)
    public Category addCate(Category category) {
        return cateRepo.save(category);
    }
    
    public List<Category> getCategoriesByName(String cateName){
        return cateRepo.findByNameCategory(cateName);
    }

    // Chức năng xóa (Delete)
    public void deleteCate(Integer id) {
        cateRepo.deleteById(id);
    }

    // Lấy tất cả danh mục
    public List<Category> getCates() {
        return cateRepo.findAll();
    }
    
    public Category getCateById(Integer id) {
        return cateRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
    }
    
    public List<Category> searchCategories(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return cateRepo.findAll(); // Trả về tất cả vai trò nếu không có từ khóa
        }
        return cateRepo.findByNameCategory(keyword); // Phương thức tìm kiếm
    }
    
}
