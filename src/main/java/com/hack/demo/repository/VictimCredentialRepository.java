package com.hack.demo.repository;

import com.hack.demo.entity.VictimCredential;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface VictimCredentialRepository extends CrudRepository<VictimCredential, Long> {

    @Query("select vc from VictimCredential vc where vc.hackerId=?1")
    Page<VictimCredential> findPageableVictimCredential(String hackerId, PageRequest pageRequest);

    @Query("select vc from VictimCredential vc where vc.hackerId=?1 and vc.type=?2")
    Page<VictimCredential> findPageableVictimCredentialWithType(String hackerId, String type, PageRequest pageRequest);

    @Modifying
    void deleteVictimCredentialByHackerIdAndId(String hackerId, long id);

    long countAllByHackerIdAndType(String hackerId, String type);
}