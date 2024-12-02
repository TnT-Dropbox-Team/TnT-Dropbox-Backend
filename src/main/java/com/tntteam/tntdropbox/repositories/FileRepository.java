package com.tntteam.tntdropbox.repositories;

import com.tntteam.tntdropbox.models.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    @Query("SELECT f FROM File f WHERE f.group.id = :grId")
    List<File> findAllByGroupId(@Param("grId") long grId);
}
