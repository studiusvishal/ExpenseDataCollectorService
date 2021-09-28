package com.bhavsar.vishal.service.datacollector.repository;

import com.bhavsar.vishal.service.datacollector.payload.response.auth.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface TokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

    @Transactional
    @Modifying
    @Query("delete from VerificationToken t where t.token = ?1")
    void delete(final String token);

    @Modifying
    @Query("delete from VerificationToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now);
}
