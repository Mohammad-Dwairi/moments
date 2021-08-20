package com.mdwairy.momentsapi.app.userdetails;

public interface AppUserDetailsService {
    String saveProfilePicture(String imageUrl);
    void deleteCurrentProfilePicture();
    void deleteProfilePicture(String pictureUrl);

    String saveCoverPicture(String pictureUrl);
    void deleteCurrentCoverPicture();
    void deleteCoverPicture(String pictureUrl);

    String saveEducation(String education);
    String editEducation(String oldEdu, String newEdu);
    void deleteEducation(String education);

    void saveWork(String work);
    void editWork(String oldWork, String newWork);
    void deleteWork(String work);

    void saveLivingPlace(String livesIn);
    void deleteLivingPlace();

    void saveCountry(String country);
    void deleteCountry();

    void saveBio(String bio);
    void deleteBio();
}
