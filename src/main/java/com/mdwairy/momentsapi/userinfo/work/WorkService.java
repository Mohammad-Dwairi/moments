package com.mdwairy.momentsapi.userinfo.work;

import com.mdwairy.momentsapi.userinfo.infoentity.InfoEntityCommonService;

import java.util.Date;

public interface WorkService extends InfoEntityCommonService<Work, Long> {

    Work updateEmployerName(long id, String employer);
    Work updateRole(long id, String role);
    Work updateIsCurrent(long id, boolean isCurrent);
    Work updateStartingDate(long id, Date startingDate);
    Work updateQuitDate(long id, Date quitDate);

}
