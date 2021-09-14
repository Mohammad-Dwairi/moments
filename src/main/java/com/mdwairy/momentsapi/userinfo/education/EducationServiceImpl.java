package com.mdwairy.momentsapi.userinfo.education;

import com.mdwairy.momentsapi.exception.ResourceNotFoundException;
import com.mdwairy.momentsapi.userinfo.UserInfo;
import com.mdwairy.momentsapi.userinfo.infoentity.InfoEntityVisibility;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mdwairy.momentsapi.constant.AppExceptionMessage.RESOURCE_NOT_FOUND;
import static com.mdwairy.momentsapi.constant.SecurityExceptionMessage.ACCESS_DENIED;
import static com.mdwairy.momentsapi.userinfo.infoentity.InfoEntityVisibility.PUBLIC;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final UserService userService;
    private final EducationRepository educationRepository;

    @Override
    public List<Education> findAll(String username) {
        List<Education> educationList = userService.findByUsername(username).getUserInfo().getEducationList();
        String authenticationName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (educationList == null) {
            return Collections.emptyList();
        }
        return educationList.stream().filter(e -> e.getVisibility() == PUBLIC || username.equals(authenticationName)).collect(Collectors.toList());
    }

    @Override
    public Education findById(Long id, String username) {
        Optional<Education> educationOptional = educationRepository.findById(id);
        if (educationOptional.isPresent()) {
            Education education = educationOptional.get();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (education.getVisibility() == PUBLIC || authentication.getName().equals(username)) {
                return education;
            }
            else {
                throw new AccessDeniedException(ACCESS_DENIED);
            }
        }
        throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
    }

    @Override
    public Education add(Education education, String username) {
        UserInfo userInfo = userService.findByUsername(username).getUserInfo();
        userInfo.addEducation(education);
        return educationRepository.save(education);
    }

    @Override
    public Education updateVisibility(Long id, InfoEntityVisibility visibility) {
        Optional<Education> educationOptional = educationRepository.findById(id);
        if (educationOptional.isPresent()) {
            Education education = educationOptional.get();
            education.setVisibility(visibility);
            return educationRepository.save(education);
        }
        throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Education> educationOptional = educationRepository.findById(id);
        if (educationOptional.isPresent()) {
            educationRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
        }
    }

    @Override
    public Education updateSchoolName(Long id, String name) {
        Optional<Education> educationOptional = educationRepository.findById(id);
        if (educationOptional.isPresent()) {
            Education education = educationOptional.get();
            education.setSchoolName(name);
            return educationRepository.save(education);
        }
        throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
    }

    @Override
    public Education updateDepartmentName(Long id, String department) {
        Optional<Education> educationOptional = educationRepository.findById(id);
        if (educationOptional.isPresent()) {
            Education education = educationOptional.get();
            education.setDepartment(department);
            return educationRepository.save(education);
        }
        throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
    }

    @Override
    public Education updateCurrentEducation(Long id, boolean isCurrent) {
        Optional<Education> educationOptional = educationRepository.findById(id);
        if (educationOptional.isPresent()) {
            Education education = educationOptional.get();
            education.setIsCurrent(isCurrent);
            return educationRepository.save(education);
        }
        throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
    }

    @Override
    public Education updateEducationType(Long id, EducationType type) {
        Optional<Education> educationOptional = educationRepository.findById(id);
        if (educationOptional.isPresent()) {
            Education education = educationOptional.get();
            education.setType(type);
            return educationRepository.save(education);
        }
        throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
    }
}
