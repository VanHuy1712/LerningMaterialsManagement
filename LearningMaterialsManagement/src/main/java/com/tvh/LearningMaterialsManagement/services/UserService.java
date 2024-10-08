/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.services;

import com.tvh.LearningMaterialsManagement.models.Role;
import com.tvh.LearningMaterialsManagement.models.User;
import com.tvh.LearningMaterialsManagement.repositories.RoleRepository;
import com.tvh.LearningMaterialsManagement.repositories.UserRepository;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Huy
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Lấy danh sách tất cả người dùng
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUserById(Integer id) {
        return userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
    }

    // Tìm người dùng theo tên người dùng (username)
    public List<User> findByFullName(String fullname) {
        return userRepo.findByFullName(fullname);
    }

    public User findbyUsername(String username) {
        return userRepo.findByUsername(username);
    }

    // Xóa người dùng theo ID
    public void deleteUser(Integer id) {
        userRepo.deleteById(id);
    }

    public User createUser(User user) throws IOException {
        user.setFullName(user.getFullName());
        user.setEmail(user.getEmail());
        user.setPhone(user.getPhone());
        user.setUsername(user.getUsername());
        user.setIdentityNumber(user.getIdentityNumber());
        user.setBirthday(user.getBirthday());
        user.setDirect(user.getDirect());

        // Mã hóa mật khẩu bằng BCrypt
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getFile() != null && !user.getFile().isEmpty()) {
            // Upload the image to Cloudinary and get the URL
            String imageUrl = cloudinaryService.uploadImage(user.getFile());
            user.setAvatar(imageUrl); // Set the URL as the avatar field
        }

        // Thiết lập vai trò mặc định với ID là 3
        Role defaultRole = roleRepo.findById(3).orElse(null);
        user.setRoleId(defaultRole); // Set vai trò cho người dùng

        return userRepo.save(user); // Save user to the database
    }

    public User updateUser(Integer id, User user) throws IOException {
        // Tìm người dùng trong DB
        User existingUser = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        // Cập nhật thông tin người dùng
        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setUsername(user.getUsername());
        existingUser.setIdentityNumber(user.getIdentityNumber());
        existingUser.setBirthday(user.getBirthday());
        existingUser.setDirect(user.getDirect());

        // Cập nhật vai trò người dùng
        if (user.getRoleId() != null) {
            existingUser.setRoleId(user.getRoleId());
        }

        if (user.getFile() != null && !user.getFile().isEmpty()) {
            // Upload the image to Cloudinary and get the URL
            String imageUrl = cloudinaryService.uploadImage(user.getFile());
            existingUser.setAvatar(imageUrl); // Set the URL as the avatar field
        }

        return userRepo.save(existingUser);  // Lưu lại thông tin người dùng đã được cập nhật
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepo.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(), 
//                user.getPassword(), 
//                user.getAuthorities()
//        );
//    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username); // Fetch user from repository
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        // Assign roles based on roleId
        if (user.getRoleId() != null) {
            switch (user.getRoleId().getId()) {
                case 1:
                    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // Admin role
                    break;
                case 2:
                    authorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE")); // Employee role
                    break;
                case 3:
                    authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER")); // Customer role
                    break;
                default:
                    break;
            }
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities // Pass the authorities we created
        );
    }

    public List<User> searchUsers(String username, String email, String phone, String fullName) {
        // Thực hiện truy vấn để tìm kiếm người dùng dựa trên các trường nhập vào
        return userRepo.findByUsernameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndPhoneContainingIgnoreCaseAndFullNameContainingIgnoreCase(username, email, phone, fullName);
    }

}
