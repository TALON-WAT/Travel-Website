package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Result;
import model.User;
import org.apache.commons.beanutils.BeanUtils;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author TALON WAT
 * @date 2019-10-17 16:42
 */
@WebServlet("/registerUserServlet")
public class RegisterUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");


        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();

        //2.封装数据
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //3.调用service完成注册
        UserService service = new UserServiceImpl();
        boolean flag =  service.doRegister(user);

        System.out.println("register result: "+ flag);

        Result info = new Result();

        //4.响应结果
        if (flag) {
            //success
            info.setFlag(true);
        }else{
            //flied
            info.setFlag(false);
            info.setErrorMsg("register flied");
        }

        /**
         * 将info对象 序列化为json 并写回客户端
         */
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(info);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}