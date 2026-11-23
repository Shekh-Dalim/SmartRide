package com.rideshare.locationservice.controller;

import com.rideshare.locationservice.dto.DriverLocationRequest;
import com.rideshare.locationservice.dto.NearByDriverResponse;
import com.rideshare.locationservice.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/location")
@Slf4j
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    // 1. Driver Location Update
    // driver phone calls this every 3 second
    // The driver's mobile app sends the latest GPS coordinates
    // (latitude and longitude) to this API every 3 seconds.
    // LocationService stores/updates the driver's current location in Redis GEO.
    @PostMapping("/drivers/update")
    public ResponseEntity<String>
    updateDriverLocation(@RequestBody DriverLocationRequest driverLocationRequest) {

        locationService.updateDriverLocation(driverLocationRequest);
        return ResponseEntity.ok("Driver Location Updated");

    }

    // 2. Find Nearby Drivers
    // Matching service calls this when ride is request
    // When a rider requests a ride, the Matching Service calls this API.
    // It sends the rider's latitude, longitude, and search radius.
    // LocationService searches Redis GEO and returns nearby available drivers.
    @GetMapping("/drivers/nearby")
    public ResponseEntity<List<NearByDriverResponse>> getNearByDriver(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "5.0") double radius) {

        return ResponseEntity.ok(locationService.findNearByDrivers(latitude,longitude,radius));


    }

    // When Driver goes offline
    // 3. Remove Driver Location
    // When a driver goes offline or stops accepting rides,
    // the Driver Service calls this API.
    // LocationService removes the driver's location from Redis GEO
    // so the Matching Service does not consider that driver for new rides.
    @DeleteMapping("/drivers/{driverID}")
    public ResponseEntity<String> removeDriver(@PathVariable String driverID){
        locationService.removeDriver(driverID);
        return ResponseEntity.ok("Driver Remove Successfully");
    }
}
