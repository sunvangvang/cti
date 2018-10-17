package com.aibyd.mvc.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.aibyd.appsys.component.AppsysAccessDeniedHandler;
import com.aibyd.appsys.component.AppsysAuthenticationEntryPoint;
import com.aibyd.appsys.component.AppsysAuthenticationFailureHandler;
import com.aibyd.appsys.component.AppsysAuthenticationSuccessHandler;
import com.aibyd.appsys.component.AppsysAuthenticationTokenFilter;
import com.aibyd.appsys.component.AppsysLogoutSuccessHandler;
import com.aibyd.appsys.component.AppsysUserDetailsService;
import com.aibyd.appsys.utils.MD5Utils;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AppsysAuthenticationEntryPoint authenticationEntryPoint;	// 未登录时返回JSON格式的数据给前端,否则为html

	@Autowired
	private AppsysAuthenticationSuccessHandler authenticationSuccessHandler;	// 登录成功返回JSON格式数据给前端,否则为html

	@Autowired
	private AppsysAuthenticationFailureHandler authenticationFailureHandler;	// 登录失败返回JSON格式数据给前端,否则为html

	@Autowired
	private AppsysLogoutSuccessHandler logoutSuccessHandler;	// 注销成功返回JSON数据给前端,否则为登录页面html

	@Autowired
	private AppsysAccessDeniedHandler accessDeniedHandler;	// 无权访问返回JSON数据给前端, 否则为403 html页面

	@Autowired
	private AppsysAuthenticationTokenFilter authenticationTokenFilter;	// JWT拦截器

	@Autowired
	private AppsysUserDetailsService userDetailsService;

	// @Autowired
	// private RabcFilterSecurityInterceptor rabcFilterSecurityInterceptor;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
				.disable()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
			.httpBasic()
				.authenticationEntryPoint(authenticationEntryPoint)
				.and()
			.authorizeRequests()
				.antMatchers("/css/**", "/fonts/**", "/layui/**", "/img/**", "/js/**",
						"/editor/**", "/login", "/auth", "/index", "/index_new", "/main")
				.permitAll()
				.anyRequest()
				.access("@appsysRbacAuthorityService.hasPermission(request, authentication)")
				// .authenticated()
				.and()
			.formLogin()
				.successHandler(authenticationSuccessHandler)
				.failureHandler(authenticationFailureHandler)
				.permitAll()
				.loginPage("/login")
				.loginProcessingUrl("/auth")
				.usernameParameter("username")
				.passwordParameter("password")
				// .defaultSuccessUrl("/index")
				.and()
			.headers()
				.frameOptions()
				.sameOrigin()
				.and()
			.logout()
				.addLogoutHandler(logoutHandler())
				.logoutSuccessUrl("/login")
				.logoutSuccessHandler(logoutSuccessHandler)
				// .invalidateHttpSession(true)
				.and()
			.rememberMe()
				.tokenValiditySeconds(300);
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
		http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
		// http.addFilterBefore(rabcFilterSecurityInterceptor, FilterSecurityInterceptor.class);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {

			@Override
			public String encode(CharSequence rawPassword) {
				return MD5Utils.encode((String) rawPassword);
			}

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return encodedPassword.equals(MD5Utils.encode((String) rawPassword));
			}
		});
		auth.eraseCredentials(false);
	}

	// @Bean
	// public BCryptPasswordEncoder passwordEncoder() {
	// return new BCryptPasswordEncoder(4);
	// }

	// @Bean
	// public LoginSuccessHandler loginSuccessHandler() {
	// return new LoginSuccessHandler();
	// }

	// @Bean
	// public LogoutSuccessHandler logoutSuccessHandler() {
	// 	return new LogoutSuccessHandler() {

	// 		private final Logger LOGGER = LoggerFactory.getLogger(LogoutSuccessHandler.class);

	// 		@Override
	// 		public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
	// 				Authentication authentication) throws IOException, ServletException {
	// 			User userDetails = (User) authentication.getPrincipal();
	// 			StringBuffer sb = new StringBuffer();
	// 			sb.append(userDetails.getUsername());
	// 			sb.append(" logout success from ");
	// 			sb.append(CommonUtils.getIpFromRequest(request));
	// 			sb.append(".");
	// 			LOGGER.info(sb.toString());
	// 		}
	// 	};
	// }

	private LogoutHandler logoutHandler() {
		return new LogoutHandler() {

			@Override
			public void logout(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) {
				authentication.setAuthenticated(false);
				try {
					response.sendRedirect("/");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		};
	}

}
