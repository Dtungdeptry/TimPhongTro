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

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByUsername(loginRequest.getUsername());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai tên tài khoản!");
        }

        String inputPassword = loginRequest.getPassword();
        String storedPassword = user.getPassword();

        boolean isPasswordMatch = storedPassword.startsWith("$2a$")
                ? passwordEncoder.matches(inputPassword, storedPassword)
                : inputPassword.equals(storedPassword);

        if (!isPasswordMatch) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai mật khẩu!");
        }

        // Nếu mật khẩu chưa được mã hóa, mã hóa và cập nhật
        if (!storedPassword.startsWith("$2a$")) {
            String encodedPassword = passwordEncoder.encode(inputPassword);
            user.setPassword(encodedPassword);
            userService.updateUserLogin(user);
        }

        // Tạo JWT token
        String token = jwtUtil.generateToken(user.getUsername());
        int roleId = user.getRole() != null ? user.getRole().getId() : 0;

        // Trả về phản hồi với token, userId và roleId
        return ResponseEntity.ok(new AuthResponse(token, user.getId(), roleId));
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody RegisterRequest registerRequest) throws MessagingException {
        try {
            // In ra log trước khi thực hiện đăng ký
            System.out.println("Bắt đầu đăng ký người dùng: " + registerRequest.getUsername());

            if (userService.findByUsername(registerRequest.getUsername()) != null) {
                System.out.println("Username đã tồn tại: " + registerRequest.getUsername());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username đã tồn tại!");
            }

            if (registerRequest.getPassword() == null || registerRequest.getConfirmPassword() == null ||
                    !registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
                System.out.println("Mật khẩu và xác nhận mật khẩu không khớp!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mật khẩu và xác nhận mật khẩu không khớp!");
            }

            // Sinh OTP trước khi tạo người dùng
            String otp = emailService.generateOTP();
            if (otp == null || otp.isEmpty()) {
                System.out.println("Không thể tạo OTP!");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không thể tạo OTP!");
            }

            User newUser = new User();
            newUser.setUsername(registerRequest.getUsername());
            newUser.setPassword(registerRequest.getPassword());
            newUser.setEmail(registerRequest.getEmail());
            newUser.setPhone(registerRequest.getPhone());
            newUser.setFullName(registerRequest.getFullName());
            newUser.setAddress(registerRequest.getAddress());
            newUser.setVerified(false);
            newUser.setOtp(otp);  // Gán OTP vào đối tượng User

            Role defaultRole = roleRepository.findById(2)
                    .orElseThrow(() -> new RuntimeException("Role không tồn tại!"));
            newUser.setRole(defaultRole);

            userService.registerUser(newUser);  // Lưu người dùng vào CSDL

            // Gửi email OTP
            emailService.sendOtpEmail(registerRequest.getEmail(), otp);
            System.out.println("Đã gửi OTP đến email: " + registerRequest.getEmail());

            return ResponseEntity.ok("Mã OTP đã được gửi đến email của bạn.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đăng ký không thành công: " + e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(@RequestParam String username, @RequestParam String otp) {
        User user = userService.findByUsername(username);

        if (user == null) {
            System.out.println("Người dùng không tồn tại!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Người dùng không tồn tại!");
        }

        System.out.println("OTP trong CSDL: " + user.getOtp());  // In OTP từ CSDL
        System.out.println("OTP người dùng nhập: " + otp);        // In OTP từ người dùng

        if (user.getOtp() != null && user.getOtp().equals(otp)) {
            user.setVerified(true);
            user.setOtp(null);  // Xóa OTP sau khi xác thực
            userService.updateUserLogin(user);

            System.out.println("Xác thực thành công!");
            return ResponseEntity.ok("Xác thực thành công! Bạn có thể đăng nhập.");
        } else {
            System.out.println("OTP không chính xác hoặc đã hết hạn!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP không chính xác hoặc đã hết hạn!");
        }
    }


    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(@RequestParam String username) throws MessagingException {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Người dùng không tồn tại!");
        }

        if (user.isVerified()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tài khoản của bạn đã được xác thực rồi!");
        }

        String newOtp = emailService.generateOTP();
        user.setOtp(newOtp);
        userService.updateUserLogin(user);
        emailService.sendOtpEmail(user.getEmail(), newOtp);

        return ResponseEntity.ok("Mã OTP mới đã được gửi!");
    }

}

