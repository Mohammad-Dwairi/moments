package com.mdwairy.momentsapi.appentity;

import java.util.List;

public interface AppEntityCommonService<Type extends AppEntity, ID extends Long> {

    List<Type> findAll(String username);
    Type findById(ID id, String username);
    Type add(Type type);
    Type updateVisibility(ID id, AppEntityVisibility isVisible);
    void deleteById(ID id);

}
