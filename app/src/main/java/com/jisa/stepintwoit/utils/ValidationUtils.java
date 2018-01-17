package com.jisa.stepintwoit.utils;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by jisajose on 2018-01-12.
 */

public class ValidationUtils {

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
