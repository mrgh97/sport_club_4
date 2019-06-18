package com.example.jpademo.controller;

import com.example.jpademo.controller.util.HeaderUtil;
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
    /**
     * POST  /videos : Create a new video.
     *
     * @param username
     * @param password
     * @return the ResponseEntity with status 201 (Created) and with body the new videoDTO, or with status 400 (Bad Request) if the video has already an ID
     */
    @ApiOperation("验证登录信息")
    @ApiImplicitParams({@ApiImplicitParam(dataType = "String",name = "username",value = "用户名",required = true),
                                            @ApiImplicitParam(dataType = "String",name = "password",value = "密码",required = true)})
    @PostMapping(value = "/login")
    public ResponseEntity<Member> verifyLogin(@RequestParam("username") String username,
                                            @RequestParam("password") String password,
                                            HttpServletRequest request) {
        log.debug("Rest to verify login");
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createAlert("Already login.", username))
                    .build();
        }
        if (mService.checkLogin(username, password)) {
            Member member = mService.findMember(username);
            session.setAttribute("user", member);
            return ResponseEntity.ok().body(member);
        }
        return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("Username or password error", username)).build();
    }

    @ApiOperation("注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Member> register(@Valid @ModelAttribute(value = "member") Member member,
                           BindingResult bindingResult,
                           @RequestParam("rePassword") String rePassword,
                           HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("Input contents has error", member.getUserName())).build();
        } else if (!member.getPassword().equals(rePassword)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("Username or password error", member.getUserName())).build();
        } else {
            member.setSign("normal");
            mService.addMember(member);
            HttpSession session = request.getSession();
            session.setAttribute("user", member);
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createEntityCreationAlert(entityName, member.getUserName().toString()))
                    .body(member);
        }
    }

    @ApiOperation("登出")
    @GetMapping("/logOut")
    public void logOut(HttpServletRequest request) {
        log.debug("Rest request to logout");
        HttpSession session = request.getSession();
        session.removeAttribute("user");
    }
//    @RequestMapping(value = "/register", method = RequestMethod.GET)
//    public String register(Model model) {
//        model.addAttribute("member", new Member());
//        return "login/register";
//    }

//    @RequestMapping(value = "/userCenter", method = RequestMethod.GET)
//    public String profile() {
//        return "login/profile";
//    }
}