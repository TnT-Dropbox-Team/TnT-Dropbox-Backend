package com.tntteam.tntdropbox.repositories;

import com.tntteam.tntdropbox.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    boolean existsByIdAndUsers_Id(Long groupId, Long userId);
}
