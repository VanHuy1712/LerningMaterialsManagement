/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.repositories;

import com.tvh.LearningMaterialsManagement.models.DetailReceiptBook;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Huy
 */
@Repository
public interface DetailReceiptBookRepository extends JpaRepository<DetailReceiptBook, Integer> {

//    @Query("SELECT d.bookId, SUM(d.quantity) as totalSold FROM DetailReceiptBook d GROUP BY d.bookId")
//    List<Object[]> getBooksSoldStatistics();
//    @Query("SELECT b.name, SUM(d.quantity) as totalSold FROM DetailReceiptBook d JOIN Book b ON d.bookId = b.id GROUP BY b.name")
//    List<Object[]> getBooksSoldStatistics();
    @Query("SELECT b.name, SUM(d.quantity) FROM DetailReceiptBook d JOIN d.bookId b GROUP BY b.name")
    List<Object[]> getBooksSoldStatistics();

}
