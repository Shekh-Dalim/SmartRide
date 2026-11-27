package com.rideshare.rideservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "rides")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    // Who request the ride
    @Column(nullable = false)
    private String riderId;

    // Who accepted the ride(null until  matched)
    @Column(nullable = false)
    private String driverId;

    // Latitude coordinate of the ride pickup location
    @Column(nullable = false)
    private double pickupLatitude;

    // Longitude coordinate of the ride pickup location
    @Column(nullable = false)
    private double pickupLongitude;

    // Full address of the pickup location
    @Column(nullable = false)
    private String pickupAddress;

    // Latitude of the drop-off location
    @Column(nullable = false)
    private double dropLatitude;

    // Longitude of the drop-off location
    @Column(nullable = false)
    private double dropLongitude;

    // Full address of the drop-off location
    @Column(nullable = false)
    private String dropAddress;

    // Ride status - tracks the lifecycle
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RideStatus status;

    // Fare Details
    private double estimatedFare;
    private double actualFare;

    // Timestamps
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime startedAt;
    private LocalDateTime completedAt;




}
