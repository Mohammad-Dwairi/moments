package com.mdwairy.momentsapi.userinfo;

import com.mdwairy.momentsapi.dto.UserInfoDTO;
import com.mdwairy.momentsapi.exception.InvalidJsonKeyException;
import com.mdwairy.momentsapi.mapper.UserInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.mdwairy.momentsapi.constant.SecurityExceptionMessage.INVALID_JSON_KEY;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/{username}/info")
public class UserInfoController {

    private final UserInfoService userInfoService;


    @GetMapping
    public UserInfoDTO getUserDetails(@PathVariable String username) {
        return UserInfoMapper.INSTANCE.userInfoToUserInfoDto(userInfoService.findUserInfo(username));
    }

    @PatchMapping("/country")
    public UserInfoDTO updateCurrentCountry(@PathVariable String username, @RequestBody Map<String, String> body) {
        String KEY = "country";
        if (body.containsKey(KEY)) {
            return UserInfoMapper.INSTANCE.userInfoToUserInfoDto(userInfoService.updateCurrentCountry(username, body.get(KEY)));
        }
        throw new InvalidJsonKeyException(INVALID_JSON_KEY);
    }

    @PatchMapping("/city")
    public UserInfoDTO updateCurrentCity(@PathVariable String username, @RequestBody Map<String, String> body) {
        String KEY = "city";
        if (body.containsKey(KEY)) {
            return UserInfoMapper.INSTANCE.userInfoToUserInfoDto(userInfoService.updateCurrentCity(username, body.get(KEY)));
        }
        throw new InvalidJsonKeyException(INVALID_JSON_KEY);
    }

    @PatchMapping("/nationality")
    public UserInfoDTO updateNationality(@PathVariable String username, @RequestBody Map<String, String> body) {
        String KEY = "nationality";
        if (body.containsKey(KEY)) {
            return UserInfoMapper.INSTANCE.userInfoToUserInfoDto(userInfoService.updateNationality(username, body.get(KEY)));
        }
        throw new InvalidJsonKeyException(INVALID_JSON_KEY);
    }

    @PatchMapping("/bio")
    public UserInfoDTO updateBio(@PathVariable String username, @RequestBody Map<String, String> body) {
        String KEY = "bio";
        if (body.containsKey(KEY)) {
            return UserInfoMapper.INSTANCE.userInfoToUserInfoDto(userInfoService.updateBio(username, body.get(KEY)));
        }
        throw new InvalidJsonKeyException(INVALID_JSON_KEY);
    }

}
