package com.mpphw.springbootBackend.controller;

import com.mpphw.springbootBackend.dto.AuthenticationRequest;
import com.mpphw.springbootBackend.dto.RegisterRequest;
import com.mpphw.springbootBackend.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequest request, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("error", "Validation failed");
            responseBody.put("details", bindingResult.getAllErrors());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }

        try {
            return ResponseEntity.ok(authService.register(request));
        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("error", e.getMessage());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@Valid @RequestBody AuthenticationRequest request, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("error", "Validation failed");
            responseBody.put("details", bindingResult.getAllErrors());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(authService.authenticate(request));
    }
}
