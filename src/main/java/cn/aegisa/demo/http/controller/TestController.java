package cn.aegisa.demo.http.controller;

import cn.aegisa.demo.http.vo.Student;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIANYINGDA at 2019/1/7 22:58
 */
@Controller
@Slf4j
public class TestController {

    @RequestMapping("/test")
    @ResponseBody
    public Object doTest(HttpServletRequest request, Student student) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println(JSON.toJSONString(parameterMap));
        Map<String, Object> map = new HashMap<>();
        map.put("student", student);
        map.put("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return map;
    }
}
