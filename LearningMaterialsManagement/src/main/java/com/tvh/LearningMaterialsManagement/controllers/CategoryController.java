/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.controllers;

import com.tvh.LearningMaterialsManagement.models.Category;
import com.tvh.LearningMaterialsManagement.services.CategoryService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Huy
 */
@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService cateService;
    
    @GetMapping
    public String categoryList(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Category> categoryList;
        if (keyword != null && !keyword.isEmpty()) {
            categoryList = cateService.searchCategories(keyword); // Tìm kiếm vai trò
        } else {
            categoryList = cateService.getCates(); // Lấy tất cả vai trò
        }
        model.addAttribute("cateList", categoryList);
        return "categories";  // Đảm bảo trả về đúng tên của template HTML
    }

    // Trang hiển thị form thêm mới danh mục
    @GetMapping("/create")
    public String showCreateCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "addCategory";
    }

    // Xử lý form thêm mới danh mục
    @PostMapping("/create")
    public String createCategory(@ModelAttribute("category") Category category, Model model) {
        cateService.addCate(category); // Gọi service để lưu danh mục mới
        model.addAttribute("cateList", cateService.getCates()); // Cập nhật danh sách danh mục
        return "redirect:/categories"; // Chuyển hướng sau khi thêm thành công
    }

    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable("id") int id, Model model) {
        Category category = cateService.getCateById(id);
        model.addAttribute("category", category);
        return "editCategory";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable("id") int id, @ModelAttribute("category") Category category, Model model) {
        Category existingCategory = cateService.getCateById(id);
        
        existingCategory.setNameCategory(category.getNameCategory()); 
        cateService.addCate(existingCategory);

        return "redirect:/categories"; // Chuyển hướng về trang danh sách vai trò sau khi cập nhật thành công
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") int id) {
        cateService.deleteCate(id); // Gọi service để xóa vai trò
        return "redirect:/categories"; // Chuyển hướng về danh sách sau khi xóa
    }
}
