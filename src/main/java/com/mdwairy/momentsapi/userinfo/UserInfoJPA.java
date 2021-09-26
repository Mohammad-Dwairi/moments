package com.mdwairy.momentsapi.userinfo;

import com.mdwairy.momentsapi.appentity.AppEntityVisibility;
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
    public UserInfo updateCurrentCountry(String country) {
        UserInfo userInfo = userSecurity.getUserPrinciple().getUserInfo();
        userInfo.setCurrentCountry(country);
        return userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo updateCurrentCity(String city) {
        UserInfo userInfo = userSecurity.getUserPrinciple().getUserInfo();
        userInfo.setCurrentCity(city);
        return userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo updateNationality(String nationality) {
        UserInfo userInfo = userSecurity.getUserPrinciple().getUserInfo();
        userInfo.setNationality(nationality);
        return userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo updateBio(String bio) {
        UserInfo userInfo = userSecurity.getUserPrinciple().getUserInfo();
        userInfo.setBio(bio);
        return userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo updateFriendsVisibility(AppEntityVisibility visibility) {
        UserInfo userInfo = userSecurity.getUserPrinciple().getUserInfo();
        userInfo.setFriendsVisibility(visibility);
        return userInfoRepository.save(userInfo);
    }

}
