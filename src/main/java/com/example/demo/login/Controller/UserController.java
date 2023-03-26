package com.example.demo.login.Controller;

import com.example.demo.login.Bean.UserRepository;
import com.example.demo.login.Dto.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.logging.Logger;

public class UserController {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);

    private UserRepository userRepository;

    private BCryptPasswordEncoder pwEncoder;

    // 회원가입 페이지 이동
    @RequestMapping(value="/join", method= RequestMethod.GET)
    public void loginGET() {

        logger.info("회원가입 페이지 진입");

    }

    //회원가입
    @RequestMapping(value="/join", method=RequestMethod.POST)
    public String joinPOST(UserModel user) throws Exception{

        String rawPw = "";			// 인코딩 전 비밀번호
        String encodePw = "";		// 인코딩 후 비밀번호

        rawPw = user.getUserPw();			// 비밀번호 데이터 얻음
        encodePw = pwEncoder.encode(rawPw);	// 비밀번호 인코딩
        user.setUserPw(encodePw);			// 인코딩된 비밀번호 user객체에 다시 저장

        /* 회원가입 쿼리 실행 */
        userRepository.save(user);

        return "redirect:/main";

    }

    //로그인 페이지 이동
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public void joinGET() {

        logger.info("로그인 페이지 진입");

    }

    // 아이디 중복 검사
    @RequestMapping(value = "/userIdChk", method = RequestMethod.POST)
    @ResponseBody
    public String userIdChkPOST(int userId) throws Exception{

        logger.info("userIdChk() 진입");

        UserModel userModel = userRepository.findByUid(userId);

        logger.info("결과값 = " + userModel);

        if(userModel != null) {
            return "fail";	// 중복 아이디가 존재
        } else {
            return "success";	// 중복 아이디 x
        }
    } // userIdChkPOST() 종료

    /* 로그인 */
    @RequestMapping(value="login.do", method=RequestMethod.POST)
    public String loginPOST(HttpServletRequest request, UserModel user, RedirectAttributes rttr) throws Exception {
        HttpSession session = request.getSession();
        String rawPw = "";
        String encodePw = "";

        UserModel selUser = userRepository.findByUid(user.getUid());    // 제출한아이디와 일치하는 아이디 있는지

        if (selUser != null) {              // 일치하는 아이디 존재시
            rawPw = user.getUserPw();       // 사용자가 제출한 비밀번호

            session.setAttribute("user", selUser);          // session에 사용자의 정보 저장
            encodePw = selUser.getUserPw();                       // 데이터베이스에 저장한 인코딩된 비밀번호

            // 비밀번호 일치여부 판단
            if (true == pwEncoder.matches(rawPw, encodePw)) {
                selUser.setUserPw("");                            // 인코딩된 비밀번호 정보 지움
                session.setAttribute("user", selUser);      // session에 사용자의 정보 저장

                return "redirect:/main";        // 메인페이지 이동
            }
        }

        rttr.addFlashAttribute("result", 0);
        return "redirect:/member/login";    // 로그인 페이지로 이동
    }

    /* 메인페이지 로그아웃 */
    @RequestMapping(value="logout.do", method=RequestMethod.GET)
    public String logoutMainGET(HttpServletRequest request) throws Exception{

        logger.info("logoutMainGET메서드 진입");

        HttpSession session = request.getSession();
        session.invalidate();

        return "redirect:/main";
    }

}
