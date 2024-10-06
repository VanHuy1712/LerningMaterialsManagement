/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.controllers;

import com.tvh.LearningMaterialsManagement.models.Book;
import com.tvh.LearningMaterialsManagement.services.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Huy
 */
@Controller
public class HomeController {

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String bookList(
            @RequestParam(value = "page", defaultValue = "0") int page, // Trang mặc định là 0 (trang đầu tiên)
            @RequestParam(value = "size", defaultValue = "8") int size, // Kích thước mặc định là 8 sách/trang
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "namePublisher", required = false) String namePublisher,
            @RequestParam(value = "fullNameAuthor", required = false) String fullNameAuthor, // Thêm tham số cho tác giả
            @RequestParam(value = "nameCategory", required = false) String nameCategory,
            @RequestParam(value = "minPrice", required = false) Long minPrice,
            @RequestParam(value = "maxPrice", required = false) Long maxPrice,
            Model model) {
        
        List<Book> bookList;
        
        List<Book> userList;
        if ((name == null || name.isEmpty())
            && (namePublisher == null || namePublisher.isEmpty())
            && (fullNameAuthor == null || fullNameAuthor.isEmpty())
            && (nameCategory == null || nameCategory.isEmpty())
            && minPrice == null
            && maxPrice == null) {
            // Trả về toàn bộ danh sách người dùng
            bookList = bookService.getBooks();
        } else {
            // Thực hiện tìm kiếm với các trường nhập
            bookList = bookService.searchBooks(name, namePublisher, fullNameAuthor, nameCategory, minPrice, maxPrice);
        }
        model.addAttribute("books", bookList);
        
        //        List<Book> books = bookService.getBooks();
        //        model.addAttribute("books", books);
        
        
        // Lấy danh sách sách theo phân trang
        // Page<Book> booksPage = bookService.getBooksPaginated(page, size);

        // Pagination
        Page<Book> booksPage = bookService.getBooksPaginated(page, size, name, namePublisher, fullNameAuthor, nameCategory, minPrice, maxPrice);

        
        // Thêm danh sách sách và các thông tin khác vào model
        model.addAttribute("bookList", booksPage.getContent()); // Danh sách sách của trang hiện tại
        model.addAttribute("totalPages", booksPage.getTotalPages()); // Tổng số trang
        model.addAttribute("currentPage", page); // Trang hiện tại
        model.addAttribute("title", "Danh sách sách");

        return "index"; // Trả về template hiển thị sách
    }
    
    
}
