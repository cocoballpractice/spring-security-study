package study.config;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Component
public class CustomAuthDetails implements AuthenticationDetailsSource<HttpServletRequest, RequestInfo> {

    /**
     * Authentication 객체에 들어갈 Details에 들어갈 정보를 직접 생성하는 AuthenticationDetailsSource를 구현
     */

    @Override
    public RequestInfo buildDetails(HttpServletRequest request) {
        return RequestInfo.builder()
                .remoteIp(request.getRemoteAddr())
                .sessiondId(request.getSession().getId())
                .loginTime(LocalDateTime.now())
                .build();
    }

}
