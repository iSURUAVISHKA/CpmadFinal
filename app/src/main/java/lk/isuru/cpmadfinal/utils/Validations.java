package lk.isuru.cpmadfinal.utils;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Pattern;

public class Validations {

    public static final int VALID = 0;
    public static final int ERR_EMPTY = 1;
    public static final int ERR_INVALID = 2;
    public static final int ERR_EMPTY_1 = 3;
    public static final int ERR_EMPTY_2 = 4;
    public static final int ERR_NOT_MATCH = 5;

    public static int validateUsername(String username) {
        if (TextUtils.isEmpty(username))
            return ERR_EMPTY;
        else
            return VALID;
    }

    public static int validateEmail(String email) {
        if  (TextUtils.isEmpty(email))
            return ERR_EMPTY;
        else if(email.matches(String.valueOf(Patterns.EMAIL_ADDRESS)))
            return VALID;
        else
            return ERR_INVALID;
    }


    public static int validatePassword(String password, String confirmPassword){
        if(TextUtils.isEmpty(password))
            return ERR_EMPTY_1;
        else if(TextUtils.isEmpty(confirmPassword))
            return ERR_EMPTY_2;
        else if (!password.equals(confirmPassword))
            return ERR_NOT_MATCH;
        else
            return VALID;
    }
}
