/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.controllers;

import com.tvh.LearningMaterialsManagement.models.Author;
import com.tvh.LearningMaterialsManagement.services.AuthorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Huy
 */
@Controller
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public String listAuthors(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Author> authorList;
        if (keyword != null && !keyword.isEmpty()) {
            authorList = authorService.searchAuthors(keyword); // Tìm kiếm vai trò
        } else {
            authorList = authorService.getAuthors(); // Lấy tất cả vai trò
        }
        model.addAttribute("authorList", authorList);
        return "authors";   // Đảm bảo trả về đúng tên của template HTML
    }

    // Trang hiển thị form thêm mới danh mục
    @GetMapping("/create")
    public String showCreateAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "addAuthor";
    }

    // Xử lý form thêm mới danh mục
    @PostMapping("/create")
    public String createAuthor(@ModelAttribute("author") Author author, Model model) {
        authorService.addAuthor(author); // Gọi service để lưu danh mục mới
        model.addAttribute("authorList", authorService.getAuthors()); // Cập nhật danh sách danh mục
        return "redirect:/authors"; // Chuyển hướng sau khi thêm thành công
    }

    @GetMapping("/edit/{id}")
    public String editAuthor(@PathVariable("id") int id, Model model) {
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        return "editAuthor"; // Trả về trang chỉnh sửa sách
    }

    @PostMapping("/edit/{id}")
    public String updateAuthor(@PathVariable("id") int id, @ModelAttribute("role") Author author, Model model) {
        Author existingAuthor = authorService.getAuthorById(id);

        existingAuthor.setFullNameAuthor(author.getFullNameAuthor());
        authorService.addAuthor(existingAuthor); 

        return "redirect:/authors";
    }

    @PostMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable("id") int id) {
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }

}
