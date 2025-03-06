package com.example.TimPhongTro.Controller;

import com.example.TimPhongTro.Config.JwtUtil;
import com.example.TimPhongTro.Entity.Role;
import com.example.TimPhongTro.Entity.User;
import com.example.TimPhongTro.Model.Dto.AuthResponse;
import com.example.TimPhongTro.Model.Dto.LoginRequest;
import com.example.TimPhongTro.Model.Dto.RegisterRequest;
import com.example.TimPhongTro.Repository.RoleRepository;
import com.example.TimPhongTro.Service.EmailService;
import com.example.TimPhongTro.Service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    @PostMapping("/loginUser")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByUsername(loginRequest.getUsername());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai tên tài khoản!");
        }

        String inputPassword = loginRequest.getPassword();
        String storedPassword = user.getPassword();

        if (storedPassword.startsWith("$2a$")) {
            if (passwordEncoder.matches(inputPassword, storedPassword)) {
                String token = jwtUtil.generateToken(user.getUsername());
                return ResponseEntity.ok(new AuthResponse(token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai mật khẩu!");
            }
        } else {
            if (inputPassword.equals(storedPassword)) {
                String encodedPassword = passwordEncoder.encode(inputPassword);

                user.setPassword(encodedPassword);
                userService.updateUserLogin(user);

                String token = jwtUtil.generateToken(user.getUsername());
                return ResponseEntity.ok(new AuthResponse(token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai mật khẩu!");
            }
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody RegisterRequest registerRequest) throws MessagingException {
        if (userService.findByUsername(registerRequest.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username đã tồn tại!");
        }

        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mật khẩu và xác nhận mật khẩu không khớp!");
        }

//        if (registerRequest.getOtp() == null || !registerRequest.getOtp().equals("EXPECTED_OTP")) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP không chính xác!");
//        }

        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(registerRequest.getPassword());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPhone(registerRequest.getPhone());
        newUser.setFullName(registerRequest.getFullName());
        newUser.setAddress(registerRequest.getAddress());
//        newUser.setOtp(registerRequest.getOtp());
        newUser.setVerified(false);

        Role defaultRole = roleRepository.findById(2)
                .orElseThrow(() -> new RuntimeException("Role không tồn tại!"));
        newUser.setRole(defaultRole);

        userService.registerUser(newUser);
        return ResponseEntity.ok(newUser);
//        emailService.sendOtpEmail(registerRequest.getEmail(), registerRequest.getOtp());

//        return ResponseEntity.ok("Mã OTP đã được gửi đến email của bạn. Hãy xác thực tài khoản!");
    }
//
//
//
//    @PostMapping("/verify")
//    public ResponseEntity<?> verifyOtp(@RequestParam String username, @RequestParam String otp) {
//        User user = userService.findByUsername(username);
//
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Người dùng không tồn tại!");
//        }
//
//        if (user.getOtp() != null && user.getOtp().equals(otp)) {
//            user.setVerified(true);
//            user.setOtp(null);
//            userService.updateUserLogin(user);
//
//            return ResponseEntity.ok("Xác thực thành công! Bạn có thể đăng nhập.");
//        } else {
//            // Nếu OTP không đúng hoặc đã hết hạn
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP không chính xác hoặc đã hết hạn!");
//        }
//    }
//
//
//    @PostMapping("/resend-otp")
//    public ResponseEntity<?> resendOtp(@RequestParam String username) throws MessagingException {
//        User user = userService.findByUsername(username);
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Người dùng không tồn tại!");
//        }
//
//        if (user.isVerified()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tài khoản của bạn đã được xác thực rồi!");
//        }
//
//        String newOtp = emailService.generateOTP();
//        user.setOtp(newOtp);
//        userService.updateUserLogin(user);
//        emailService.sendOtpEmail(user.getEmail(), newOtp);
//
//        return ResponseEntity.ok("Mã OTP mới đã được gửi!");
//    }

}

