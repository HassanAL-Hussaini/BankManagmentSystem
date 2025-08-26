package com.example.bankmanagmentsystem.Config;

import com.example.bankmanagmentsystem.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration//
@EnableWebSecurity//السماح بالسكيورتي و انه الويب هذا محاط بالسكيورتي
@RequiredArgsConstructor
public class ConfigSecurity {
    //now we will
    private final MyUserDetailsService myUserDetailsService;
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //هو اللي بيعطيني الصلاحيات اللي تخليني اوصل للميثودات واكسر السكيورتي على الموقع كامل
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        //set user details and set user password
        //set user details
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        //بداية الchain
        //this is Not Error
        http.csrf()
                .disable()
                .sessionManagement()
                //this session for frontend developer
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                //important point here 👇
//                .requestMatchers("/api/v1/auth/register")
                //فلسفة في التسميه هنا اعطي الصلاحيات للأشخاص
                //.primitiveALL -> for all
                //.hasAnyAuthority -> is for every One
                //.hasAuthority


                // Customer
                .requestMatchers("/api/v1/customer").hasAnyAuthority("ADMIN","CUSTOMER")//get customer by id
                .requestMatchers("/api/v1/customer/register").permitAll()//register customer
                .requestMatchers("/api/v1/customer").hasAnyAuthority("CUSTOMER")//update Customer
                .requestMatchers("/api/v1/customer/{deleteCustomerId}").hasAnyAuthority("ADMIN")//delete Customer

                //Employee
                .requestMatchers("/api/v1/employee").hasAnyAuthority("ADMIN","EMPLOYEE") // get
                .requestMatchers("/api/v1/employee").hasAuthority("ADMIN") // register
                .requestMatchers("/api/v1/employee").hasAuthority("EMPLOYEE") // update
                .requestMatchers("/api/v1/employee/{deletedEmployeeId}").hasAuthority("ADMIN") // delete


                //Account
                .requestMatchers("/api/v1/account/**").hasAnyAuthority("EMPLOYEE", "CUSTOMER")

                //Auth

                // or .hasAnyAuthority("USER","ADMIN")
                //here we will close any endpoint that we dose not selected .
                //اي شئ انا مو محدده
                .anyRequest().authenticated()
                .and()
                //this path is محجوز من سبرنق
                .logout().logoutUrl("/api/v1/aith/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }
}

