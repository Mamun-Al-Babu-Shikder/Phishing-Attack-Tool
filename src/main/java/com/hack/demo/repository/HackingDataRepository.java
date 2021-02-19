package com.hack.demo.repository;

import com.hack.demo.entity.HackingData;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface HackingDataRepository extends CrudRepository<HackingData, Long> {

    HackingData findHackingDataByHackerIdAndTrackId(String id, String trackId);

    List<HackingData> findAllByHackerEmailAndType(String email, String type);

    long countHackingDataByHackerEmailAndHackerIdAndType(String email, String hackerId, String type);

    @Modifying
    int deleteHackingDataByHackerEmailAndTrackIdAndType(String email, String trackId, String type);
}
