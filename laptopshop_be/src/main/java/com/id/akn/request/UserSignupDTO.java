package com.id.akn.request;

import com.id.akn.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupDTO {
    @NotBlank(message = "Tên không được để trống")
    private String name;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(com|org|net|edu|gov)$", message = "Email phải có đuôi .com, .org, .net, .edu, hoặc .gov")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 7, max = 20, message = "Mật khẩu phải có độ dài từ 7 đến 20 ký tự")
    @Pattern(regexp = "^[^\\s]*$", message = "Mật khẩu không được chứa dấu cách")
    private String password;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^0\\d{9,10}$", message = "Số điện thoại phải bắt đầu bằng số 0 và có độ dài từ 10 đến 11 chữ số, không chứa khoảng trắng hoặc ký tự đăc biệt")
    private String phoneNumber;

    private String gender;

    private LocalDate birthday;
}
