package com.mdwairy.momentsapi.userinfo;

import com.mdwairy.momentsapi.appentity.AppEntityVisibility;
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
    public UserInfoDTO updateCurrentCountry(@RequestBody Map<String, String> body) {
        String KEY = "country";
        if (body.containsKey(KEY)) {
            return UserInfoMapper.INSTANCE.userInfoToUserInfoDto(userInfoService.updateCurrentCountry(body.get(KEY)));
        }
        throw new InvalidJsonKeyException(INVALID_JSON_KEY);
    }

    @PatchMapping("/city")
    public UserInfoDTO updateCurrentCity(@RequestBody Map<String, String> body) {
        String KEY = "city";
        if (body.containsKey(KEY)) {
            return UserInfoMapper.INSTANCE.userInfoToUserInfoDto(userInfoService.updateCurrentCity(body.get(KEY)));
        }
        throw new InvalidJsonKeyException(INVALID_JSON_KEY);
    }

    @PatchMapping("/nationality")
    public UserInfoDTO updateNationality(@RequestBody Map<String, String> body) {
        String KEY = "nationality";
        if (body.containsKey(KEY)) {
            return UserInfoMapper.INSTANCE.userInfoToUserInfoDto(userInfoService.updateNationality(body.get(KEY)));
        }
        throw new InvalidJsonKeyException(INVALID_JSON_KEY);
    }

    @PatchMapping("/bio")
    public UserInfoDTO updateBio(@RequestBody Map<String, String> body) {
        String KEY = "bio";
        if (body.containsKey(KEY)) {
            return UserInfoMapper.INSTANCE.userInfoToUserInfoDto(userInfoService.updateBio(body.get(KEY)));
        }
        throw new InvalidJsonKeyException(INVALID_JSON_KEY);
    }

    @PatchMapping("/friends_visibility")
    public UserInfoDTO updateFriendsVisibility(@RequestBody Map<String, AppEntityVisibility> body) {
        String KEY = "visibility";
        if (body.containsKey(KEY)) {
            return UserInfoMapper.INSTANCE.userInfoToUserInfoDto(userInfoService.updateFriendsVisibility(body.get("visibility")));
        }
        throw new InvalidJsonKeyException(INVALID_JSON_KEY);
    }

}
