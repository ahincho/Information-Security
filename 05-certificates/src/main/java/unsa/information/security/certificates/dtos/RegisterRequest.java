package unsa.information.security.certificates.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotEmpty()
    @NotBlank()
    private String firstname;
    @NotEmpty()
    @NotBlank()
    private String lastname;
    @NotEmpty()
    @NotBlank()
    @Email()
    private String email;
    @NotEmpty()
    @NotBlank()
    @Size(min = 8, max = 32, message = "Password must be between 8 and 32 characters")
    private String password;
    public RegisterRequest(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
