package com.eldar.services.helpers;

/**
 * @author caito Vilas
 * Date 08/2024
 * Class to validate user
 */
import java.util.regex.Pattern;

public class ValidateUserHelper {

        /**
         * Method to validate email
         * @param email
         * @return boolean
         */
        public static boolean validateEmail(String email){
            Pattern PATTERN = Pattern.compile("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            return PATTERN.matcher(email).matches();
        }

        /**
         * Method to validate password
         * @param password
         * @return boolean
         */
        public static boolean validatePassword(String password){
            final String PASS_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
            final Pattern PASS_PATTERN = Pattern.compile(PASS_REGEX);
            return PASS_PATTERN.matcher(password).matches();
        }
}
