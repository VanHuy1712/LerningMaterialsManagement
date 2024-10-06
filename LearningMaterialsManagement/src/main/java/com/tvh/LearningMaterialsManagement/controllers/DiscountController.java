/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.controllers;

import com.tvh.LearningMaterialsManagement.models.Discount;
import com.tvh.LearningMaterialsManagement.services.DiscountService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Huy
 */
@Controller
@RequestMapping("/discounts")
public class DiscountController {
    @Autowired
    private DiscountService discountService;

    @GetMapping
    public String roleList(Model model) {
        List<Discount> roles = discountService.getDiscounts();
        model.addAttribute("discountList", roles);
        return "discounts";  // Đảm bảo trả về đúng tên của template HTML
    }

    // Trang hiển thị form thêm mới danh mục
    @GetMapping("/create")
    public String showCreateDiscountForm(Model model) {
        model.addAttribute("discount", new Discount());
        return "addDiscount";
    }

    // Xử lý form thêm mới danh mục
    @PostMapping("/create")
    public String createDiscount(@ModelAttribute("discount") Discount discount, Model model) {
        discountService.addDiscount(discount); // Gọi service để lưu danh mục mới
        model.addAttribute("discountList", discountService.getDiscounts()); // Cập nhật danh sách danh mục
        return "redirect:/discounts"; // Chuyển hướng sau khi thêm thành công
    }

    @GetMapping("/edit/{id}")
    public String editDiscount(@PathVariable("id") int id, Model model) {
        Discount role = discountService.getDiscountById(id);
        model.addAttribute("publisher", role);
        return "editDiscount";
    }

    @PostMapping("/edit/{id}")
    public String updateDiscount(@PathVariable("id") int id, @ModelAttribute("role") Discount discount, Model model) {
        Discount existingDiscount = discountService.getDiscountById(id);
        
        existingDiscount.setDiscountCode(discount.getDiscountCode()); 
        discountService.addDiscount(existingDiscount);

        return "redirect:/discounts"; // Chuyển hướng về trang danh sách vai trò sau khi cập nhật thành công
    }

    @PostMapping("/delete/{id}")
    public String deleteDiscount(@PathVariable("id") int id) {
        discountService.deleteDiscount(id); // Gọi service để xóa vai trò
        return "redirect:/discounts"; // Chuyển hướng về danh sách sau khi xóa
    }
}
