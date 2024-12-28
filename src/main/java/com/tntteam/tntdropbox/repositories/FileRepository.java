package com.tntteam.tntdropbox.repositories;

import com.tntteam.tntdropbox.models.File;
import com.tntteam.tntdropbox.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    Page<File> findByUserIdAndNameLikeAndTypeLike(Long userId, String name, String type, Pageable pageable);
}