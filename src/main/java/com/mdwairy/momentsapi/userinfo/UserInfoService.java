package com.mdwairy.momentsapi.userinfo;

import com.mdwairy.momentsapi.userinfo.infoentity.InfoEntityVisibility;

public interface UserInfoService {

    UserInfo findUserInfo(String username);
    UserInfo updateCurrentCountry(String username, String country);
    UserInfo updateCurrentCity(String username, String city);
    UserInfo updateNationality(String username, String nationality);
    UserInfo updateBio(String username, String bio);
    UserInfo updateFriendsVisibility(InfoEntityVisibility visibility);
}
