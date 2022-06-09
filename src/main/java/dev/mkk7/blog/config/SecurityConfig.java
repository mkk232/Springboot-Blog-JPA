package dev.mkk7.blog.config;

import dev.mkk7.blog.config.auth.PrincipalDetailService;
import dev.mkk7.blog.model.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // 스프링에 Bean 등록
@EnableWebSecurity // Request가 Controller로 가기 전 Filter를 거는것이다. // 시큐리티 필터가 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Bean // IoC가 된다.
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }

    /* 시큐리티가 대신 로그인을 해주는데 password를 가로채기를 하는데
     해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
     같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음. */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // csrf 토큰 비활성화(테스트 시 걸어두는게 좋음)
                .authorizeRequests() // http Request가 들어오면
                .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**") // 이 경로는
                .permitAll() // 모두 허락한다
                .anyRequest() // 이를 제외한 다른 Request는
                .authenticated() // 인증이 되어야 한다.
            .and()
                .formLogin()
                .loginPage("/auth/loginForm") // 인증이 필요하다면 login 화면을 보여주겠다.
                .loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 로그인을 대신해줌.
                .defaultSuccessUrl("/"); // 정상적으로 요청이 완료되면 여기로 온다.
    }

}
