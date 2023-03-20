package com.xzw;

import com.xzw.domain.entity.Article;
import com.xzw.domain.entity.SgArticleUser;
import com.xzw.service.ArticleService;
import com.xzw.service.SgArticleDzService;
import com.xzw.service.SgArticleUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.List;

//@SpringBootTest(classes = XZWBlogApplication.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
/*@RunWith(SpringRunner.class)的作用表明Test测试类要使用注入的类，比如@Autowired注入的类，
有了@RunWith(SpringRunner.class)这些类才能实例化到spring容器中，自动注入才能生效*/
public class test1 {
    @Autowired
    private SgArticleUserService sgArticleUserService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private SgArticleDzService sgArticleDzService;

    @Test
    public void t1(){
        Article article = new Article();
        article.setId(Long.valueOf(1));
//        article.setCategoryId(Long.valueOf(2));
        List<Long> list = sgArticleUserService.listSgArticleUser(Long.valueOf(1));
        System.out.println(list);

        articleService.listSgArticleUser(article,list,1, 3);
    }
    @Test
    public void t2(){
        sgArticleUserService.delById(1L,1L);
    }
    @Test
    public void t3(){
        sgArticleUserService.showById(1L,1L);
    }
    @Test
    public void t4(){
        sgArticleUserService.addById(1L,1L);
    }
    //测试点赞
    @Test
    public void t21(){
        sgArticleDzService.getdz(1L);
    }




    @Test
    public void  test1(){
        String s1 = "xzw1";
        int length = s1.length();
        String reverse1 = " ";
        for (int i = 0; i < length; i++) {
            reverse1 = s1.charAt(i)+reverse1;//字符串中获取单个字符的字符的放法
        }
        System.out.println(reverse1);

        String s2 = "xzw2";
        char []array = s2.toCharArray();//把字符串分割成单个字符的数组
        String reverse2 = "";
        for(int i = array.length -1 ; i>=0 ; i--){//遍历数组,从后向前拼接
            reverse2 +=array[i];
        }
        System.out.println(reverse2);

        String s3 = "xzw3";
        StringBuffer sb = new StringBuffer(s3);
        String afterReverse = sb.reverse().toString();
        System.out.println(afterReverse);

        String s4 = "xzw4";
        System.out.println(reverseRecursive(s4));
    }
    public static String reverseRecursive(String s4){

        int length4 = s4.length();
        if(length4<=1){
            return s4;
        }
        String left  = s4.substring(0,length4/2);
        String right = s4.substring(length4/2 ,length4);
        String afterReverse = reverseRecursive(right)+reverseRecursive(left);//此处是递归的方法调用
        return afterReverse;
    }
    @Test
    public void test2(){
        int i = 30;
        Integer j = new Integer(30);
        Integer k = new Integer(30);
        Integer l = 30;
        Integer m = 30;
        Integer n = 300;
        Integer p = 300;
        int q = 300;
        Integer r = 300;
        Integer s = new Integer(300);
        Integer t = new Integer(300);
        String t1 ="xzw";
        String t2 ="xzw";
        System.out.println(t1.equals(t2));//true
        System.out.println(i == j);//true  自动拆箱 比较的是值
        System.out.println(j.equals(i));//true  自动拆箱 比较的是值
        //System.out.println(i.equals(k));  //编译出错，基本型不能调用equals()
        System.out.println(k == j);//false j 和 k 是两个不同的堆 比较的是栈中存放的堆地址
        System.out.println(j == l);//false 比较的是栈中存放的堆地址
        System.out.println(j.equals(k));//true 比较的是值 值相同
        System.out.println(n == p);//false，Integer值范围在-128~127时，存放在常量池中，超出范围存在堆中，因为超过了 -128--127 所以是false
        System.out.println(n.equals(p));//true  虽然不在范围-128~127内  但是比较的是值
        System.out.println(s == t);//false，Integer值范围在-128~127时，存放在常量池中，超出范围存在堆中，因为超过了 -128--127 所以是false
        System.out.println(s.equals(t));//true  虽然不在范围-128~127内  但是比较的是值
        System.out.println(l == m);//true
        System.out.println(l.equals(m));//true 比较值且在-128~127内
        System.out.println(j == 30);//true  自动拆箱 再进行强转
        System.out.println(p == 300.0);//true  自动拆箱 再进行强转
        System.out.println(p.equals(300));//true  比较值 值相同
        System.out.println(p.equals(300.0));//false 比较值 值不同
        System.out.print("1:");
        System.out.println((q == r)+","+(r.equals(q)));//true
        System.out.print("2:");
        System.out.println((q == s)+","+(s.equals(q)));//true
        System.out.print("3:");
        System.out.println((r == s)+","+(s.equals(r)));//true
        System.out.println(j == l);// false 栈不同  和范围无关

//        Integer值范围在-128在-128~127时，存放在常量池中，超出范围存在堆中，因为超过了 -128--127 所以是false
    }
  /*  private void s1(String x){

    }
    private Integer s1(String x){
        return null;
    }*/
    @Test
    public void test2_2(){
//        https://www.jianshu.com/p/9cb9c61b0986
         Integer a = new Integer(200);
         Integer b = new Integer(200);
         Integer c = 200;//Integer c = Integer.valueOf(200);
         Integer e = 200;
         int d = 200;

         System.out.println("两个new出来的对象    ==判断"+(a==b));
         System.out.println("两个new出来的对象    equal判断"+a.equals(b));
         System.out.println("new出的对象和用int赋值的Integer   ==判断"+(a==c));
         System.out.println("new出的对象和用int赋值的Integer   equal判断"+(a.equals(c)));
         System.out.println("两个用int赋值的Integer    ==判断"+(c==e));
         System.out.println("两个用int赋值的Integer    equal判断"+(c.equals(e)));
         System.out.println("基本类型和new出的对象   ==判断"+(d==a));
         System.out.println("基本类型和new出的对象   equal判断"+(a.equals(d)));
         System.out.println("基本类型和自动装箱的对象   ==判断"+(d==c));
         System.out.println("基本类型和自动装箱的对象   equal判断"+(c.equals(d)));

    }
}
