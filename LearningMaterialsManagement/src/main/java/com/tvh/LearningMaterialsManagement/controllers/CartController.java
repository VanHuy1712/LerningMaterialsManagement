/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.controllers;

import com.tvh.LearningMaterialsManagement.models.Book;
import com.tvh.LearningMaterialsManagement.models.CartItem;
import com.tvh.LearningMaterialsManagement.models.DetailReceiptBook;
import com.tvh.LearningMaterialsManagement.models.Receipt;
import com.tvh.LearningMaterialsManagement.models.User;
import com.tvh.LearningMaterialsManagement.repositories.DetailReceiptBookRepository;
import com.tvh.LearningMaterialsManagement.repositories.ReceiptRepository;
import com.tvh.LearningMaterialsManagement.services.BookService;
import com.tvh.LearningMaterialsManagement.services.UserService;
import jakarta.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Huy
 */
@Controller
@RequestMapping("/cart")
public class CartController {

//    @Autowired
//    private CartService cartService;
//
    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private DetailReceiptBookRepository detailReceiptBookRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String viewCart(Model model, HttpSession session) {
        // Lấy giỏ hàng từ session
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // Tính tổng giá tiền
        long totalPrice = cart.stream()
                .mapToLong(CartItem::getTotalPrice)
                .sum();

//        // Tính tổng giá tiền với BigInteger
//        BigInteger totalPrice = cart.stream()
//                .map(item -> BigInteger.valueOf(item.getTotalPrice()))
//                .reduce(BigInteger.ZERO, BigInteger::add);
        // Truyền giỏ hàng và tổng giá vào model
        model.addAttribute("cartItems", cart); // Chỉnh sửa tên biến ở đây
        model.addAttribute("totalPrice", totalPrice);

        return "cart"; // Trả về trang giỏ hàng
    }

    @PostMapping("/add")
    @ResponseBody
    public String addToCart(@RequestParam("bookId") int bookId, @RequestParam("quantity") int quantity, HttpSession session) {
        // Lấy giỏ hàng từ session
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // Kiểm tra xem sách đã có trong giỏ hàng hay chưa
        boolean found = false;
        for (CartItem item : cart) {
            if (item.getBook().getId() == bookId) {
                // Nếu sách đã có trong giỏ hàng, cập nhật số lượng
                item.setQuantity(item.getQuantity() + quantity);
                found = true;
                break;
            }
        }

        // Nếu sách chưa có trong giỏ hàng, thêm mới
        if (!found) {
            Book book = bookService.getBookById(bookId); // Lấy sách từ service
            CartItem newItem = new CartItem(book, quantity);
            cart.add(newItem);
        }

        // Lưu giỏ hàng vào session
        session.setAttribute("cart", cart);

        return "Đã thêm vào giỏ hàng!";
    }

    @GetMapping("/direct-payment")
    public String directPayment(Principal principal, Model model, HttpSession session) {
        // Kiểm tra nếu người dùng đã đăng nhập
        if (principal == null) {
            return "redirect:/login"; // Chuyển đến trang đăng nhập
        }

        // Lấy thông tin người dùng
        String username = principal.getName();
        User user = userService.findbyUsername(username); // Giả sử bạn có phương thức tìm người dùng theo username

        // Truyền thông tin người dùng vào model
        model.addAttribute("fullName", user.getFullName());
        model.addAttribute("phone", user.getPhone());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("identityNumber", user.getIdentityNumber());
        model.addAttribute("direct", user.getDirect()); // Truyền thông tin địa chỉ

        return "directPayment"; // Trả về trang thanh toán trực tiếp
    }

    @PostMapping("/confirm-direct-payment")
    public String confirmDirectPayment(Principal principal, HttpSession session) {
        // Kiểm tra người dùng đã đăng nhập
        if (principal == null) {
            return "redirect:/login"; // Chuyển đến trang đăng nhập
        }

        // Lấy thông tin người dùng
        String username = principal.getName();
        User user = userService.findbyUsername(username);

        // Tạo hóa đơn
        Receipt receipt = new Receipt();
        receipt.setCreateDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        receipt.setStatus("Đang chuẩn bị hàng");
        receipt.setUserId(user);
        receipt.setDiscountId(null); // Giả sử không có mã giảm giá

        // Lưu hóa đơn
        receiptRepository.save(receipt); // Bạn cần có ReceiptRepository

        // Lấy giỏ hàng từ session
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            // Tạo chi tiết hóa đơn
            for (CartItem cartItem : cart) {
                DetailReceiptBook detail = new DetailReceiptBook();
                detail.setBookId(cartItem.getBook());
                detail.setReceiptId(receipt);
                detail.setQuantity(cartItem.getQuantity());
                detail.setTotalUnitPrice(cartItem.getTotalPrice());
                detailReceiptBookRepository.save(detail); // Bạn cần có DetailReceiptBookRepository
            }
        }

        // Xóa giỏ hàng sau khi thanh toán
        session.removeAttribute("cart");

        return "redirect:/"; // Chuyển đến trang thành công
    }

}
