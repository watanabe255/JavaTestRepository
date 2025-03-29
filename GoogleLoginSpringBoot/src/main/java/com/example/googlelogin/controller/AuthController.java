package com.example.googlelogin.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
//import com.google.api.client.json.JacksonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final String CLIENT_ID = "685124458001-ngrnm07uhkkd3n2ess5jamerea985ec2.apps.googleusercontent.com";

    @PostMapping("/google-login")
    public String googleLogin(@RequestParam String id_token, HttpSession session) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        try {
            GoogleIdToken idToken = verifier.verify(id_token);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                session.setAttribute("login", true);
                session.setAttribute("userId", payload.getSubject());
                return "ログイン成功: " + payload.getSubject();
            } else {
                return "IDトークンの検証に失敗しました。";
            }
        } catch (GeneralSecurityException | IOException e) {
            return "エラー: " + e.getMessage();
        }
    }
}

