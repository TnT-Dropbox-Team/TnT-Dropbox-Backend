package com.tntteam.tntdropbox.repositories;

import com.tntteam.tntdropbox.models.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    Page<History> findByUserId(Long userId, Pageable pageable);
    boolean existsByIdAndUserId(Long historyId, Long userId);
}
