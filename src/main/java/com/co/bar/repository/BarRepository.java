package com.co.bar.repository;

import com.co.bar.entity.ArraysEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarRepository extends JpaRepository<ArraysEntity, Integer> {
}
