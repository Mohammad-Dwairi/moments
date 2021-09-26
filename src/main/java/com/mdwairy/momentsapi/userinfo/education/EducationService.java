package com.mdwairy.momentsapi.userinfo.education;


import com.mdwairy.momentsapi.appentity.AppEntityCommonService;

public interface EducationService extends AppEntityCommonService<Education, Long> {

    Education updateSchoolName(Long id, String name);
    Education updateDepartmentName(Long id, String department);
    Education updateCurrentEducation(Long id, boolean isCurrent);
    Education updateEducationType(Long id, EducationType type);

}
