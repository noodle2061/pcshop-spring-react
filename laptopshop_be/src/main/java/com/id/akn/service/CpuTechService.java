package com.id.akn.service;

import com.id.akn.exception.BrandException;
import com.id.akn.exception.CpuTechException;
import com.id.akn.request.CpuTechDTO;

import java.util.List;

public interface CpuTechService {
    List<CpuTechDTO> getAllCpuTechs();
    CpuTechDTO getCpuTechById(Short id) throws CpuTechException;
    CpuTechDTO createCpuTech(CpuTechDTO cpuTechDTO) throws CpuTechException, BrandException;
    CpuTechDTO updateCpuTech(Short id, CpuTechDTO cpuTechDTO) throws CpuTechException, BrandException;
    void deleteCpuTech(Short id) throws CpuTechException;
}
