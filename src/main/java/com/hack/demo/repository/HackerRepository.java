package com.hack.demo.repository;

import com.hack.demo.entity.Hacker;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HackerRepository extends CrudRepository<Hacker, String> {

    Hacker findHackerByEmail(String email);

    @Query("select h.id from Hacker h where h.email=?1")
    String findHackerIdByEmail(String email);

}
