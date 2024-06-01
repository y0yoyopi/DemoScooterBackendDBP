package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.auth.application;


import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.auth.domain.AuthService;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.auth.dto.JwtAuthResponse;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.auth.dto.LoginReq;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.auth.dto.RegisterReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/hola")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginReq req) {
        return ResponseEntity.ok(authService.login(req));
    }
    @PostMapping("/register")
    public ResponseEntity<JwtAuthResponse> register(@RequestBody RegisterReq req) {
        return ResponseEntity.ok(authService.register(req));
    }


}
