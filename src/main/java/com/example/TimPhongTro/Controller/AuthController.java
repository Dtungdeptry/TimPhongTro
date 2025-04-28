package com.example.TimPhongTro.Controller;

import com.example.TimPhongTro.Config.JwtUtil;
import com.example.TimPhongTro.Entity.Role;
import com.example.TimPhongTro.Entity.User;
import com.example.TimPhongTro.Model.Dto.AuthResponse;
import com.example.TimPhongTro.Model.Dto.LoginRequest;
import com.example.TimPhongTro.Model.Dto.RegisterRequest;
import com.example.TimPhongTro.Repository.RoleRepository;
import com.example.TimPhongTro.Repository.UserRepository;
import com.example.TimPhongTro.Service.EmailService;
import com.example.TimPhongTro.Service.UserService;
import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            // Tìm người dùng theo tên đăng nhập
            User user = userService.findByUsername(loginRequest.getUsername());

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai tên tài khoản!");
            }

            String inputPassword = loginRequest.getPassword();
            String storedPassword = user.getPassword();

            // Kiểm tra mật khẩu
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

        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đã xảy ra lỗi khi xử lý yêu cầu: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody RegisterRequest registerRequest) throws MessagingException {
        try {
            System.out.println("Bắt đầu đăng ký người dùng: " + registerRequest.getUsername());

            if (userService.findByUsername(registerRequest.getUsername()) != null) {
                System.out.println("Username đã tồn tại: " + registerRequest.getUsername());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username đã tồn tại!");
            }

            String passwordPattern = "(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}";
            if (!registerRequest.getPassword().matches(passwordPattern)) {
                System.out.println("Mật khẩu không hợp lệ! Mật khẩu phải có ít nhất 1 chữ hoa, 1 ký tự đặc biệt và 1 chữ số.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mật khẩu phải có ít nhất 1 chữ hoa, 1 ký tự đặc biệt và 1 chữ số.");
            }

                if (!isValidPhoneNumber(registerRequest.getPhone())) {
                System.out.println("Số điện thoại không hợp lệ: " + registerRequest.getPhone());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Số điện thoại phải có 10 chữ số!");
            }

            if (!isValidEmail(registerRequest.getEmail())) {
                System.out.println("Email không hợp lệ: " + registerRequest.getEmail());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email không hợp lệ!");
            }

            if (userRepository.findByEmail(registerRequest.getEmail()) != null) {
                System.out.println("Email đã tồn tại: " + registerRequest.getEmail());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email đã tồn tại!");
            }

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
            newUser.setOtp(otp);

            Role defaultRole = roleRepository.findById(2)
                    .orElseThrow(() -> new RuntimeException("Role không tồn tại!"));
            newUser.setRole(defaultRole);

            userService.registerUser(newUser);

            emailService.sendOtpEmail(registerRequest.getEmail(), otp);
            System.out.println("Đã gửi OTP đến email: " + registerRequest.getEmail());

            return ResponseEntity.ok("Mã OTP đã được gửi đến email của bạn.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đăng ký không thành công: " + e.getMessage());
        }
    }

    // Kiểm tra số điện thoại có đúng 10 chữ số
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\d{10}");
    }

    // Kiểm tra email đúng cú pháp
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(@RequestParam String username, @RequestParam String otp) {
        try {
            User user = userService.findByUsername(username);

            if (user == null) {
                System.out.println("Người dùng không tồn tại!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Người dùng không tồn tại!");
            }

            // Kiểm tra OTP
            if (user.getOtp() != null && user.getOtp().equals(otp)) {
                user.setVerified(true);
                user.setOtp(null);  // Xóa OTP sau khi xác thực
                userService.updateUserLogin(user);

                return ResponseEntity.ok("Xác thực thành công! Bạn có thể đăng nhập.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP không chính xác hoặc đã hết hạn!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã có lỗi xảy ra: " + e.getMessage());
        }
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(@RequestParam String username) {
        try {
            User user = userService.findByUsername(username);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Người dùng không tồn tại!");
            }

            if (user.isVerified()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tài khoản của bạn đã được xác thực rồi!");
            }

            // Tạo OTP mới và gửi email
            String newOtp = emailService.generateOTP();
            user.setOtp(newOtp);
            userService.updateUserLogin(user);
            emailService.sendOtpEmail(user.getEmail(), newOtp);

            return ResponseEntity.ok("Mã OTP mới đã được gửi!");
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi gửi email OTP: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã có lỗi xảy ra: " + e.getMessage());
        }
    }

}

