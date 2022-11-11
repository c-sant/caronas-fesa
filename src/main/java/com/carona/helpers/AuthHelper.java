package com.carona.helpers;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthHelper {
    public static String encryptPassword(String password) throws NoSuchAlgorithmException,
    UnsupportedEncodingException {

        String passwordEncrypted = "";

        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algorithm.digest(password.getBytes("UTF-8"));

        for(byte letter : messageDigest){
            passwordEncrypted += String.format("%02X", 0xFF & letter);
        }
        
        return passwordEncrypted;
    }
}
