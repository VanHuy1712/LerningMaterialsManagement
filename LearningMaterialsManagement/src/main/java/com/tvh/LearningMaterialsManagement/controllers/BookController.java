/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.controllers;

import com.tvh.LearningMaterialsManagement.models.Author;
import com.tvh.LearningMaterialsManagement.models.Book;
import com.tvh.LearningMaterialsManagement.models.Category;
import com.tvh.LearningMaterialsManagement.models.DetailAuthorBook;
import com.tvh.LearningMaterialsManagement.models.DetailCategoryBook;
import com.tvh.LearningMaterialsManagement.models.Publisher;
import com.tvh.LearningMaterialsManagement.repositories.DetailAuthorBookRepository;
import com.tvh.LearningMaterialsManagement.repositories.DetailCategoryBookRepository;
import com.tvh.LearningMaterialsManagement.services.AuthorService;
import com.tvh.LearningMaterialsManagement.services.BookService;
import com.tvh.LearningMaterialsManagement.services.CategoryService;
import com.tvh.LearningMaterialsManagement.services.PublisherService;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private PublisherService publisherService;
    @Autowired
    private CategoryService cateService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private DetailAuthorBookRepository detailAuthorBookRepo;
    @Autowired
    private DetailCategoryBookRepository detailCategoryBookRepo;

    // Hiển thị danh sách sách với phân trang
    @GetMapping
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

        return "books"; // Trả về template hiển thị sách
    }
    
    // Trang hiển thị form thêm mới danh mục
    @GetMapping("/create")
    public String showCreateBookForm(Model model) {
//        model.addAttribute("book", new Book()); // Gửi một đối tượng Category trống cho form
//        List<Publisher> publishers = publisherService.getPublishers();
//        model.addAttribute("publishers", publishers);
//        List<Category> categories = cateService.getCates();
//        model.addAttribute("categories", categories);
//        return "addBook"; // Tên của template HTML chứa form thêm danh mục

        model.addAttribute("book", new Book());

        List<Publisher> publishers = publisherService.getPublishers();
        List<Category> categories = cateService.getCates();
        List<Author> authors = authorService.getAuthors(); // Lấy danh sách tác giả

        model.addAttribute("publishers", publishers);
        model.addAttribute("categories", categories);
        model.addAttribute("authors", authors); // Truyền danh sách tác giả tới view

        return "addBook"; // Trả về view thêm sách
    }
    
//    // Xử lý form thêm mới danh mục
//    @PostMapping("/create")
//    public String createBook(@ModelAttribute("book") Book book, Model model) throws IOException {
//        bookService.addBook(book); // Gọi service để lưu danh mục mới
//        model.addAttribute("bookList", bookService.getBooks()); // Cập nhật danh sách danh mục
//        return "redirect:/books"; // Chuyển hướng sau khi thêm thành công
//    }
    @PostMapping("/create")
    public String createBook(
            @ModelAttribute("book") Book book,
            @RequestParam("authorIds") List<Integer> authorIds, // Lấy danh sách authorIds từ form
            @RequestParam("categoryIds") List<Integer> categoryIds,
            Model model) throws IOException {
        bookService.addBook(book, authorIds, categoryIds); // Gọi service để lưu sách và tác giả
        model.addAttribute("books", bookService.getBooks());
        return "redirect:/books"; // Chuyển hướng sau khi thêm thành công
    }
    
    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") int id, Model model) {
//        List<Publisher> publishers = publisherService.getPublishers();
//        model.addAttribute("publishers", publishers);
//        List<Category> categories = cateService.getCates();
//        model.addAttribute("categories", categories);
//        Book book = bookService.getBookById(id);
//        model.addAttribute("book", book);
//        return "editBook"; // Trả về trang chỉnh sửa sách

        List<Publisher> publishers = publisherService.getPublishers();
        List<Category> categories = cateService.getCates();
        List<Author> authors = authorService.getAuthors();
        Book book = bookService.getBookById(id);

        // Lấy danh sách các tác giả đã liên kết với sách
        List<DetailAuthorBook> details = detailAuthorBookRepo.findByBookId(book);
        List<Integer> selectedAuthorIds = details.stream()
                .map(detail -> detail.getAuthorId().getId())
                .collect(Collectors.toList());
        
        // Lấy danh sách các thể loại đã liên kết với sách
        List<DetailCategoryBook> categoryDetails = detailCategoryBookRepo.findByBookId(book);
        List<Integer> selectedCategoryIds = categoryDetails.stream()
                .map(detail -> detail.getCategoryId().getId())
                .collect(Collectors.toList());

        model.addAttribute("publishers", publishers);
        model.addAttribute("categories", categories);
        model.addAttribute("authors", authors);
        model.addAttribute("selectedAuthorIds", selectedAuthorIds); // Truyền danh sách tác giả đã chọn
        model.addAttribute("selectedCategoryIds", selectedCategoryIds);
        model.addAttribute("book", book);

        return "editBook"; // Trả về trang chỉnh sửa sách
    }
    
//    @PostMapping("/edit/{id}")
//    public String updateBook(@PathVariable("id") Integer id, @ModelAttribute("book") Book book, Model model) {
//        try {
//            bookService.updateBook(id, book);  
//            model.addAttribute("message", "Sách đã được cập nhật thành công!");
//        } catch (IOException e) {
//            model.addAttribute("message", "Đã có lỗi xảy ra khi tải ảnh lên!");
//            e.printStackTrace();
//        }
//        return "redirect:/books";
//    }
    
    @PostMapping("/edit/{id}")
    public String updateBook(
            @PathVariable("id") Integer id,
            @ModelAttribute("book") Book book,
            @RequestParam("authorIds") List<Integer> authorIds, // Lấy danh sách authorIds từ form
            @RequestParam("categoryIds") List<Integer> categoryIds,
            Model model) {
        try {
            bookService.updateBook(id, book, authorIds, categoryIds); // Gọi service để cập nhật sách và tác giả
            model.addAttribute("message", "Sách đã được cập nhật thành công!");
        } catch (IOException e) {
            model.addAttribute("message", "Đã có lỗi xảy ra khi tải ảnh lên!");
            e.printStackTrace();
        }
        return "redirect:/books";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.deleteBook(id);
        return "redirect:/books"; // Chuyển hướng về danh sách sau khi xóa
    }

}
