package com.hunji.common.filter;

import com.hunji.common.constant.Constants;
import com.hunji.common.utils.http.HttpHelper;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * https://blog.csdn.net/yaomingyang/article/details/108246334
 * springboot通过拦截器获取参数有两种方式，一种通过request.getParameter获取Get方式传递的参数
 * 另外一种是通过request.getInputStream或reques.getReader获取通过POST/PUT/DELETE/PATCH传递的参数；
 * @PathVariable注解是REST风格url获取参数的方式，只能用在GET请求类型，通过getParameter获取参数
 * @RequestParam注解支持GET和POST/PUT/DELETE/PATCH方式，Get方式通过getParameter获取参数和post方式通过getInputStream或getReader获取参数
 * @RequestBody注解支持POST/PUT/DELETE/PATCH，可以通过getInputStream和getReader获取参数
 * 述通过getInputStream或getReader在拦截器中获取会导致控制器拿到的参数为空，这是因为流读取一次之后流的标志位已经发生了变化，无法多次读取参数；
 *
 * 通过装饰者模式，增强了HttpServletRequest，把流中的数据读取后暂存在byte[]中，可以实现多次读取的需求
 * @author hunji
 * @version 1.0
 * @date 2023/1/13 9:24
 */
public class RepeatedlyRequestWrapper extends HttpServletRequestWrapper
{
    private final byte[] body;

    public RepeatedlyRequestWrapper(HttpServletRequest request, ServletResponse response) throws IOException {
        super(request);
        request.setCharacterEncoding(Constants.UTF8);
        response.setCharacterEncoding(Constants.UTF8);
        // 把reques的参数读入到byte数组中，就可以多次使用
        body= HttpHelper.getBodyString(request).getBytes(Constants.UTF8);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bis.read();
            }
        };
    }
}
