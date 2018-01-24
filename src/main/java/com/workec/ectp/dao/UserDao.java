package com.workec.ectp.dao;


import com.workec.ectp.entity.DO.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User,Integer> {

      List<User> findByName(String name);

      List<User> findByAccount(String account);



}
