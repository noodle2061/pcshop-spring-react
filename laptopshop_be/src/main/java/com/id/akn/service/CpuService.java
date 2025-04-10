package com.id.akn.service;

import com.id.akn.exception.CpuException;
import com.id.akn.exception.CpuTechException;
import com.id.akn.model.Cpu;
import com.id.akn.request.CpuDTO;

import java.util.List;

public interface CpuService {
    List<CpuDTO> getAllCpus();
    CpuDTO getCpuDTOById(Short id) throws CpuException;
    Cpu getCpuById(Short id) throws CpuException;
    CpuDTO createCpu(CpuDTO cpuDTO) throws CpuException, CpuTechException;
    CpuDTO updateCpu(Short id, CpuDTO cpuDTO) throws CpuException;
    void deleteCpu(Short id) throws CpuException;
    CpuDTO convertToDTO(Cpu cpu);
}
