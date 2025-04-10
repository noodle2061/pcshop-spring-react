package com.id.akn.serviceimpl;

import com.id.akn.exception.BrandException;
import com.id.akn.exception.CpuTechException;
import com.id.akn.model.Brand;
import com.id.akn.model.CpuTech;
import com.id.akn.repository.CpuTechRepository;
import com.id.akn.request.CpuTechDTO;
import com.id.akn.service.BrandService;
import com.id.akn.service.CpuTechService;
import com.id.akn.util.NormalizeString;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CpuTechServiceImpl implements CpuTechService {

    private final CpuTechRepository cpuTechRepository;
    private final BrandService brandService;

    @Override
    public List<CpuTechDTO> getAllCpuTechs() {
        return cpuTechRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CpuTechDTO getCpuTechById(Short id) throws CpuTechException {
        CpuTech cpuTech = cpuTechRepository.findById(id)
                .orElseThrow(() -> new CpuTechException("CpuTech not found"));
        return convertToDTO(cpuTech);
    }

    @Override
    public CpuTechDTO createCpuTech(CpuTechDTO cpuTechDTO) throws BrandException, CpuTechException {
        Brand brand = brandService.getBrandById(cpuTechDTO.getBrandId());
        CpuTech cpuTech = new CpuTech();
        cpuTech.setBrand(brand);
        String normalizedTech = NormalizeString.normalize(cpuTechDTO.getTechName());
//        if (normalizedTech == null || normalizedTech.isEmpty()) {
//            throw new CpuTechException("Technology name cannot be empty.");
//        }
        if (cpuTechRepository.findByNameNormalize(normalizedTech) != null) {
            throw new CpuTechException("CPU technology with the name '" + cpuTechDTO.getTechName() + "' already exists.");
        }
        cpuTech.setName(cpuTechDTO.getTechName().trim().replaceAll("\\s+", " "));
        return convertToDTO(cpuTechRepository.save(cpuTech));
    }

    @Override
    public CpuTechDTO updateCpuTech(Short id, CpuTechDTO cpuTechDTO) throws CpuTechException, BrandException {
        CpuTech cpuTech = cpuTechRepository.findById(id)
                .orElseThrow(() -> new CpuTechException("CpuTech not found"));
        Brand brand = brandService.getBrandById(cpuTechDTO.getBrandId());
        cpuTech.setBrand(brand);
        String normalizedTech = NormalizeString.normalize(cpuTechDTO.getTechName());
//        if (normalizedTech == null || normalizedTech.isEmpty()) {
//            throw new CpuTechException("CPU technology name cannot be empty.");
//        }
        CpuTech existingTech = cpuTechRepository.findByNameNormalize(normalizedTech);
        if (existingTech != null && !existingTech.getId().equals(id)) {
            throw new CpuTechException("CPU technology with the name '" + cpuTechDTO.getTechName() + "' already exists.");
        }
        cpuTech.setName(cpuTechDTO.getTechName().trim().replaceAll("\\s+", " "));
        return convertToDTO(cpuTechRepository.save(cpuTech));
    }

    @Override
    public void deleteCpuTech(Short id) throws CpuTechException {
        if (!cpuTechRepository.existsById(id)) {
            throw new CpuTechException("CpuTech not found");
        }
        cpuTechRepository.deleteById(id);
    }

    private CpuTechDTO convertToDTO(CpuTech cpuTech) {
        return new CpuTechDTO(
                cpuTech.getId(),
                cpuTech.getBrand().getId(),
                cpuTech.getBrand().getName(),
                cpuTech.getName()
        );
    }
}
