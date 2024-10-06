/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.services;

import com.tvh.LearningMaterialsManagement.models.Receipt;
import com.tvh.LearningMaterialsManagement.repositories.ReceiptRepository;
import com.tvh.LearningMaterialsManagement.repositories.UserRepository;
import com.tvh.LearningMaterialsManagement.resourceException.ResourceNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Huy
 */
@Service
public class ReceiptService {
     @Autowired
    private ReceiptRepository receiptRepo;

    @Autowired
    private UserRepository userRepo;  // To fetch user data based on user_id

    // Get a list of receipts
    public List<Receipt> getAllReceipts() {
        return receiptRepo.findAll();
    }

    // Get receipt by ID
    public Receipt getReceiptById(Integer id) {
        return receiptRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid receipt Id: " + id));
    }

    // Update receipt status
    public Receipt updateReceiptStatus(Integer id, String status) {
        Receipt receipt = getReceiptById(id);
        receipt.setStatus(status);
        return receiptRepo.save(receipt);
    }
    
    public Receipt addReceipt(Receipt receipt) {
        return receiptRepo.save(receipt);
    }

    // Optionally, you can add a delete function here
    public void deleteReceipt(Integer id) {
        receiptRepo.deleteById(id);
    }
}
