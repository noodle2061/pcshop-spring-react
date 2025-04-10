package com.id.akn.controller;

import com.id.akn.exception.BrandException;
import com.id.akn.exception.CpuTechException;
import com.id.akn.request.CpuTechDTO;
import com.id.akn.service.CpuTechService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cputechs")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CpuTechController {

    private CpuTechService cpuTechService;

    @GetMapping
    public ResponseEntity<List<CpuTechDTO>> getAllCpuTechs() {
        return ResponseEntity.ok(cpuTechService.getAllCpuTechs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CpuTechDTO> getCpuTechById(@PathVariable Short id) {
        try {
            return ResponseEntity.ok(cpuTechService.getCpuTechById(id));
        } catch (CpuTechException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/api/admin")
    public ResponseEntity<CpuTechDTO> createCpuTech(@RequestBody CpuTechDTO cpuTechDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(cpuTechService.createCpuTech(cpuTechDTO));
        } catch (CpuTechException | BrandException e ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/api/admin/{id}")
    public ResponseEntity<CpuTechDTO> updateCpuTech(@PathVariable Short id, @RequestBody CpuTechDTO cpuTechDTO) {
        try {
            return ResponseEntity.ok(cpuTechService.updateCpuTech(id, cpuTechDTO));
        } catch (CpuTechException | BrandException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/api/admin/{id}")
    public ResponseEntity<Void> deleteCpuTech(@PathVariable Short id) {
        try {
            cpuTechService.deleteCpuTech(id);
            return ResponseEntity.noContent().build();
        } catch (CpuTechException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
