package com.mdwairy.momentsapi.userinfo.education;

import com.mdwairy.momentsapi.appentity.AppEntityVisibility;
import com.mdwairy.momentsapi.users.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EducationRepository extends CrudRepository<Education, Long> {
    List<Education> findAllByUser(User user);
    List<Education> findAllByUserAndVisibilityIsNot(User user, AppEntityVisibility visibility);
    List<Education> findAllByUserAndVisibility(User user, AppEntityVisibility visibility);
    Optional<Education> findEducationByIdAndUser(Long id, User user);
}
