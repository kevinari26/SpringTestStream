package com.kevinAri.example.model.repository;

import com.kevinAri.example.model.entity.TestStreamEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface TestStreamRepo extends JpaRepository<TestStreamEntity, String> {
    List<TestStreamEntity> findByNumberFieldIsGreaterThan(BigDecimal numberFieldGreaterThan, Sort sort);
}
