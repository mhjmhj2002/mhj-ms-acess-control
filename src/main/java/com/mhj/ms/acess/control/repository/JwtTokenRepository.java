package com.mhj.ms.acess.control.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mhj.ms.acess.control.auth.entity.JwtToken;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtToken, String> {
}
