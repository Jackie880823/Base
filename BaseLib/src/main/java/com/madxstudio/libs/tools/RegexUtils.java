package com.madxstudio.libs.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liangzemian on 2017/4/6.
 */

public class RegexUtils {

    /**
     * 验证邮箱
     *
     * @param email
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkEmail(String email){
        final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher;
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
