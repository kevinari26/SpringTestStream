package com.kevinAri.example.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "test_stream")
public class TestStreamEntity {
    @Id
    public String uuid;
    public String stringField;
    public String stringField2;
    public BigDecimal numberField;
    public Date dateField;
}
