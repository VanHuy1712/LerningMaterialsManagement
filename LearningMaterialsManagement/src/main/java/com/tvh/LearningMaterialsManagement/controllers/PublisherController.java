/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.controllers;

import com.tvh.LearningMaterialsManagement.models.Publisher;
import com.tvh.LearningMaterialsManagement.services.PublisherService;
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
@RequestMapping("/publishers")
public class PublisherController {
    
    @Autowired
    private PublisherService pubService;

    @GetMapping
    public String publisherList(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Publisher> publishers;
        if (keyword != null && !keyword.isEmpty()) {
            publishers = pubService.searchPublishers(keyword);
        } else {
            publishers = pubService.getPublishers();
        }
        model.addAttribute("publisherList", publishers);
        return "publishers";  // Đảm bảo trả về đúng tên của template HTML
    }

    // Trang hiển thị form thêm mới danh mục
    @GetMapping("/create")
    public String showCreatePublisherForm(Model model) {
        model.addAttribute("publisher", new Publisher());
        return "addPublisher";
    }

    // Xử lý form thêm mới danh mục
    @PostMapping("/create")
    public String createPublisher(@ModelAttribute("publisher") Publisher publisher, Model model) {
        pubService.addPublisher(publisher); // Gọi service để lưu danh mục mới
        model.addAttribute("publisherList", pubService.getPublishers()); // Cập nhật danh sách danh mục
        return "redirect:/publishers"; // Chuyển hướng sau khi thêm thành công
    }

    @GetMapping("/edit/{id}")
    public String editPublisher(@PathVariable("id") int id, Model model) {
        Publisher publisher = pubService.getPublisherById(id);
        model.addAttribute("publisher", publisher);
        return "editPublisher";
    }

    @PostMapping("/edit/{id}")
    public String updatePublisher(@PathVariable("id") int id, @ModelAttribute("publisher") Publisher publisher, Model model) {
        Publisher existingPublisher = pubService.getPublisherById(id);
        
        existingPublisher.setNamePublisher(publisher.getNamePublisher()); 
        pubService.addPublisher(existingPublisher);

        return "redirect:/publishers"; // Chuyển hướng về trang danh sách vai trò sau khi cập nhật thành công
    }

    @PostMapping("/delete/{id}")
    public String deletePublisher(@PathVariable("id") int id) {
        pubService.deletePublisher(id); // Gọi service để xóa vai trò
        return "redirect:/publishers"; // Chuyển hướng về danh sách sau khi xóa
    }
}
