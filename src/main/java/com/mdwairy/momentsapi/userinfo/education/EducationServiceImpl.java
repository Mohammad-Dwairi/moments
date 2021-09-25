package com.mdwairy.momentsapi.userinfo.education;

import com.mdwairy.momentsapi.exception.ResourceNotFoundException;
import com.mdwairy.momentsapi.userinfo.UserInfo;
import com.mdwairy.momentsapi.userinfo.friendship.FriendshipService;
import com.mdwairy.momentsapi.userinfo.infoentity.InfoEntityVisibility;
import com.mdwairy.momentsapi.users.User;
import com.mdwairy.momentsapi.users.UserSecurity;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.mdwairy.momentsapi.constant.AppExceptionMessage.RESOURCE_NOT_FOUND;
import static com.mdwairy.momentsapi.userinfo.infoentity.InfoEntityVisibility.*;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final UserService userService;
    private final UserSecurity userSecurity;
    private final EducationRepository educationRepository;
    private final FriendshipService friendshipService;

    @Override
    public List<Education> findAll(String username) {

        String authUsername = userSecurity.getUserPrinciple().getUsername();

        if (authUsername.equals(username)) {
            User user = userSecurity.getUserPrinciple().getUser();
            return educationRepository.findAllByUser(user);
        }

        User user = userService.findByUsername(username);
        boolean isFriend = friendshipService.checkIfFriends(username, authUsername);

        if (isFriend) {
            return educationRepository.findAllByUserAndVisibilityIsNot(user, PRIVATE);
        }

        return educationRepository.findAllByUserAndVisibility(user, PUBLIC);
    }

    @Override
    public Education findById(Long id, String username) {

        String authUsername = userSecurity.getUserPrinciple().getUsername();
        boolean isFriend = friendshipService.checkIfFriends(authUsername, username);
        boolean isOwner = authUsername.equals(username);

        if (isOwner) {
            return getEducationForAuthenticatedUser(id);
        }

        Education education = getEducationForRequestedUser(username, id);
        InfoEntityVisibility visibility = education.getVisibility();

        if (isFriend && visibility == FRIENDS || visibility == PUBLIC) {
            return education;
        }

        throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
    }

    @Override
    public Education add(Education education) {
        String authUsername = userSecurity.getUserPrinciple().getUsername();
        UserInfo userInfo = userService.findByUsername(authUsername).getUserInfo();
        userInfo.addEducation(education);
        return educationRepository.save(education);
    }

    @Override
    public Education updateVisibility(Long id, InfoEntityVisibility visibility) {
        Education education = getEducationForAuthenticatedUser(id);
        education.setVisibility(visibility);
        return educationRepository.save(education);
    }

    @Override
    public void deleteById(Long id) {
        Education education = getEducationForAuthenticatedUser(id);
        educationRepository.delete(education);
    }

    @Override
    public Education updateSchoolName(Long id, String name) {
        Education education = getEducationForAuthenticatedUser(id);
        education.setSchoolName(name);
        return educationRepository.save(education);
    }

    @Override
    public Education updateDepartmentName(Long id, String department) {
        Education education = getEducationForAuthenticatedUser(id);
        education.setDepartment(department);
        return educationRepository.save(education);
    }

    @Override
    public Education updateCurrentEducation(Long id, boolean isCurrent) {
       Education education = getEducationForAuthenticatedUser(id);
       education.setIsCurrent(isCurrent);
       return educationRepository.save(education);
    }

    @Override
    public Education updateEducationType(Long id, EducationType type) {
        Education education = getEducationForAuthenticatedUser(id);
        education.setType(type);
        return educationRepository.save(education);
    }

    private Education getEducationForAuthenticatedUser(long educationId) {
        User user = userSecurity.getUserPrinciple().getUser();
        Optional<Education> educationOptional = educationRepository.findEducationByIdAndUser(educationId, user);
        return educationOptional.orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
    }

    private Education getEducationForRequestedUser(String username, long educationId) {
        User requestedUser = userService.findByUsername(username);
        Optional<Education> educationOptional = educationRepository.findEducationByIdAndUser(educationId, requestedUser);
        return educationOptional.orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
    }
}
