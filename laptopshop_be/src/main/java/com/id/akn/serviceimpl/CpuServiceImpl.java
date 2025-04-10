package com.id.akn.serviceimpl;

import com.id.akn.exception.CpuException;
import com.id.akn.exception.CpuTechException;
import com.id.akn.model.Cpu;
import com.id.akn.model.CpuTech;
import com.id.akn.repository.CpuRepository;
import com.id.akn.repository.CpuTechRepository;
import com.id.akn.request.CpuDTO;
import com.id.akn.service.CpuService;
import com.id.akn.util.NormalizeString;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CpuServiceImpl implements CpuService {

    private final CpuRepository cpuRepository;
    private final CpuTechRepository cpuTechRepository;

    @Override
    public List<CpuDTO> getAllCpus() {
        return cpuRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CpuDTO getCpuDTOById(Short id) throws CpuException {
        Cpu cpu = cpuRepository.findById(id)
                .orElseThrow(() -> new CpuException("CPU found"));
        return convertToDTO(cpu);
    }

    @Override
    public Cpu getCpuById(Short id) throws CpuException {
        return cpuRepository.findById(id)
                .orElseThrow(() -> new CpuException("CPU found"));
    }

    @Override
    public CpuDTO createCpu(CpuDTO cpuDTO) throws CpuException, CpuTechException {
        CpuTech cpuTech = cpuTechRepository.findById(cpuDTO.getTechnologyId())
                .orElseThrow(() -> new CpuTechException("CPU Technology not found"));
        Cpu cpu = new Cpu();
        cpu.setTechnology(cpuTech);
        String normalizedModel = NormalizeString.normalize(cpuDTO.getModel());
        //cpuRepository.findByModelAndTechnology(normalizedModel, cpuTech) != null - xử lý trường hợp hi hữu khi có 2 model trùng nhau
        if (cpuRepository.findByNameNormalize(normalizedModel) != null) {
            throw new CpuException("CPU with the model '" + cpuDTO.getModel() + "' already exists in the specified technology.");
        }
        cpu.setModel(cpuDTO.getModel());
        cpu.setSpeed(cpuDTO.getSpeed());
        cpu.setMaxSpeed(cpuDTO.getMaxSpeed());
        cpu.setCore(cpuDTO.getCore());
        cpu.setThread(cpuDTO.getThread());
        cpu.setCache(cpuDTO.getCache());
        cpu.setTops(cpuDTO.getTops());
        return convertToDTO(cpuRepository.save(cpu));
    }

    @Override
    public CpuDTO updateCpu(Short id, CpuDTO cpuDTO) throws CpuException {
        Cpu cpu = cpuRepository.findById(id)
                .orElseThrow(() -> new CpuException("CPU not found"));
        CpuTech cpuTech = cpuTechRepository.findById(cpuDTO.getTechnologyId())
                .orElseThrow(() -> new CpuException("CPU Technology not found"));
        cpu.setTechnology(cpuTech);
        String normalizedModel = NormalizeString.normalize(cpuDTO.getModel());
        Cpu existingModel = cpuRepository.findByNameNormalize(normalizedModel);
        //Cpu existingModel = cpuRepository.findByModelAndTechnology(normalizedModel, cpuTech);
        if (existingModel != null && !existingModel.getId().equals(id)) {
            throw new CpuException("CPU with the model '" + cpuDTO.getModel() + "' already exists.");
        }
        cpu.setModel(cpuDTO.getModel());
        cpu.setSpeed(cpuDTO.getSpeed());
        cpu.setMaxSpeed(cpuDTO.getMaxSpeed());
        cpu.setCore(cpuDTO.getCore());
        cpu.setThread(cpuDTO.getThread());
        cpu.setCache(cpuDTO.getCache());
        cpu.setTops(cpuDTO.getTops());
        return convertToDTO(cpuRepository.save(cpu));
    }

    @Override
    public void deleteCpu(Short id) throws CpuException {
        if (!cpuRepository.existsById(id)) {
            throw new CpuException("CPU not found");
        }
        cpuRepository.deleteById(id);
    }

    @Override
    public CpuDTO convertToDTO(Cpu cpu) {
        return new CpuDTO(
                cpu.getId(),
                cpu.getTechnology().getBrand().getId(),
                cpu.getTechnology().getBrand().getName(),
                cpu.getTechnology().getId(),
                cpu.getTechnology().getName(),
                cpu.getModel(),
                cpu.getSpeed(),
                cpu.getMaxSpeed(),
                cpu.getCore(),
                cpu.getThread(),
                cpu.getCache(),
                cpu.getTops()
        );
    }
}
