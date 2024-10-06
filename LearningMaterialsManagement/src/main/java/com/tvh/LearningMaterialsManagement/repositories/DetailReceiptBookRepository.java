/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.repositories;

import com.tvh.LearningMaterialsManagement.models.DetailReceiptBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Huy
 */
@Repository
public interface DetailReceiptBookRepository extends JpaRepository<DetailReceiptBook, Integer>{
    
}
