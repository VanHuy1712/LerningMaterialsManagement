/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.controllers;

import com.tvh.LearningMaterialsManagement.models.User;
import com.tvh.LearningMaterialsManagement.models.Role;
import com.tvh.LearningMaterialsManagement.services.UserService;
import com.tvh.LearningMaterialsManagement.services.RoleService;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Huy
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping
    public String userList(@RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "fullName", required = false) String fullName,
            Model model) {

        List<User> userList;
        if ((username == null || username.isEmpty())
                && (email == null || email.isEmpty())
                && (phone == null || phone.isEmpty())
                && (fullName == null || fullName.isEmpty())) {
            // Trả về toàn bộ danh sách người dùng
            userList = userService.getAllUsers();
        } else {
            // Thực hiện tìm kiếm với các trường nhập
            userList = userService.searchUsers(username, email, phone, fullName);
        }
        model.addAttribute("userList", userList);
        return "users";  // Đảm bảo trả về đúng tên của template HTML
    }

    @GetMapping("/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());  // Khởi tạo đối tượng User để bind với form
        return "addUser";  // Trả về trang HTML createUser
    }

    // Xử lý yêu cầu thêm mới người dùng
    @PostMapping("/create")
    public String createUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            userService.createUser(user);  // Gọi tới service để lưu người dùng mới
            redirectAttributes.addFlashAttribute("successMessage", "Thêm người dùng thành công!"); // Thêm thông báo thành công
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi thêm người dùng."); // Thêm thông báo lỗi
        }
        return "redirect:/users";  // Chuyển hướng về trang danh sách người dùng
    }

    // Hiển thị form chỉnh sửa người dùng
    @GetMapping("/edit/{id}")
    public String getUserById(@PathVariable("id") Integer id, Model model) {
        User user = userService.getUserById(id);  // Gọi phương thức tìm người dùng theo ID
        List<Role> roles = roleService.getRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);// Truyền thông tin người dùng vào model
        return "editUser";                       // Trả về trang HTML hiển thị thông tin chi tiết
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") Integer id, @ModelAttribute("user") User user, Model model) {
        try {
            userService.updateUser(id, user);  // Gọi service để cập nhật thông tin người dùng
            model.addAttribute("message", "Người dùng đã được cập nhật thành công!");
        } catch (IOException e) {
            model.addAttribute("message", "Đã có lỗi xảy ra khi tải ảnh lên!");
            e.printStackTrace();
        }
        return "redirect:/users";  // Chuyển hướng về trang danh sách người dùng
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, @RequestParam("_method") String method, RedirectAttributes redirectAttributes) {
        if ("delete".equals(method)) {
            try {
                userService.deleteUser(id);
                redirectAttributes.addFlashAttribute("message", "Người dùng đã được xóa thành công!");
            } catch (NoSuchElementException e) {
                redirectAttributes.addFlashAttribute("error", "Người dùng không tồn tại!");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Đã có lỗi xảy ra khi xóa người dùng!");
            }
        }
        return "redirect:/users";  // Redirect to the users list page
    }
}
