package com.mdwairy.momentsapi.userinfo.picture;

import com.mdwairy.momentsapi.exception.ResourceNotFoundException;
import com.mdwairy.momentsapi.userinfo.UserInfo;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mdwairy.momentsapi.constant.AppExceptionMessage.RESOURCE_NOT_FOUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final UserService userService;

    @Override
    public List<Picture> findAllByUsername(String username) {
        String authName = SecurityContextHolder.getContext().getAuthentication().getName();
        return pictureRepository.findAllByUsername(username, authName);
    }

    @Override
    public List<Picture> findAllByType(String username, PictureType type) {
        List<Picture> pictures = pictureRepository.findAllByType(username, type);
        if (pictures == null) {
            return Collections.emptyList();
        }
        String authName = SecurityContextHolder.getContext().getAuthentication().getName();
        return pictures.stream().filter(picture -> picture.getIsVisible() || authName.equals(username)).collect(Collectors.toList());
    }

    @Override
    public Picture findById(String username, Long id) {
        Optional<Picture> pictureOptional = pictureRepository.findById(id);
        if (pictureOptional.isPresent()) {
            Picture picture = pictureOptional.get();
            String authName = SecurityContextHolder.getContext().getAuthentication().getName();
            if (picture.getIsVisible() || username.equals(authName)) {
                return picture;
            }
            throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
        }
        throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
    }

    @Override
    public Picture add(String username, Picture picture) {
        UserInfo userInfo = userService.findByUsername(username).getUserInfo();
        userInfo.addPicture(picture);
        return pictureRepository.save(picture);
    }

    @Override
    public Picture updateVisibility(Long id, boolean isVisible) {
        Optional<Picture> profilePictureOptional = pictureRepository.findById(id);
        if (profilePictureOptional.isPresent()) {
            Picture picture = profilePictureOptional.get();
            picture.setIsVisible(isVisible);
            return picture;
        }
        throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
    }

    @Override
    public void deletePicture(Long id) {
        if (pictureRepository.existsById(id)) {
            pictureRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
        }
    }

}