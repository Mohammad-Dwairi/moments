package com.mdwairy.momentsapi.userinfo.work;

import com.mdwairy.momentsapi.exception.ResourceNotFoundException;
import com.mdwairy.momentsapi.userinfo.UserInfo;
import com.mdwairy.momentsapi.userinfo.friendship.FriendshipService;
import com.mdwairy.momentsapi.userinfo.infoentity.InfoEntityVisibility;
import com.mdwairy.momentsapi.users.User;
import com.mdwairy.momentsapi.users.UserSecurity;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.mdwairy.momentsapi.constant.AppExceptionMessage.RESOURCE_NOT_FOUND;
import static com.mdwairy.momentsapi.userinfo.infoentity.InfoEntityVisibility.*;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkServiceImpl implements WorkService {

    private final UserService userService;
    private final WorkRepository workRepository;
    private final UserSecurity userSecurity;
    private final FriendshipService friendshipService;

    @Override
    public List<Work> findAll(String username) {
        User user = userService.findByUsername(username);
        String authName = userSecurity.getUserPrinciple().getUsername();
        boolean isOwner = authName.equals(username);
        boolean isFriend = friendshipService.checkIfFriends(authName, username);

        if (isOwner) {
            return workRepository.findAllByUser(user);
        }
        if (isFriend) {
            return workRepository.findAllByUserAndVisibilityIsNot(user, PRIVATE);
        }
        return workRepository.findAllByUserAndVisibility(user, PUBLIC);
    }

    @Override
    public Work findById(Long id, String username) {

        String authUsername = userSecurity.getUserPrinciple().getUsername();
        boolean isFriend = friendshipService.checkIfFriends(username, authUsername);
        boolean isOwner = authUsername.equals(username);

        if (isOwner) {
            return getWorkForAuthenticatedUser(id);
        }

        Work work = getWorkForRequestedUser(id, username);
        InfoEntityVisibility visibility = work.getVisibility();
        if (isFriend && visibility == FRIENDS || visibility == PUBLIC) {
            return work;
        }

        throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
    }

    @Override
    public Work add(Work work) {
        String authUsername = userSecurity.getUserPrinciple().getUsername();
        UserInfo userInfo = userService.findByUsername(authUsername).getUserInfo(); // cannot use userSecurity user because the workList is lazily fetched.
        userInfo.addWork(work);
        return workRepository.save(work);
    }

    @Override
    public Work updateVisibility(Long id, InfoEntityVisibility visibility) {
        Work work = getWorkForAuthenticatedUser(id);
        work.setVisibility(visibility);
        return workRepository.save(work);
    }

    @Override
    public Work updateEmployerName(long id, String employerName) {
        Work work = getWorkForAuthenticatedUser(id);
        work.setEmployerName(employerName);
        return workRepository.save(work);
    }

    @Override
    public Work updateRole(long id, String role) {
        Work work = getWorkForAuthenticatedUser(id);
        work.setRole(role);
        return workRepository.save(work);
    }

    @Override
    public Work updateIsCurrent(long id, boolean isCurrent) {
        Work work = getWorkForAuthenticatedUser(id);
        work.setIsCurrent(isCurrent);
        return workRepository.save(work);
    }

    @Override
    public Work updateStartingDate(long id, Date startingDate) {
        Work work = getWorkForAuthenticatedUser(id);
        work.setStartingDate(startingDate);
        return workRepository.save(work);
    }

    @Override
    public Work updateQuitDate(long id, Date quitDate) {
        Work work = getWorkForAuthenticatedUser(id);
        work.setStartingDate(quitDate);
        return workRepository.save(work);
    }

    @Override
    public void deleteById(Long id) {
        Work work = getWorkForAuthenticatedUser(id);
        workRepository.delete(work);
    }

    private Work getWorkForAuthenticatedUser(Long workId) {
        User user = userSecurity.getUserPrinciple().getUser();
        Optional<Work> workOptional = workRepository.findWorkByIdAndUser(workId, user);
        return workOptional.orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
    }

    private Work getWorkForRequestedUser(Long id, String requestedUsername) {
        User user = userService.findByUsername(requestedUsername);
        Optional<Work> workOptional = workRepository.findWorkByIdAndUser(id, user);
        return workOptional.orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
    }
}
