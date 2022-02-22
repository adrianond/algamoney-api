package com.algamoney.api.http;

import com.algamoney.api.config.AlgamoneyApiProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/api/token")
@Api(tags = "Access Token")
public class RevogarTokenWS {

    @Autowired
    private AlgamoneyApiProperties algamoneyApiProperties;

    @ApiOperation("Revoke access token")
    @DeleteMapping("/revoke")
    public void revoke(HttpServletRequest req, HttpServletResponse resp
                      /* @ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                       @RequestHeader(value = "Authorization") String authorization*/) {
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(algamoneyApiProperties.getSeguranca().isEnableHttps());
        cookie.setPath(req.getContextPath() + "/oauth/token");
        cookie.setMaxAge(0);//expirar o cookie agora!

        resp.addCookie(cookie);
        resp.setStatus(HttpStatus.NO_CONTENT.value());
    }
}
