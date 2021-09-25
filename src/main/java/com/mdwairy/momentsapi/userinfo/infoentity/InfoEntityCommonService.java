package com.mdwairy.momentsapi.userinfo.infoentity;

import java.util.List;

public interface InfoEntityCommonService<Type extends InfoEntity, ID extends Long> {

    List<Type> findAll(String username);
    Type findById(ID id, String username);
    Type add(Type type);
    Type updateVisibility(ID id, InfoEntityVisibility isVisible);
    void deleteById(ID id);

}
