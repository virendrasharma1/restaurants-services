package com.restaurants.db.mariadb.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "restaurant_orders")
public class RestaurantOrdersEntity implements java.io.Serializable {

    private Long orderId;
    private RestaurantsEntity restaurants;
    private Double totalCost;
    private Long createdBy;
    private LocalDateTime createdTimestamp;
    private LocalDateTime orderedTime;
    private Set<OrderDetailsEntity> orderDetailses = new HashSet<OrderDetailsEntity>(0);

    public RestaurantOrdersEntity() {
    }

    public RestaurantOrdersEntity(Long orderId, RestaurantsEntity restaurants, Double totalCost, Long createdBy,
                                  LocalDateTime createdTimestamp, LocalDateTime orderedTime) {
        this.orderId = orderId;
        this.restaurants = restaurants;
        this.totalCost = totalCost;
        this.createdBy = createdBy;
        this.orderedTime = orderedTime;
        this.createdTimestamp = createdTimestamp;
    }

    public RestaurantOrdersEntity(Long orderId, RestaurantsEntity restaurants, Double totalCost, Long createdBy,
                                  LocalDateTime createdTimestamp, LocalDateTime orderedTime, Set<OrderDetailsEntity> orderDetailses) {
        this.orderId = orderId;
        this.restaurants = restaurants;
        this.totalCost = totalCost;
        this.createdBy = createdBy;
        this.createdTimestamp = createdTimestamp;
        this.orderedTime = orderedTime;
        this.orderDetailses = orderDetailses;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_id", unique = true, nullable = false)
    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    public RestaurantsEntity getRestaurants() {
        return this.restaurants;
    }

    public void setRestaurants(RestaurantsEntity restaurants) {
        this.restaurants = restaurants;
    }

    @Column(name = "total_cost", nullable = false, precision = 13, scale = 3)
    public Double getTotalCost() {
        return this.totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    @Column(name = "created_by", nullable = false)
    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "created_timestamp", nullable = false, length = 19)
    public LocalDateTime getCreatedTimestamp() {
        return this.createdTimestamp;
    }

    public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    @Column(name = "ordered_time", nullable = false, length = 19)
    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(LocalDateTime orderedTime) {
        this.orderedTime = orderedTime;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurantOrders")
    public Set<OrderDetailsEntity> getOrderDetailses() {
        return this.orderDetailses;
    }

    public void setOrderDetailses(Set<OrderDetailsEntity> orderDetailses) {
        this.orderDetailses = orderDetailses;
    }
}
