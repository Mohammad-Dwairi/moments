package com.mdwairy.momentsapi.userinfo;

import com.mdwairy.momentsapi.appentity.AppEntityVisibility;

public interface UserInfoService {

    UserInfo findUserInfo(String username);
    UserInfo updateCurrentCountry(String country);
    UserInfo updateCurrentCity(String city);
    UserInfo updateNationality(String nationality);
    UserInfo updateBio(String bio);
    UserInfo updateFriendsVisibility(AppEntityVisibility visibility);
}
