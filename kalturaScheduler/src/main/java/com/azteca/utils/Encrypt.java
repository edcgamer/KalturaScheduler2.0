/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azteca.utils;

import com.azteca.persistence.entities.ComscoreSitio;
import com.azteca.service.ConsultasBDService;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Santa
 */
public class Encrypt {

    public static byte[] encrypt(String message) throws Exception {
        MessageDigest md = MessageDigest.getInstance("md5");
        byte[] digestOfPassword = md.digest("ABCDEABCDE"
                .getBytes("utf-8"));
        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
        for (int j = 0, k = 16; j < 8;) {
            keyBytes[k++] = keyBytes[j++];
        }

        SecretKey key = new SecretKeySpec(keyBytes, "DESede");
        IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        byte[] plainTextBytes = message.getBytes("utf-8");
        byte[] cipherText = cipher.doFinal(plainTextBytes);
        // String encodedCipherText = new sun.misc.BASE64Encoder()
        // .encode(cipherText);

        return cipherText;
    }

    public static String decrypt(byte[] message) throws Exception {
        MessageDigest md = MessageDigest.getInstance("md5");
        byte[] digestOfPassword = md.digest("ABCDEABCDE"
                .getBytes("utf-8"));
        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
        for (int j = 0, k = 16; j < 8;) {
            keyBytes[k++] = keyBytes[j++];
        }

        SecretKey key = new SecretKeySpec(keyBytes, "DESede");
        IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        decipher.init(Cipher.DECRYPT_MODE, key, iv);

        byte[] plainText = decipher.doFinal(message);

        return new String(plainText, "UTF-8");
    }
    
    public static void main(String[] args) {
        ApplicationContext ap = new ClassPathXmlApplicationContext("Spring-Quartz.xml");
            ConsultasBDService rep = ap.getBean("scheduler", ConsultasBDService.class);
            try {
            for (ComscoreSitio cs : rep.findAllComscoreSitio()) {
                cs.setUsuario("gherrera");
                cs.setContrasena(encrypt("valeria28dejulio"));
                rep.saveComscoreSitio(cs);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
          
    }
}
