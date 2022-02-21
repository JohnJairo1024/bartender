package com.co.bar.repository;

import com.co.bar.entity.ArraysEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface BarRepository extends JpaRepository<ArraysEntity, Integer> {
    ArraysEntity findArraysById(int id);
}
