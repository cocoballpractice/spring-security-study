package study.student;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;

@Component
public class StudentManager implements AuthenticationProvider, InitializingBean {

    /**
     * Student 객체에게 인증을 제공할 인증 제공자
     * UsernamePasswordAuthenticationFilter에서 UsernamePasswordAuthenticationToken을 처리가 가능한
     * AuthenticationProvider 구현체 측으로 인증 요청을 위임하게 되며
     * 위임을 받은 AuthenticationProvider의 구현체는 아래에 구현해둔 메서드로 인증을 진행하게 됨
     */

    private HashMap<String, Student> studentDB = new HashMap<>(); // 원래는 DB에서 가져와야 하나 임시로 구현


    /**
     *
     * UsernamePasswordAuthenticationFilter에서 return this.getAuthenticationManager().authenticate()를 통해 토큰 인증 요청
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        // DB 상에 저장된 허가된 Student에 대해 토큰 생성
        if(studentDB.containsKey(token.getName())) {
            Student student = studentDB.get(token.getName());
            return StudentAuthenticationToken.builder()
                    .principal(student)
                    .credentials("null")
                    .details(student.getUsername())
                    .authenticated(true)
                    .build();
        }
        return null; // 그 외의 경우엔 null로 처리 (false로 할 시 어쨌든 인증 작업은 했다는 것이 되므로 문제가 됨)
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 해당 Provider는 UsernamePasswordAuthenticationToken을 통해서 인증하겠다고 선언
        return authentication == UsernamePasswordAuthenticationToken.class;
    }

    // InitializingBean을 통해 컴포넌트 스캔 단계에서 작동
    @Override
    public void afterPropertiesSet() throws Exception {
        Set.of(
                new Student("hong", "홍길동", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT"))),
                new Student("kang", "강호동", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT"))),
                new Student("kim", "김기동", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")))
        ).forEach(s ->
                studentDB.put(s.getId(), s)
        );
    }
}
