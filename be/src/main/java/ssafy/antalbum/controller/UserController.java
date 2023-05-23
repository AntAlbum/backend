package ssafy.antalbum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ssafy.antalbum.dto.AddUserRequest;
import ssafy.antalbum.entity.user.User;
import ssafy.antalbum.service.UserService;


/**
 * 일반 회원가입 기능을 위한 컨트롤러입니다. 추후에 일반 로그인이 구현될 시 사용됩니다.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("user/{userid}")
    public ResponseEntity<User> getUser(@PathVariable("userid") Long userId) {
        return new ResponseEntity<>(userService.findById(userId), HttpStatus.OK);
    }

    @PostMapping("/user")
    public String signup(AddUserRequest request) {
        userService.save(request); // 회원 가입 메서드 호출

        // todo: redirect url setting
        return "redirect:";
    }

}
