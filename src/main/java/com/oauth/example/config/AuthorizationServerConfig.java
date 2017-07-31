package com.oauth.example.config;

import com.oauth.example.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.concurrent.CompletableFuture;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    AuthorizationServerEndpointsConfigurer auth;

    @Autowired
    private AuthenticationManager authenticationManager;

    private CustomUserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints.userDetailsService(userDetailsService)
                .authorizationCodeServices(authorizationCodeServices())
                .authenticationManager(this.authenticationManager)
                .tokenStore(tokenStore()).approvalStoreDisabled();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        CompletableFuture.runAsync(() -> {
            try {
                clients.jdbc(dataSource).withClient("my-trusted-client")
                        .authorizedGrantTypes("client_credentials", "password", "refresh_token")
                        .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT", "admin")
                        .scopes("read", "write", "trust")
                        .resourceIds("oauth2-resource")
                        .accessTokenValiditySeconds(5000)
                        .secret("secret");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        CompletableFuture.runAsync(() -> {
            security.checkTokenAccess("isAuthenticated()");
        });
    }

    @Bean
    public JdbcTokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    protected AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }


}
