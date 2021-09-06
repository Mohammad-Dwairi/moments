package com.mdwairy.momentsapi.userinfo.education;


import com.mdwairy.momentsapi.userinfo.infoentity.InfoEntityCommonService;

public interface EducationService extends InfoEntityCommonService<Education, Long> {

    Education updateSchoolName(Long id, String name);
    Education updateDepartmentName(Long id, String department);
    Education updateCurrentEducation(Long id, boolean isCurrent);
    Education updateEducationType(Long id, EducationType type);

}
