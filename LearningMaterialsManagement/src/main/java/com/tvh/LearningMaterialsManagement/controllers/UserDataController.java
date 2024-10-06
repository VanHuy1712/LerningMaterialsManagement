/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.controllers;

import com.tvh.LearningMaterialsManagement.services.UserService;
import com.tvh.LearningMaterialsManagement.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author Huy
 */
@ControllerAdvice
public class UserDataController {
    
    @Autowired
    private UserService userService;
    
    @ModelAttribute
    public void CommonAttributes(Model model){
        Authentication authencation = SecurityContextHolder.getContext().getAuthentication();
        
        User u = new User();
        if (authencation != null && authencation.getPrincipal() instanceof UserDetails){
            UserDetails userDetails = (UserDetails) authencation.getPrincipal();
            u = this.userService.findbyUsername(userDetails.getUsername());
        } else if (authencation != null) {
            u = this.userService.findbyUsername(authencation.getName());
        }
        
        model.addAttribute("current_user", u);
    }
    
}
