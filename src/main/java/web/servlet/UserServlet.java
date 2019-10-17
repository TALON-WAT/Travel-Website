package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import exception.UserNameExistsException;
import exception.UserNameNotNullException;
import model.Result;
import model.User;
import org.apache.commons.beanutils.BeanUtils;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author TALON WAT
 * @date 2019-10-17 20:42
 */
@WebServlet("/user")
public class UserServlet extends HttpServlet {
    /**
     * 实例用户业务类
     */
    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获得请求的标识符
        String action = request.getParameter("action");
        //处理注册请求
        if("register".equals(action)){
            register(request,response);
        }
    }

    private void register(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        Result resultInfo = null;

        String userCheckCode = request.getParameter("check");
        //2.2服务器生成的验证码
        String serverCheckCode= (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        //2.3校验
        if(serverCheckCode!=null && !serverCheckCode.equalsIgnoreCase(userCheckCode)){
            resultInfo = new Result(false,"验证码错误");
        }else {
            try {
                //3.获取数据并封装数据到User对象
                User user = new User();
                BeanUtils.populate(user, request.getParameterMap());
                //4.调用业务逻辑层注册用户
                boolean flag = userService.register(user, request.getContextPath());
                //5.获取注册结果
                if (flag) {
                    resultInfo = new Result(true);
                }
            } catch (UserNameNotNullException e) {
                resultInfo = new Result(false, e.getMessage());
            } catch (UserNameExistsException e) {
                resultInfo = new Result(false, e.getMessage());
            } catch (Exception e) {
                //打印异常信息
                e.printStackTrace();
                //用户处理不了的异常，要去到友好页面
                throw new RuntimeException(e);
            }
        }
        //6.将resultInfo转换为json数据返回给客户端
        String jsonData = new ObjectMapper().writeValueAsString(resultInfo);
        //7.输出给浏览器
        response.getWriter().write(jsonData);
    }
}
