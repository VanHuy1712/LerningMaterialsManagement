/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.controllers;

import com.tvh.LearningMaterialsManagement.models.Role;
import com.tvh.LearningMaterialsManagement.services.RoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Huy
 */
@Controller
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String listRoles(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Role> roleList;
        if (keyword != null && !keyword.isEmpty()) {
            roleList = roleService.searchRoles(keyword); // Tìm kiếm vai trò
        } else {
            roleList = roleService.getRoles(); // Lấy tất cả vai trò
        }
        model.addAttribute("roleList", roleList);
        return "roles";   // Đảm bảo trả về đúng tên của template HTML
    }

    // Trang hiển thị form thêm mới danh mục
    @GetMapping("/create")
    public String showCreateRoleForm(Model model) {
        model.addAttribute("role", new Role());
        return "addRole";
    }

    // Xử lý form thêm mới danh mục
    @PostMapping("/create")
    public String createRole(@ModelAttribute("role") Role role, Model model) {
        roleService.addRole(role); // Gọi service để lưu danh mục mới
        model.addAttribute("roleList", roleService.getRoles()); // Cập nhật danh sách danh mục
        return "redirect:/roles"; // Chuyển hướng sau khi thêm thành công
    }

    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") int id, Model model) {
        Role role = roleService.getRoleById(id);
        model.addAttribute("role", role);
        return "editRole"; // Trả về trang chỉnh sửa sách
    }

    @PostMapping("/edit/{id}")
    public String updateRole(@PathVariable("id") int id, @ModelAttribute("role") Role role, Model model) {
        Role existingRole = roleService.getRoleById(id);

        existingRole.setRoleName(role.getRoleName()); // Cập nhật tên vai trò
        roleService.addRole(existingRole); // Lưu lại vai trò sau khi chỉnh sửa

        return "redirect:/roles"; // Chuyển hướng về trang danh sách vai trò sau khi cập nhật thành công
    }

    @PostMapping("/delete/{id}")
    public String deleteRole(@PathVariable("id") int id) {
        roleService.deleteRole(id); // Gọi service để xóa vai trò
        return "redirect:/roles"; // Chuyển hướng về danh sách sau khi xóa
    }

}
