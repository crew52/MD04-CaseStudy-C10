package codegym.c10.webservice.validator;

import codegym.c10.webservice.payload.request.CreateAccountTeacherRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, CreateAccountTeacherRequest > {
    @Override
    public boolean isValid(CreateAccountTeacherRequest createAccountTeacherRequest, ConstraintValidatorContext context) {
        if (createAccountTeacherRequest == null) {
            return true;
        }
        String password = createAccountTeacherRequest.getPassword();
        String confirmPassword = createAccountTeacherRequest.getConfirmPassword();

            return password != null && password.equals(confirmPassword);
    }
}
