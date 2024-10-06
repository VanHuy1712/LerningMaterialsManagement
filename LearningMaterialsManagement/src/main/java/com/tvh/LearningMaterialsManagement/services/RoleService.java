/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.services;

import com.tvh.LearningMaterialsManagement.models.Role;
import com.tvh.LearningMaterialsManagement.repositories.RoleRepository;
import com.tvh.LearningMaterialsManagement.resourceException.ResourceNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Huy
 */
@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepo;

    public Role addRole(Role role) {
        String roleName = role.getRoleName().toUpperCase(); // Chuyển thành chữ in hoa

        role.setRoleName(roleName);

        return roleRepo.save(role); // Lưu vai trò vào cơ sở dữ liệu
    }

    public void deleteRole(Integer id) {
        roleRepo.deleteById(id);
    }

    public List<Role> getRoles() {
        return roleRepo.findAll();
    }
    
    public Role getRoleById(Integer id) {
        return roleRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid role Id: " + id));
    }
    
    public List<Role> searchRoles(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return roleRepo.findAll(); // Trả về tất cả vai trò nếu không có từ khóa
        }
        return roleRepo.findByRoleNameContainingIgnoreCase(keyword); // Phương thức tìm kiếm
    }
}
