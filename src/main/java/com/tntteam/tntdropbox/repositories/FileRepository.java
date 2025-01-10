package com.tntteam.tntdropbox.repositories;

import com.tntteam.tntdropbox.models.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    Page<File> findByUserIdAndNameLikeAndTypeLike(Long userId, String name, String type, Pageable pageable);
    Page<File> findByGroupIdAndNameLikeAndTypeLike(Long groupId, String name, String type, Pageable pageable);
}