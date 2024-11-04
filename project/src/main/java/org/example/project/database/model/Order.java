package org.example.project.database.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "javaschema", name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private Customer customer;
    @Column(name = "product_id")
    private int productId;
    private int quantity;
    @Column(name = "total_price")
    private int totalPrice;
}
