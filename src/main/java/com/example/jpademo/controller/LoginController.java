package com.example.jpademo.controller;

import com.example.jpademo.service.MemberService;
import com.example.jpademo.domain.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

@Api
@RestController
@RequestMapping("/api")
public class LoginController {

    private final Logger log = LoggerFactory.getLogger(LoginController.class);

    private MemberService mService;

    private String entityName = "Member";

    @Autowired
    public void setmService(MemberService m) {
        this.mService = m;
    }


    @RequestMapping(value = "/loadToken", method = { RequestMethod.GET })
    public void loadToken(Model model, HttpServletResponse response, @RequestParam(value = "clientId", required = false) String  clientId) {
        String token = "";
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        HttpSession session = request.getSession();
        if (session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            SecurityContext securityContext = (SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT");
            Authentication authentication = securityContext.getAuthentication();
            OAuth2AuthenticationDetails OAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) authentication.getDetails();
            token = OAuth2AuthenticationDetails.getTokenValue();
        }
        try {
            String url = "http://localhost:8081/oauth2/authorize?client_id=client&response_type=code";
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}