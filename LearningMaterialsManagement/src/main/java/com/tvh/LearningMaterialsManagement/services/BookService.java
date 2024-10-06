/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.services;

import com.tvh.LearningMaterialsManagement.models.Author;
import com.tvh.LearningMaterialsManagement.models.Book;
import com.tvh.LearningMaterialsManagement.models.Category;
import com.tvh.LearningMaterialsManagement.models.DetailAuthorBook;
import com.tvh.LearningMaterialsManagement.models.DetailCategoryBook;
import com.tvh.LearningMaterialsManagement.repositories.AuthorRepository;
import com.tvh.LearningMaterialsManagement.repositories.BookRepository;
import com.tvh.LearningMaterialsManagement.repositories.CategoryRepository;
import com.tvh.LearningMaterialsManagement.repositories.DetailAuthorBookRepository;
import com.tvh.LearningMaterialsManagement.repositories.DetailCategoryBookRepository;
import com.tvh.LearningMaterialsManagement.resourceException.ResourceNotFoundException;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author Huy
 */
@Service
public class BookService {
    @Autowired
    private BookRepository bookRepo;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private AuthorRepository authorRepo;
    @Autowired
    private DetailAuthorBookRepository detailAuthorBookRepo;
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private DetailCategoryBookRepository detailCategoryBookRepo;
    
    // Lấy tất cả danh mục
    public List<Book> getBooks() {
        return bookRepo.findAll();
    }

    // Chức năng thêm (Create)
    public Book addBook(Book book, List<Integer> authorIds, List<Integer> categoryIds) throws IOException {

        book.setName(book.getName());
        book.setPrice(book.getPrice());
        book.setDescription(book.getDescription());
        book.setAmount(book.getAmount());
        book.setPublisherId(book.getPublisherId());
        
        if (book.getFile() != null && !book.getFile().isEmpty()) {
            // Upload the image to Cloudinary and get the URL
            String imageUrl = cloudinaryService.uploadImage(book.getFile());
            book.setAvatarBook(imageUrl);// Set the URL as the avatar field
        }
        
//        return bookRepo.save(book);

        Book savedBook = bookRepo.save(book);

        // Lưu thông tin vào bảng DetailAuthorBook
        for (Integer authorId : authorIds) {
            Author author = authorRepo.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
            DetailAuthorBook detail = new DetailAuthorBook();
            detail.setBookId(savedBook);
            detail.setAuthorId(author);
            detailAuthorBookRepo.save(detail);
        }
        
        // Lưu thông tin vào bảng DetailCategoryBook
    for (Integer categoryId : categoryIds) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        DetailCategoryBook detail = new DetailCategoryBook();
        detail.setBookId(savedBook);
        detail.setCategoryId(category);
        detailCategoryBookRepo.save(detail);
    }

        return savedBook;
    }

    // Chức năng sửa (Update)
    public Book updateBook(Integer id, Book book, List<Integer> authorIds, List<Integer> categoryIds) throws IOException{;
        
        Book existingBook = bookRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        
        existingBook.setName(book.getName());
        existingBook.setPrice(book.getPrice());
        existingBook.setAmount(book.getAmount());
        existingBook.setDescription(book.getDescription());
        existingBook.setPublisherId(book.getPublisherId());
        
        if (book.getFile() != null && !book.getFile().isEmpty()) {
            // Upload the image to Cloudinary and get the URL
            String imageUrl = cloudinaryService.uploadImage(book.getFile());
            existingBook.setAvatarBook(imageUrl);// Set the URL as the avatar field
        }
        
//        return bookRepo.save(existingBook);

        Book updatedBook = bookRepo.save(existingBook);

        // Cập nhật các tác giả
        List<DetailAuthorBook> existingDetails = detailAuthorBookRepo.findByBookId(updatedBook);

        // Xóa các tác giả không còn liên kết
        for (DetailAuthorBook detail : existingDetails) {
            if (!authorIds.contains(detail.getAuthorId().getId())) {
                detailAuthorBookRepo.delete(detail);
            }
        }

        // Thêm tác giả mới
        for (Integer authorId : authorIds) {
            boolean alreadyLinked = existingDetails.stream()
                    .anyMatch(detail -> detail.getAuthorId().getId().equals(authorId));

            if (!alreadyLinked) {
                Author author = authorRepo.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
                DetailAuthorBook newDetail = new DetailAuthorBook();
                newDetail.setBookId(updatedBook);
                newDetail.setAuthorId(author);
                detailAuthorBookRepo.save(newDetail);
            }
        }
        
        // Cập nhật các thể loại
        List<DetailCategoryBook> existingCategoryDetails = detailCategoryBookRepo.findByBookId(updatedBook);

        // Xóa các thể loại không còn liên kết
        for (DetailCategoryBook detail : existingCategoryDetails) {
            if (!categoryIds.contains(detail.getCategoryId().getId())) {
                detailCategoryBookRepo.delete(detail);
            }
        }

        // Thêm thể loại mới
        for (Integer categoryId : categoryIds) {
            boolean alreadyLinked = existingCategoryDetails.stream()
                    .anyMatch(detail -> detail.getCategoryId().getId().equals(categoryId));

            if (!alreadyLinked) {
                Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
                DetailCategoryBook newDetail = new DetailCategoryBook();
                newDetail.setBookId(updatedBook);
                newDetail.setCategoryId(category);
                detailCategoryBookRepo.save(newDetail);
            }
        }

        return updatedBook;
    }

    // Chức năng xóa (Delete)
    public void deleteBook(Integer id) {
        bookRepo.deleteById(id);
    }
    
    // Method to get paginated books
    public Page<Book> getBooksPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookRepo.findAll(pageable);
    }
    
    public Book getBookById(Integer id) {
        return bookRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
    }
    
    //Khong phan trang
    public List<Book> searchBooks(String name, String namePublisher, String fullNameAuthor, String nameCategory, Long minPrice, Long maxPrice) {
        return bookRepo.findBooksByCriteria(name, namePublisher, fullNameAuthor, nameCategory, minPrice, maxPrice);
    }

    //Phân trang
    // Update this method to return paginated results
    public Page<Book> getBooksPaginated(int page, int size, String name, String namePublisher, String fullNameAuthor, String nameCategory, Long minPrice, Long maxPrice) {
        Pageable pageable = PageRequest.of(page, size);
        return bookRepo.findAllByCriteria(pageable, name, namePublisher, fullNameAuthor, nameCategory, minPrice, maxPrice);
    }
}
