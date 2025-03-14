package codegym.c10.webservice.validator;

import codegym.c10.webservice.payload.request.RegisterUserRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, RegisterUserRequest> {
    @Override
    public boolean isValid(RegisterUserRequest registerUserRequest, ConstraintValidatorContext context) {
        if (registerUserRequest == null) {
            return true;
        }
        String password = registerUserRequest.getPassword();
        String confirmPassword = registerUserRequest.getConfirmPassword();

            return password != null && password.equals(confirmPassword);
    }
}
