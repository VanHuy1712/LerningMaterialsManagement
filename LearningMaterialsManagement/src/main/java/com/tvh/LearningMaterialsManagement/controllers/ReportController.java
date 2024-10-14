/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.controllers;

import com.tvh.LearningMaterialsManagement.services.ReportService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Huy
 */
@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // Phương thức hiển thị trang báo cáo
    @GetMapping
    public String getReportsPage(Model model) {
        return "reports"; // Tên của view hiển thị các tùy chọn báo cáo
    }

    @GetMapping("/books-sold")
    public String getBooksSoldReport(Model model) {
        List<Object[]> statistics = reportService.getBooksSoldStatistics();
        model.addAttribute("statistics", statistics);

//        // In ra thống kê để kiểm tra
//        statistics.forEach(stat -> {
//            System.out.println("Tên sách: " + stat[0] + ", Số lượng: " + stat[1]);
//        });

        return "books_sold_report"; // Tên của view hiển thị báo cáo sách bán
    }
    
    // Thêm phương thức cho báo cáo top 5 thể loại sách bán chạy
    @GetMapping("/top-categories")
    public String getTopSellingCategoriesReport(Model model) {
        List<Object[]> topCategories = reportService.getTop5SellingCategories();
        model.addAttribute("topCategories", topCategories);
        return "top_categories_report"; // Tên của view hiển thị báo cáo thể loại sách bán chạy
    }
}
