/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.controllers;

import com.tvh.LearningMaterialsManagement.models.Receipt;
import com.tvh.LearningMaterialsManagement.models.User;
import com.tvh.LearningMaterialsManagement.services.ReceiptService;
import com.tvh.LearningMaterialsManagement.services.UserService;
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
@RequestMapping("/receipts")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private UserService userService; // Assuming you have a user service

    @GetMapping
    public String listReceipts(Model model) {
        List<Receipt> receiptList = receiptService.getAllReceipts();
        model.addAttribute("receiptList", receiptList);
        return "receipts";   // Ensure this matches your Thymeleaf template name
    }

//    @GetMapping("/edit/{id}")
//    public String editReceipt(@PathVariable("id") int id, Model model) {
//        Receipt receipt = receiptService.getReceiptById(id);
//        User user = receipt.getUserId();  // Giả sử Receipt có quan hệ với User thông qua userId
//
//        model.addAttribute("receipt", receipt);
//        model.addAttribute("userName", user.getFullName()); // Gán thông tin người dùng
//        model.addAttribute("statuses", List.of("Đang chuẩn bị hàng", "Đơn bị hủy", "Đơn hàng đang giao", "Đơn hàng giao thành công"));
//
//        return "editReceipt"; // Trả về trang editReceipt.html
//    }
//
//    @PostMapping("/edit/{id}")
//    public String updateReceipt(@PathVariable("id") int id, @RequestParam("status") String status) {
//        receiptService.updateReceiptStatus(id, status); // Cập nhật trạng thái hóa đơn
//        return "redirect:/receipts"; // Chuyển hướng về danh sách hóa đơn sau khi cập nhật
//    }
    
    @GetMapping("/edit/{id}")
    public String editReceipt(@PathVariable("id") int id, Model model) {
        Receipt receipt = receiptService.getReceiptById(id);  // Lấy thông tin hóa đơn dựa vào id
        model.addAttribute("receipt", receipt);  // Gán receipt vào model để hiển thị trên form
        model.addAttribute("statuses", List.of("Đang chuẩn bị hàng", "Đơn bị hủy", "Đơn hàng đang giao", "Đơn hàng giao thành công"));  // Danh sách các trạng thái để chọn
        return "editReceipt";  // Trả về trang editReceipt.html
    }

    @PostMapping("/edit/{id}")
    public String updateReceipt(@PathVariable("id") int id, @ModelAttribute("receipt") Receipt receipt, Model model) {
        Receipt existingReceipt = receiptService.getReceiptById(id);  // Tìm hóa đơn hiện tại

        existingReceipt.setStatus(receipt.getStatus());  // Cập nhật trạng thái
        receiptService.addReceipt(existingReceipt);  // Lưu hóa đơn sau khi cập nhật

        return "redirect:/receipts";  // Chuyển hướng về danh sách hóa đơn sau khi cập nhật thành công
    }
    
//    @GetMapping("/edit/{id}")
//    public String editPublisher(@PathVariable("id") int id, Model model) {
//        Receipt receipt = receiptService.getReceiptById(id);
//        model.addAttribute("receipt", receipt);
//        return "editReceipt";
//    }
//
//    @PostMapping("/edit/{id}")
//    public String updatePublisher(@PathVariable("id") int id, @ModelAttribute("receipt") Receipt receipt, Model model) {
//        Receipt existingReceipt = receiptService.getReceiptById(id);
//        
//        existingReceipt.setStatus(receipt.getStatus()); 
//        receiptService.addReceipt(existingReceipt);
//
//        return "redirect:/receipts"; // Chuyển hướng về trang danh sách vai trò sau khi cập nhật thành công
//    }

    @PostMapping("/delete/{id}")
    public String deleteReceipt(@PathVariable("id") int id) {
        receiptService.deleteReceipt(id); // Delete receipt
        return "redirect:/receipts"; // Redirect to the list after deletion
    }
}
