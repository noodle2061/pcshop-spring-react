package com.id.akn.serviceimpl;

import com.id.akn.exception.CpuException;
import com.id.akn.exception.GpuException;
import com.id.akn.model.Brand;
import com.id.akn.model.Cpu;
import com.id.akn.model.Gpu;
import com.id.akn.repository.BrandRepository;
import com.id.akn.repository.GpuRepository;
import com.id.akn.request.GpuDTO;
import com.id.akn.service.GpuService;
import com.id.akn.util.NormalizeString;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GpuServiceImpl implements GpuService {

    private final GpuRepository gpuRepository;
    private final BrandRepository brandRepository;

    @Override
    public List<GpuDTO> getAllGpus() {
        return gpuRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GpuDTO getGpuDTOById(Short id) throws GpuException {
        Gpu gpu = gpuRepository.findById(id)
                .orElseThrow(() -> new GpuException("Gpu not found"));
        return convertToDTO(gpu);
    }

    @Override
    public Gpu getGpuById(Short id) throws GpuException {
        return gpuRepository.findById(id)
                .orElseThrow(() -> new GpuException("Gpu not found"));
    }

    @Override
    public GpuDTO createGpu(GpuDTO gpuDTO) throws GpuException {
        Brand brand = brandRepository.findById(gpuDTO.getBrandId())
                .orElseThrow(() -> new GpuException("Brand not found"));
        Gpu gpu = new Gpu();
        gpu.setBrand(brand);
        String normalizedModel = NormalizeString.normalize(gpuDTO.getModel());
        if (gpuRepository.findByNameNormalize(normalizedModel) != null) {
            throw new GpuException("GPU technology with the name '" + gpuDTO.getModel() + "' already exists.");
        }
        gpu.setModel(gpuDTO.getModel());
        gpu.setMemory(gpuDTO.getMemory());
        gpu.setTops(gpuDTO.getTops());
        gpu.setType(gpuDTO.getType());
        return convertToDTO(gpuRepository.save(gpu));
    }

    @Override
    public GpuDTO updateGpu(Short id, GpuDTO gpuDTO) throws GpuException {
        Gpu gpu = gpuRepository.findById(id)
                .orElseThrow(() -> new GpuException("Gpu not found"));
        Brand brand = brandRepository.findById(gpuDTO.getBrandId())
                .orElseThrow(() -> new GpuException("Brand not found"));
        gpu.setBrand(brand);
        String normalizedModel = NormalizeString.normalize(gpuDTO.getModel());
        Gpu existingModel = gpuRepository.findByNameNormalize(normalizedModel);
        if (existingModel != null && !existingModel.getId().equals(id)) {
            throw new GpuException("GPU with the model '" + gpuDTO.getModel() + "' already exists.");
        }
        gpu.setModel(gpuDTO.getModel());
        gpu.setMemory(gpuDTO.getMemory());
        gpu.setTops(gpuDTO.getTops());
        gpu.setType(gpuDTO.getType());
        return convertToDTO(gpuRepository.save(gpu));
    }

    @Override
    public void deleteGpu(Short id) throws GpuException {
        if (!gpuRepository.existsById(id)) {
            throw new GpuException("Gpu not found");
        }
        gpuRepository.deleteById(id);
    }
    @Override
    public GpuDTO convertToDTO(Gpu gpu) {
        return new GpuDTO(
                gpu.getId(),
                gpu.getModel(),
                gpu.getMemory(),
                gpu.getTops(),
                gpu.getBrand().getId(),
                gpu.getBrand().getName(),
                gpu.getType()
        );
    }
}