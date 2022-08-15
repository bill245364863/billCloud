package com.bill.test;

import cn.hutool.jwt.JWT;
import com.bill.cloud.BillServiceMain9026;
import com.bill.cloud.mapper.SysMenuMapper;
import com.bill.cloud.mapper.SysUserMapper;
import com.bill.entity.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author huangxiaotao
 * @Date 2022/5/26 21:36
 **/
@SpringBootTest(classes = BillServiceMain9026.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MybatisTest {
    @Resource
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Test
    public void testPasswordEncoder(){
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
        boolean matches = passwordEncoder.matches("123456","$2a$10$RzQMXSNfTjryYVySyFazmOkyiAvcT2.xulLvYJqTaOmDA8ZnN9fTS");
        System.out.println(matches);
    }

    @Test
    public void jwt(){
        JWT of = JWT.of("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NTcwMjkzODAsImV4cCI6MTY1NzAyOTk4MCwidG9rZW4iOiJ7XCJhY2NvdW50Tm9uRXhwaXJlZFwiOnRydWUsXCJhY2NvdW50Tm9uTG9ja2VkXCI6dHJ1ZSxcImF1dGhvcml0eXNcIjpbXCJ0ZXN0XCIsXCJhZG1pblwiXSxcImNyZWRlbnRpYWxzTm9uRXhwaXJlZFwiOnRydWUsXCJlbmFibGVkXCI6dHJ1ZSxcInBhc3N3b3JkXCI6XCIkMmEkMTAkUnpRTVhTTmZUanJ5WVZ5U3lGYXptT2t5aUF2Y1QyLnh1bEx2WUpxVGFPbURBOFpuTjlmVFNcIixcInN5c1VzZXJcIjp7XCJpZFwiOjEsXCJuaWNrTmFtZVwiOlwiaHh0XCIsXCJwYXNzd2RcIjpcIiQyYSQxMCRSelFNWFNOZlRqcnlZVnlTeUZhem1Pa3lpQXZjVDIueHVsTHZZSnFUYU9tREE4Wm5OOWZUU1wiLFwic2V4XCI6XCIxXCIsXCJ1c2VyTmFtZVwiOlwiYmlsbFwifSxcInVzZXJuYW1lXCI6XCJiaWxsXCJ9In0.viRiOfRaI9gReSg1fki5TQ9Beh9SNiL09T3Hk3e5-uw");
        Object data = of.getPayload("token");
        System.out.println(data);
    }
    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<SysUser> userList = sysUserMapper.selectList(null);
//        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }
    @Test
    public void testMapper(){
        List<String> strings = sysMenuMapper.selectPermsByUserId(Long.valueOf(1));
        System.out.println(strings);
    }

}
