package com.id.akn.service;

import com.id.akn.exception.GpuException;
import com.id.akn.model.Gpu;
import com.id.akn.request.GpuDTO;

import java.util.List;

public interface GpuService {
    List<GpuDTO> getAllGpus();
    GpuDTO getGpuDTOById(Short id) throws GpuException;
    Gpu getGpuById(Short id) throws GpuException;
    GpuDTO createGpu(GpuDTO gpuDTO) throws GpuException;
    GpuDTO updateGpu(Short id, GpuDTO gpuDTO) throws GpuException;
    void deleteGpu(Short id) throws GpuException;
    GpuDTO convertToDTO(Gpu gpu);
}
