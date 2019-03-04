package com.restaurants.utils;

import com.restaurants.exceptions.AppErrorException;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtils {

    private static Logger logger = Logger.getLogger(PasswordUtils.class);
    private static final String HASHING_KEY = "PBKDF2WithHmacSHA1";
    private static final String NO_SUCH_ALGORITHM = "NoSuchAlgorithmException";

    public boolean compare(String originalPassword, String storedPassword) throws AppErrorException {
        String[] parts = storedPassword.split(":");
        byte[] salt = fromHex(parts[0]);
        int iterations = Integer.parseInt(parts[1]);
        byte[] hash = fromHex(parts[2]);
        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);

        SecretKeyFactory skf;
        try {
            skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException var13) {
            logger.error(var13.getMessage());
            throw new AppErrorException("NoSuchAlgorithmException", "APPLICATION_ERROR");
        }

        Object var9 = null;

        byte[] testHash;
        try {
            testHash = skf.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException var12) {
            logger.error(var12.getMessage());
            throw new AppErrorException("InvalidKeySpecException", "APPLICATION_ERROR");
        }

        int diff = hash.length ^ testHash.length;

        for(int i = 0; i < hash.length && i < testHash.length; ++i) {
            diff |= hash[i] ^ testHash[i];
        }

        return diff == 0;
    }

    public String generateHash(String password) throws AppErrorException {
        int iterations = 999;
        char[] chars = password.toCharArray();
        Object var4 = null;

        byte[] salt;
        try {
            salt = getSalt().getBytes();
        } catch (NoSuchAlgorithmException var11) {
            logger.error(var11.getMessage());
            throw new AppErrorException("NoSuchAlgorithmException", "APPLICATION_ERROR");
        }

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 512);
        SecretKeyFactory skf = null;

        try {
            skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException var10) {
            logger.error(var10.getMessage());
            throw new AppErrorException("NoSuchAlgorithmException", "APPLICATION_ERROR");
        }

        Object var7 = null;

        byte[] hash;
        try {
            hash = skf.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException var9) {
            logger.error(var9.getMessage());
            throw new AppErrorException("InvalidKeySpecException", "APPLICATION_ERROR");
        }

        password = toHex(salt) + ":" + iterations + ":" + toHex(hash);
        if (password.length() > 125) {
            password = password.substring(0, 125);
        }

        return password;
    }

    private static String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }

    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = array.length * 2 - hex.length();
        return paddingLength > 0 ? String.format("%0" + paddingLength + "d", Integer.valueOf(0)) + hex : hex;
    }

    private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];

        for(int i = 0; i < bytes.length; ++i) {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }

        return bytes;
    }
}
