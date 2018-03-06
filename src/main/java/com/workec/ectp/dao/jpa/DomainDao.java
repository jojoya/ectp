package com.workec.ectp.dao.jpa;


import com.workec.ectp.entity.Do.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DomainDao extends JpaRepository<Domain,Integer> {

      List<Domain> findByName(String name);

}
