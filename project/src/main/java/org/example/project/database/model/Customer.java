package org.example.project.database.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(schema = "javaschema", name = "customers")
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "email")
    private String mail;
    private String name;
    @Column(name = "surname")
    private String surName;
    private String tel;
    private String address;
    @Column(name = "additional_address")
    private String address1;
    private String comments;
    @Column(name = "promocode")
    private String promoCode;
    @Column(name = "total_price")
    private int totalPrice;
    @Transient
    private String shoppingCart;
}
