package com.workec.ectp.repository;

import com.workec.ectp.entity.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ModuleRepositoryCustomImpl implements ModuleRepositoryCustom {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public String findModulesByName(String name) {

        return "";
    }
}
