package pl.coderslab;

import jakarta.validation.ConstraintViolation;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationUtils {
    public static <T> String validationMessage(Set<ConstraintViolation<T>> constraintViolations) {
        return constraintViolations.stream()
                .map(cv -> cv.getPropertyPath().toString()
                        .concat(" : ")
                        .concat(cv.getMessage()))
                .collect(Collectors.joining(", "));
    }
}
