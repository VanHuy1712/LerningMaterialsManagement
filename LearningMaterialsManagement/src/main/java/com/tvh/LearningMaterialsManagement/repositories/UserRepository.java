/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.repositories;

import com.tvh.LearningMaterialsManagement.models.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Huy
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    List<User> findByFullName(String fullName);
    User findByUsername(String username);
    List<User> findByUsernameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndPhoneContainingIgnoreCaseAndFullNameContainingIgnoreCase(String username, String email, String phone, String fullName);
}
