package com.id.akn.controller;

import com.id.akn.exception.OsVersionException;
import com.id.akn.model.OsVersion;
import com.id.akn.service.OsVersionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/osversions")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OsVersionController {
    private OsVersionService osVersionService;

    @GetMapping
    public ResponseEntity<List<OsVersion>> getAllOsVersions() {
        return ResponseEntity.ok(osVersionService.getAllOsVersions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OsVersion> getOsVersionById(@PathVariable Short id) throws OsVersionException {
        return ResponseEntity.ok(osVersionService.getOsVersionById(id));
    }

    @PostMapping("/api/admin")
    public ResponseEntity<OsVersion> createOsVersion(@RequestBody OsVersion osVersion) throws OsVersionException {
        return ResponseEntity.status(HttpStatus.CREATED).body(osVersionService.createOsVersion(osVersion));
    }

    @PutMapping("/api/admin/{id}")
    public ResponseEntity<OsVersion> updateOsVersion(@PathVariable Short id, @RequestBody OsVersion osVersion) throws OsVersionException {
        return ResponseEntity.ok(osVersionService.updateOsVersion(id, osVersion));
    }

    @DeleteMapping("/api/admin/{id}")
    public ResponseEntity<Void> deleteOsVersion(@PathVariable Short id) {
        osVersionService.deleteOsVersion(id);
        return ResponseEntity.noContent().build();
    }
}
