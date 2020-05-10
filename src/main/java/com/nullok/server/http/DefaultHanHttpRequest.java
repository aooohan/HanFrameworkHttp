package com.nullok.server.http;

import com.nullok.utils.DecodeUtil;
import com.nullok.utils.RequestTypeUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.util.CharsetUtil;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 默认的请求体
 * @author ：lihan
 * @description：
 * @date ：2020/5/9 16:30
 */
public class DefaultHanHttpRequest implements HanHttpRequest {
    private Class< ? extends Annotation> method;
    private HttpHeaders headers;
    private String content;
    private String uri;
    private String host;
    private Map<String, String> parameters;

    public DefaultHanHttpRequest() {
    }

    /**
     * 解析HttpRequest
     */
    public void parse(FullHttpRequest httpRequest,String host) throws UnsupportedEncodingException {
        this.method = RequestTypeUtil.stringToClass(httpRequest.method().name());
        this.headers = httpRequest.headers();
        this.host = host;
        this.content = httpRequest.content().toString(CharsetUtil.UTF_8);
        this.uri = URLDecoder.decode(httpRequest.uri(), "UTF-8");
        this.parameters = DecodeUtil.parseParameters(uri);
    }
    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getContentType() {
        return headers.get("Content-Type");
    }

    @Override
    public String getParameter(String name) {
        return parameters.get(name);
    }

    @Override
    public Set<String> getParameterNames() {
        return parameters.keySet();
    }

    @Override
    public String getRemoteHost() {
        return host;
    }

    @Override
    public Map<String, String> getHeaders() {
        HashMap<String, String> head = new HashMap<>();
        for (Map.Entry<String, String> entry : headers) {
            System.out.println(entry);
            head.put(entry.getKey(), entry.getValue());
        }
        return head;
    }

    @Override
    public Class<? extends Annotation> getRequestMethod() {
        return method;
    }
}
