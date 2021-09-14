package com.mdwairy.momentsapi.userinfo.work;

import com.mdwairy.momentsapi.exception.ResourceNotFoundException;
import com.mdwairy.momentsapi.userinfo.UserInfo;
import com.mdwairy.momentsapi.userinfo.infoentity.InfoEntityVisibility;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.mdwairy.momentsapi.constant.AppExceptionMessage.RESOURCE_NOT_FOUND;
import static com.mdwairy.momentsapi.constant.SecurityExceptionMessage.ACCESS_DENIED;
import static com.mdwairy.momentsapi.userinfo.infoentity.InfoEntityVisibility.PUBLIC;
import static com.mdwairy.momentsapi.users.UserRole.ROLE_ADMIN;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WorkServiceImpl implements WorkService {

    private final UserService userService;
    private final WorkRepository workRepository;

    @Override
    public List<Work> findAll(String username) {
        List<Work> workList = userService.findByUsername(username).getUserInfo().getWorkList();
        if (workList.isEmpty()) {
            return Collections.emptyList();
        }
        String authenticationName = SecurityContextHolder.getContext().getAuthentication().getName();
        return workList.stream().filter(work -> work.getVisibility() == PUBLIC || authenticationName.equals(username)).collect(toList());
    }

    @Override
    public Work findById(Long id, String username) {

        Optional<Work> workOptional = workRepository.findById(id);
        if (workOptional.isPresent()) {
            Work work = workOptional.get();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.getName().equals(username) || work.getVisibility() == PUBLIC || authentication.getAuthorities().contains(ROLE_ADMIN)) {
                return work;
            } else {
                throw new AccessDeniedException(ACCESS_DENIED);
            }
        }
        throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);

    }

    @Override
    public Work add(Work work, String username) {
        UserInfo userInfo = userService.findByUsername(username).getUserInfo();
        userInfo.addWork(work);
        return workRepository.save(work);
    }

    @Override
    public Work updateVisibility(Long id, InfoEntityVisibility visibility) {
        Optional<Work> workOptional = workRepository.findById(id);
        if (workOptional.isPresent()) {
            Work work = workOptional.get();
            work.setVisibility(visibility);
            return workRepository.save(work);
        }
        throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
    }

    @Override
    public Work updateEmployerName(long id, String employerName) {
        Optional<Work> workOptional = workRepository.findById(id);
        if (workOptional.isPresent()) {
            Work work = workOptional.get();
            work.setEmployerName(employerName);
            return workRepository.save(work);
        }
        throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
    }

    @Override
    public Work updateRole(long id, String role) {
        Optional<Work> workOptional = workRepository.findById(id);
        if (workOptional.isPresent()) {
            Work work = workOptional.get();
            work.setRole(role);
            return workRepository.save(work);
        }
        throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
    }

    @Override
    public Work updateIsCurrent(long id, boolean isCurrent) {
        Optional<Work> workOptional = workRepository.findById(id);
        if (workOptional.isPresent()) {
            Work work = workOptional.get();
            work.setIsCurrent(isCurrent);
            return workRepository.save(work);
        }
        throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
    }

    @Override
    public Work updateStartingDate(long id, Date startingDate) {
        Optional<Work> workOptional = workRepository.findById(id);
        if (workOptional.isPresent()) {
            Work work = workOptional.get();
            work.setStartingDate(startingDate);
            return workRepository.save(work);
        }
        throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
    }

    @Override
    public Work updateQuitDate(long id, Date quitDate) {
        Optional<Work> workOptional = workRepository.findById(id);
        if (workOptional.isPresent()) {
            Work work = workOptional.get();
            work.setStartingDate(quitDate);
            return workRepository.save(work);
        }
        throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Work> workOptional = workRepository.findById(id);
        if (workOptional.isPresent()) {
            workRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
        }
    }
}
