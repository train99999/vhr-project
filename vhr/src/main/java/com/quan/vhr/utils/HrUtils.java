package com.quan.vhr.utils;

import com.quan.vhr.bean.Hr;
import org.springframework.security.core.context.SecurityContextHolder;

public class HrUtils {
    public static Hr getCurrentHr(){
        // 获取当前用户对象
        return ((Hr)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
