package com.mycompany.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Toys.
 */
@Entity
@Table(name = "toys")
public class Toys implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "category")
    private String category;

    @Column(name = "avg_price")
    private Double avg_price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Toys name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public Toys price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public Toys category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getAvg_price() {
        return avg_price;
    }

    public Toys avg_price(Double avg_price) {
        this.avg_price = avg_price;
        return this;
    }

    public void setAvg_price(Double avg_price) {
        this.avg_price = avg_price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Toys toys = (Toys) o;
        if(toys.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, toys.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Toys{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", price='" + price + "'" +
            ", category='" + category + "'" +
            ", avg_price='" + avg_price + "'" +
            '}';
    }
}
