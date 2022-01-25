package com.joongoprime.backend.entity;

import com.joongoprime.backend.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferRepository extends JpaRepository<Prefer, Integer> {

    @Query("select p from Prefer p where p.user_id = :uid and p.product_id = :product_id")
    List<Prefer> getPreferFromUidAndPid(@Param("uid") String uid, @Param("product_id") Integer product_id);
}
