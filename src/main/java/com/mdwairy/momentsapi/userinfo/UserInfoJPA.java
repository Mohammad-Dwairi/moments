package com.mdwairy.momentsapi.userinfo;

import com.mdwairy.momentsapi.userinfo.infoentity.InfoEntityVisibility;
import com.mdwairy.momentsapi.users.UserSecurity;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoJPA implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserService userService;
    private final UserSecurity userSecurity;

    @Override
    public UserInfo findUserInfo(String username) {
        return userService.findByUsername(username).getUserInfo();
    }

    @Override
    public UserInfo updateCurrentCountry(String username, String country) {
        UserInfo userInfo = userService.findByUsername(username).getUserInfo();
        userInfo.setCurrentCountry(country);
        return userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo updateCurrentCity(String username, String city) {
        UserInfo userInfo = userService.findByUsername(username).getUserInfo();
        userInfo.setCurrentCity(city);
        return userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo updateNationality(String username, String nationality) {
        UserInfo userInfo = userService.findByUsername(username).getUserInfo();
        userInfo.setNationality(nationality);
        return userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo updateBio(String username, String bio) {
        UserInfo userInfo = userService.findByUsername(username).getUserInfo();
        userInfo.setBio(bio);
        return userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo updateFriendsVisibility(InfoEntityVisibility visibility) {
        UserInfo userInfo = userSecurity.getUserPrinciple().getUserInfo();
        userInfo.setFriendsVisibility(visibility);
        return userInfoRepository.save(userInfo);
    }

}
