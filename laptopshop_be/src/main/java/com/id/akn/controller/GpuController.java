package com.id.akn.controller;

import com.id.akn.exception.GpuException;
import com.id.akn.request.GpuDTO;
import com.id.akn.service.GpuService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gpus")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GpuController {
    private GpuService gpuService;

    @GetMapping
    public ResponseEntity<List<GpuDTO>> getAllGpus() {
        return ResponseEntity.ok(gpuService.getAllGpus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GpuDTO> getGpuById(@PathVariable Short id) {
        try {
            return ResponseEntity.ok(gpuService.getGpuDTOById(id));
        } catch (GpuException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/api/admin")
    public ResponseEntity<GpuDTO> createGpu(@RequestBody GpuDTO gpuDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(gpuService.createGpu(gpuDTO));
        } catch (GpuException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/api/admin/{id}")
    public ResponseEntity<GpuDTO> updateGpu(@PathVariable Short id, @RequestBody GpuDTO gpuDTO) {
        try {
            return ResponseEntity.ok(gpuService.updateGpu(id, gpuDTO));
        } catch (GpuException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/api/admin/{id}")
    public ResponseEntity<Void> deleteGpu(@PathVariable Short id) {
        try {
            gpuService.deleteGpu(id);
            return ResponseEntity.noContent().build();
        } catch (GpuException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}