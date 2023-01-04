package com.algamoney.api.config.security.token;

import com.algamoney.api.config.AlgamoneyApiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * classe responsável por intercepitar o retorno da resposta do refresh token e inseri - lo em cookie,
 * ningúem tem acesso ao refresh token que está dentro do cookie (http ou https)
 * OAuth2AccessToken - objeto que retorna o refresh token, é o objeto que desejo intercepitar
 * ResponseBodyAdvice  - interface que contém os métodos para intercepitar o retorno da resposta
 */

@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

    @Autowired
    private AlgamoneyApiProperties algamoneyApiProperties;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getMethod().getName().equals("postAccessToken");
    }


    /**
     * Método que intercepta a resposta para inserir o refresh token no cookie, este método será executado quando o método
     * supports retornar true, ou seja, quando método for igual "postAccessToken" -> return returnType.getMethod().getName().equals("postAccessToken");
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */

    @Override
    public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
                                             MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                             ServerHttpRequest request, ServerHttpResponse response) {

        HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
        HttpServletResponse resp = ((ServletServerHttpResponse) response).getServletResponse();

        String refreshToken = body.getRefreshToken().getValue();
        adicionarRefreshTokenNoCookie(refreshToken, req, resp);

        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;
        removerRefreshTokenDoBody(token);

        return body;
    }


    /**
     * remove o refresh token do corpo da requisição
     * @param token
     */
    private void removerRefreshTokenDoBody(DefaultOAuth2AccessToken token) {
        token.setRefreshToken(null);
    }

    private void adicionarRefreshTokenNoCookie(String refreshToken, HttpServletRequest req, HttpServletResponse resp) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(algamoneyApiProperties.getSeguranca().isEnableHttps());
        // url para qual o browser vai enviar o cookie contendo o refresh token
        refreshTokenCookie.setPath(req.getContextPath() + "/oauth/token");
        //tempo que o cookie expira em dias, neste caso, 30 dias
        refreshTokenCookie.setMaxAge(2592000);
        resp.addCookie(refreshTokenCookie);
    }
}

