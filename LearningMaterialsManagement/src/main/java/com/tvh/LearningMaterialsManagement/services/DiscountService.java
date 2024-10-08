/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.services;

import com.tvh.LearningMaterialsManagement.models.Discount;
import com.tvh.LearningMaterialsManagement.repositories.DiscountRepository;
import com.tvh.LearningMaterialsManagement.resourceException.ResourceNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Huy
 */
@Service
public class DiscountService {
    @Autowired
    private DiscountRepository discountRepo;

    // Chức năng thêm (Create)
    public Discount addDiscount(Discount discount) {
        return discountRepo.save(discount);
    }

    // Chức năng xóa (Delete)
    public void deleteDiscount(Integer id) {
        discountRepo.deleteById(id);
    }

    // Lấy tất cả danh mục
    public List<Discount> getDiscounts() {
        return discountRepo.findAll();
    }
    
    public Discount getDiscountById(Integer id) {
        return discountRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
    }
    
    public Discount getDiscountByCode(String code) {
        return discountRepo.findByDiscountCode(code);
    }
}
