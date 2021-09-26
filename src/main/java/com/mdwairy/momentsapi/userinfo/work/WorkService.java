package com.mdwairy.momentsapi.userinfo.work;

import com.mdwairy.momentsapi.appentity.AppEntityCommonService;

import java.util.Date;

public interface WorkService extends AppEntityCommonService<Work, Long> {

    Work updateEmployerName(long id, String employer);
    Work updateRole(long id, String role);
    Work updateIsCurrent(long id, boolean isCurrent);
    Work updateStartingDate(long id, Date startingDate);
    Work updateQuitDate(long id, Date quitDate);

}
