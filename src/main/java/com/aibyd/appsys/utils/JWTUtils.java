package com.aibyd.appsys.utils;

import java.util.Date;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.springframework.util.ResourceUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Java Web Token
 */


public class JWTUtils {

    // 寻找证书文件
    // private static InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("aibyd.jks");

    private static PrivateKey privateKey = null;

    private static PublicKey publicKey = null;

    private static final String keyStorePass = "1qazxcvbgt5@#$";

    static {
        FileInputStream fin = null;
        try {
            File file = ResourceUtils.getFile("classpath:aibyd.jks");
            fin = new FileInputStream(file);
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(fin, keyStorePass.toCharArray());
            privateKey = (PrivateKey) keyStore.getKey("aibyd", keyStorePass.toCharArray());
            publicKey = keyStore.getCertificate("aibyd").getPublicKey();
        }catch (Exception e) {
            e.printStackTrace();;
        }finally {
            try {
                if(fin != null) {
                    fin.close();
                }
            }catch (Exception es) {
                es.printStackTrace();
            }
        }

    }

    // static {
    //     try {
    //         KeyStore keyStore = KeyStore.getInstance("JKS");
    //         keyStore.load(in, keyStorePass.toCharArray());
    //         privateKey = (PrivateKey) keyStore.getKey("jwt", keyStorePass.toCharArray());
    //         publicKey = keyStore.getCertificate("jwt").getPublicKey();
    //     }catch (Exception e) {
    //         e.printStackTrace();;
    //     }
    // }

    public static String generateToken(String subject, int expirationSeconds, String salt) {
        return Jwts.builder()
            .setClaims(null)
            .setSubject(subject)
            .setExpiration(new Date(System.currentTimeMillis() + expirationSeconds * 1000))
            // .signWith(SignatureAlgorithm.HS512, salt) // 不使用公钥私钥
            .signWith(SignatureAlgorithm.RS512, privateKey)
            .compact();
    }

    public static String parseToken(String token, String salt) {
        String subject = null;
        try {
            Claims claims = Jwts.parser()
                // .setSigningKey(salt) // 不使用公钥私钥
                .setSigningKey(publicKey)
                .parseClaimsJws(token)
                .getBody();
            subject = claims.getSubject();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return subject;
    }

}