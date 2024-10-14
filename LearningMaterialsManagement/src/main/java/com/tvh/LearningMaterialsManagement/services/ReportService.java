/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.services;

import com.tvh.LearningMaterialsManagement.repositories.DetailReceiptBookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Huy
 */
@Service
public class ReportService {
    @Autowired
    private DetailReceiptBookRepository detailReceiptBookRepository;
    @Autowired
    private EntityManager entityManager;

    public List<Object[]> getBooksSoldStatistics() {
        return detailReceiptBookRepository.getBooksSoldStatistics();
    }
    
    // Phương thức thống kê top 5 thể loại sách bán chạy
    public List<Object[]> getTop5SellingCategories() {
        String queryString = "SELECT c.nameCategory, SUM(d.quantity) AS totalSold " +
                "FROM Category c " +
                "JOIN DetailCategoryBook dcb ON c.id = dcb.categoryId.id " +
                "JOIN DetailReceiptBook d ON dcb.bookId.id = d.bookId.id " +
                "GROUP BY c.id " +
                "ORDER BY totalSold DESC";

        Query query = entityManager.createQuery(queryString);
        query.setMaxResults(5);

        return query.getResultList();
    }
}
