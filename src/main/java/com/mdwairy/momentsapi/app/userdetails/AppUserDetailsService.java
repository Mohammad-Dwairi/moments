package com.mdwairy.momentsapi.app.userdetails;

public interface AppUserDetailsService {
    String saveProfilePicture(String imageUrl);
    void deleteCurrentProfilePicture();
    void deleteProfilePicture(String pictureUrl);

    String saveCoverPicture(String pictureUrl);
    void deleteCurrentCoverPicture();
    void deleteCoverPicture(String pictureUrl);
}
