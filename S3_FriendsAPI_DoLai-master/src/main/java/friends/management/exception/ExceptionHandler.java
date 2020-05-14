package friends.management.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object>
    handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                 HttpHeaders headers,
                                 HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error_code", 400);
        List<Map<String, Object>> errorList = new ArrayList<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(
                        x -> {
                            Map<String, Object> errorMap = new LinkedHashMap<>();
                            errorMap.put(camelCaseToScoreCase(x.getField()), x.getDefaultMessage());
                            errorList.add(errorMap);
                        });

        Collections.sort(errorList, (Map<String, Object> m1, Map<String, Object> m2) -> {
            String key1 = m1.keySet().iterator().next();
            String key2 = m2.keySet().iterator().next();
            return key1.compareTo(key2);
        });

        body.put("error_fields", errorList);

        return new ResponseEntity<>(body, headers, status);

    }
    private String camelCaseToScoreCase(String input) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1-$2";
        return input.replaceAll(regex, replacement).toLowerCase();
    }
}
