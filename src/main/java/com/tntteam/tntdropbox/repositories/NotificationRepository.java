package com.tntteam.tntdropbox.repositories;

import com.tntteam.tntdropbox.models.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Test, Long> {
}
