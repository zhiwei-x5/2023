package com.xzw.utils;

import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {

    private BeanCopyUtils() {
    }
    /**定义泛型方法，其格式是：修饰符 <类型参数列表> 返回类型 方法名(形参列表) { 方法体 }
     *以下：<类型参数列表> 返回类型都为V    */
    /**Class<V> clazz需要传入class作为参数，然后通过反射来实例化*/
    public static <V> V copyBean(Object source,Class<V> clazz) {
        //创建目标对象
        V result = null;
        try {
            //通过反射机制，无论传入哪个类都能实例
            result = clazz.newInstance();
            //实现属性copy
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return result;
    }
    public static <O,V> List<V> copyBeanList(List<O> list,Class<V> clazz){
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}
