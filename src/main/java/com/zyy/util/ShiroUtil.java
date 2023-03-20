package com.zyy.util;

import com.zyy.shiro.AccountProfile;
import lombok.Data;
import org.apache.shiro.SecurityUtils;
@Data
public class ShiroUtil {
    public static AccountProfile getProfile(){
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
