package org.kamsystem.common.exception;

import java.util.List;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Getter
public class InvalidRequestBodyException extends RuntimeException {

    private final List<FieldError> fieldErrors;

    public InvalidRequestBodyException(BindingResult bindingResult) {
        super("Invalid request parameters");
        this.fieldErrors = bindingResult.getFieldErrors();
    }
}
