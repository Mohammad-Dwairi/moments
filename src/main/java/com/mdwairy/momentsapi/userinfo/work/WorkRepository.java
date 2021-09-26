package com.mdwairy.momentsapi.userinfo.work;

import com.mdwairy.momentsapi.appentity.AppEntityVisibility;
import com.mdwairy.momentsapi.users.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface WorkRepository extends CrudRepository<Work, Long> {
    Optional<Work> findWorkByIdAndUser(Long id, User user);
    List<Work> findAllByUser(User user);
    List<Work> findAllByUserAndVisibilityIsNot(User user, AppEntityVisibility visibility);
    List<Work> findAllByUserAndVisibility(User user, AppEntityVisibility visibility);
}
