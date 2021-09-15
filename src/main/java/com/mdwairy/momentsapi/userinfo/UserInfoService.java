package com.mdwairy.momentsapi.userinfo;

import com.mdwairy.momentsapi.userinfo.infoentity.InfoEntityVisibility;

public interface UserInfoService {

    UserInfo findUserInfo(String username);
    UserInfo updateCurrentCountry(String country);
    UserInfo updateCurrentCity(String city);
    UserInfo updateNationality(String nationality);
    UserInfo updateBio(String bio);
    UserInfo updateFriendsVisibility(InfoEntityVisibility visibility);
}
