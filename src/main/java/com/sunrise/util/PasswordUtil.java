package com.sunrise.util;

import org.mindrot.jbcrypt.BCrypt;


public class PasswordUtil {


    // Generate BCrypt Hash

    public static String hashPassword(String password) {


        return BCrypt.hashpw(
                password,
                BCrypt.gensalt(12)
        );

    }



    // Verify Password

    public static boolean checkPassword(
            String plainPassword,
            String hashedPassword
    ) {


        return BCrypt.checkpw(
                plainPassword,
                hashedPassword
        );

    }

}