package control;

import org.apache.struts2.dispatcher.multipart.JakartaMultiPartRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 过滤请求，便于文件上传
 */
public class RequestParseWrapper extends JakartaMultiPartRequest {
    @Override
    public void parse(HttpServletRequest request, String arg1) throws IOException {
        String url = request.getRequestURI().toString();// 取得请求的URL
        if (url.indexOf("uploaderFile.action") > 0) {// 调用的是uploadWebApp.action方法
            // 不需要struts2的处理
        } else {
            // 需要struts2的处理,调用回父类的方法
            super.parse(request, arg1);
        }
    }
}
