package com.mdwairy.momentsapi.userinfo.work;

import java.util.Date;
import java.util.List;

public interface WorkService {

    List<Work> findAll(String username);
    Work findWork(Long id, String username);

    Work addWork(Work work, String username);

    Work updateVisibility(long id, boolean isVisible);
    Work updateEmployerName(long id, String employer);
    Work updateRole(long id, String role);
    Work updateIsCurrent(long id, boolean isCurrent);
    Work updateStartingDate(long id, Date startingDate);
    Work updateQuitDate(long id, Date quitDate);

    void deleteWork(Long id, String username);

}
