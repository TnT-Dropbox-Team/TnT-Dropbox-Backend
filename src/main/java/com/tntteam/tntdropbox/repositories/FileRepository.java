package com.tntteam.tntdropbox.repositories;

import com.tntteam.tntdropbox.models.File;
import com.tntteam.tntdropbox.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    Page<File> findByUserIdAndNameLike(Long userId, String name, Pageable pageable);
    Page<File> findByGroupIdAndNameLike(Long groupId, String name, Pageable pageable);
    Optional<File> findByNameAndUser(String name, User user);
}