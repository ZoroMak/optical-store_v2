package org.example.project.database.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "javaschema", name = "product")
@Getter
@Setter
public class Product implements Comparable<Product>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dataArt")
    private int dataArt;
    private String name;
    private int cost;
    private String image;
    private String link;
    @Column(name = "productcol")
    private int productCol;


    @Override
    public int compareTo(Product o) {
        return Integer.compare(this.cost, o.cost);
    }
}
