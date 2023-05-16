package com.jmg.checkagro.check.controller.errorhandling;

import com.jmg.checkagro.check.constants.Constants;
import com.jmg.checkagro.check.utils.DateTimeUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.jmg.checkagro.check.constants.Constants.*;

@ControllerAdvice
@RestController
@Log4j2
class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest req, Exception ex) {
        Map<String, Object> result = new HashMap<>();
        result.put(TIMESTAMP, DateTimeUtils.now().toEpochDay());
        result.put(STATUS, HttpStatus.NOT_FOUND.value());
        result.put(Constants.ERROR, ex.getMessage());
        result.put(PATH, new UrlPathHelper().getPathWithinApplication(req));
        log.error(Constants.ERROR + ex.getMessage());
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }


}
