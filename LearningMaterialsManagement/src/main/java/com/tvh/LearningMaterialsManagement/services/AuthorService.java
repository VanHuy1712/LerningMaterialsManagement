/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.services;

import com.tvh.LearningMaterialsManagement.models.Author;
import com.tvh.LearningMaterialsManagement.repositories.AuthorRepository;
import com.tvh.LearningMaterialsManagement.resourceException.ResourceNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Huy
 */
@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepo;

    // Chức năng thêm (Create)
    public Author addAuthor(Author author) {
        return authorRepo.save(author);
    }

    // Chức năng sửa (Update)
    public Author updateAuthor(Integer id, Author newAuthorData) {
        return authorRepo.findById(id).map(author -> {
            author.setFullNameAuthor(newAuthorData.getFullNameAuthor());
            // Set các thuộc tính khác nếu có
            return authorRepo.save(author);
        }).orElseThrow(() -> new ResourceNotFoundException("Author not found with id " + id));
    }

    // Chức năng xóa (Delete)
    public void deleteAuthor(Integer id) {
        authorRepo.deleteById(id);
    }

    // Lấy tất cả danh mục
    public List<Author> getAuthors() {
        return authorRepo.findAll();
    }
    
    public List<Author> getAuthorByFullName(String fullNameAuthor) {
        return authorRepo.findByFullNameAuthor(fullNameAuthor);
    }
    
    public Author getAuthorById(Integer id) {
        return authorRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
    }
    
    public List<Author> searchAuthors(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return authorRepo.findAll(); // Trả về tất cả vai trò nếu không có từ khóa
        }
        return authorRepo.findByFullNameAuthor(keyword); // Phương thức tìm kiếm
    }
}
