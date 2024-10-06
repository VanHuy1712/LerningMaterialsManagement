/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.services;

import com.tvh.LearningMaterialsManagement.models.Publisher;
import com.tvh.LearningMaterialsManagement.repositories.PublisherRepository;
import com.tvh.LearningMaterialsManagement.resourceException.ResourceNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Huy
 */
@Service
public class PublisherService {
    @Autowired
    private PublisherRepository publisherRepo;

    // Chức năng thêm (Create)
    public Publisher addPublisher(Publisher publisher) {
        return publisherRepo.save(publisher);
    }

    // Chức năng xóa (Delete)
    public void deletePublisher(Integer id) {
        publisherRepo.deleteById(id);
    }

    // Lấy tất cả danh mục
    public List<Publisher> getPublishers() {
        return publisherRepo.findAll();
    }
    
    public Publisher getPublisherById(Integer id) {
        return publisherRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
    }
    
    public List<Publisher> searchPublishers(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return publisherRepo.findAll(); // Trả về tất cả vai trò nếu không có từ khóa
        }
        return publisherRepo.findByNamePublisherContainingIgnoreCase(keyword); // Phương thức tìm kiếm
    }
}
