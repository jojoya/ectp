package com.workec.ectp.dao;


import com.workec.ectp.entity.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DomainDao extends JpaRepository<Domain,Integer> {

      List<Domain> findByValue(String value);

}
