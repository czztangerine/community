package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.RequestParamMapMethodArgumentResolver;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.model.IAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.jar.JarOutputStream;

@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @Autowired //注入
    private AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello Spring Boot.";
    }

    @RequestMapping("/data") //注解声明路径
    @ResponseBody
    public String getData() {
        return alphaService.find();
    }

    //获得请求对象
    @RequestMapping("http")
    public void http(HttpServletRequest request, HttpServletResponse response){ //http请求对象接口
        //获取请求数据
        System.out.println(request.getMethod()); //获取请求方式
        System.out.println(request.getServletPath()); //请求路径
        Enumeration<String> enumeration = request.getHeaderNames(); //所有请求行的key，迭代器
        while (enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ": " + value);
        }
        System.out.println(request.getParameter("code"));

        //response用来对浏览器做出响应的对象,返回响应数据
        response.setContentType("text/html;charset=utf-8"); //设置返回类型，文本，图片等 (支持中文)
        //用封装的输出流输出
        try(
                PrintWriter writer = response.getWriter(); //java7句法，在这里创建，编译时自动加finally
                ) {

            writer.write("<h1>牛客网<h1>"); //不是完整网页，要先输出header（一级标题）
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //处理get请求，向服务器获取数据

    // /students?current=1&limit=20
    @RequestMapping(path = "/students", method = RequestMethod.GET) //只能处理get请求
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit){ //传入参数和参数名一致就行
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    // /student/123
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET) //球，是大括号
    @ResponseBody
    public String getStudent(@PathVariable("id") int id){
        System.out.println(id);
        return "a student";
    }

    //POST 向浏览器提交请求
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody //注意这里是响应，处理返回数据
    public String saveStudent(String name, int age){ //和表单一致的名字就可以传入
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    //向浏览器响应数据（向浏览器发送）响应html数据
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    //这里不写body就会返回网页，而不是内容
    public ModelAndView getTeacher(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "张三");
        mav.addObject("age", 30);
        mav.setViewName("/demo/view"); //会去templates目录下找，自动找网页格式，不用写后缀
        return mav;
    }
    //相对简洁一点 
    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model){ //这里返回字符串是返回model的路径
        model.addAttribute("name","xidian");
        model.addAttribute("age",70);
        return "/demo/view";
    }

    //响应JSON数据（异步请求，在本页面内，不弹出额外网页）
    // 响应JSON数据(异步请求)
    // Java对象 -> JSON字符串 -> JS对象
    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEmp() {
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 23);
        emp.put("salary", 8000.00);
        return emp;
    }

    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getEmps() {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 23);
        emp.put("salary", 8000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "李四");
        emp.put("age", 24);
        emp.put("salary", 9000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "王五");
        emp.put("age", 25);
        emp.put("salary", 10000.00);
        list.add(emp);

        return list;
    }
    //ttI:1cGLXh)#
}
