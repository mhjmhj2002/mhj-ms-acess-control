package com.mhj.ms.acess.control.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mhj.ms.acess.control.auth.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User,String> {
//    @Query(value="{'email' : ?0}")
    User findByEmail(String email);
}