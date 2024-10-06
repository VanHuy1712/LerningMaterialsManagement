/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.repositories;

import com.tvh.LearningMaterialsManagement.models.Author;
import com.tvh.LearningMaterialsManagement.models.Book;
import com.tvh.LearningMaterialsManagement.models.DetailAuthorBook;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Huy
 */
@Repository
public interface DetailAuthorBookRepository extends JpaRepository<DetailAuthorBook, Integer>{
    List<DetailAuthorBook> findByBookId(Book bookId);
}
