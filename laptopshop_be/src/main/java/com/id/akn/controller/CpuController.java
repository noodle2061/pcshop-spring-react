package com.id.akn.controller;

import com.id.akn.exception.CpuException;
import com.id.akn.exception.CpuTechException;
import com.id.akn.request.CpuDTO;
import com.id.akn.service.CpuService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cpus")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CpuController {
    private CpuService cpuService;

    @GetMapping
    public ResponseEntity<List<CpuDTO>> getAllCpus() {
        return ResponseEntity.ok(cpuService.getAllCpus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CpuDTO> getCpuById(@PathVariable Short id) {
        try {
            return ResponseEntity.ok(cpuService.getCpuDTOById(id));
        } catch (CpuException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/api/admin")
    public ResponseEntity<CpuDTO> createCpu(@RequestBody CpuDTO cpuDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(cpuService.createCpu(cpuDTO));
        } catch (CpuException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (CpuTechException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/api/admin/{id}")
    public ResponseEntity<CpuDTO> updateCpu(@PathVariable Short id, @RequestBody CpuDTO cpuDTO) {
        try {
            return ResponseEntity.ok(cpuService.updateCpu(id, cpuDTO));
        } catch (CpuException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/api/admin/{id}")
    public ResponseEntity<Void> deleteCpu(@PathVariable Short id) {
        try {
            cpuService.deleteCpu(id);
            return ResponseEntity.noContent().build();
        } catch (CpuException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
