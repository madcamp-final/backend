package com.joongoprime.backend.entity;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Integer>{
    @Query("select i.type from Idea i where i.category = :category")
    List<String> getTypeFromCategory(@Param("category") String category);
}
