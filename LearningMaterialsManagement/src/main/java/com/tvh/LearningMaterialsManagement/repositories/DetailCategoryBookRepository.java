/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.repositories;

import com.tvh.LearningMaterialsManagement.models.Book;
import com.tvh.LearningMaterialsManagement.models.DetailCategoryBook;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Huy
 */
@Repository
public interface DetailCategoryBookRepository extends JpaRepository<DetailCategoryBook, Integer>{
    List<DetailCategoryBook> findByBookId(Book bookId);
}
