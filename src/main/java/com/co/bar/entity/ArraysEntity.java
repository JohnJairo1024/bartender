package com.co.bar.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "arrays")
@Data
public class ArraysEntity {
    @Id
    int id;
    String input_array;
}
