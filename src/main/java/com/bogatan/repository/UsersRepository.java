package com.bogatan.repository;

import com.bogatan.persistance.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface UsersRepository extends JpaRepository<Users, String> {

}