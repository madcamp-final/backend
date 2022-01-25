package com.joongoprime.backend.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {

    @Query("select t from Trade  t where t.buyer = :buyer and t.seller = :seller and t.product_id = :product")
    List<Trade> checkTradeExists(@Param("seller")String seller, @Param("buyer")String buyer, @Param("product")Integer product);

}
