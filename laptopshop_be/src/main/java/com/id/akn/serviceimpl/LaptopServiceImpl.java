package com.id.akn.serviceimpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.id.akn.exception.*;
import com.id.akn.model.*;
import com.id.akn.repository.*;
import com.id.akn.request.*;
import com.id.akn.service.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class LaptopServiceImpl implements LaptopService {

    private LaptopRepository laptopRepository;
    private BrandService brandService;
    private OsVersionService osVersionService;
    private ColorService colorService;
    private ImageStorageService imageStorageService;
    private CpuService cpuService;
    private GpuService gpuService;
    private CategoryService categoryService;
    private LaptopColorRepository laptopColorRepository;
    @Override
    public List<LaptopDTO> getAllLaptops() {
        return laptopRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LaptopDTO getLaptopById(Integer id) throws LaptopException {
        Laptop laptop = laptopRepository.findById(id)
                .orElseThrow(() -> new LaptopException("Laptop not found"));
        return convertToDTO(laptop);
    }

    @Override
    public LaptopDTO createLaptop(LaptopDTO laptopDTO) throws OsVersionException, BrandException, CpuException, ColorException, LaptopException {

        Laptop laptop = new Laptop();
        laptop.setBrand(brandService.getBrandById(laptopDTO.getBrandId()));

        if(laptopRepository.existsLaptopByModel(laptopDTO.getModel())){
            throw new LaptopException("Model Laptop is existed");
        }
        laptop.setModel(laptopDTO.getModel());
        laptop.setCpu(cpuService.getCpuById(laptopDTO.getCpu().getId()));
        laptop.setGpus(laptopDTO.getGpus().stream().map(gpu -> {
            try {
                return gpuService.getGpuById(gpu.getId());
            } catch (GpuException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toSet()));
        laptop.setRamMemory(laptopDTO.getRamMemory());
        laptop.setRamDetail(laptopDTO.getRamDetail());
        laptop.setDiskCapacity(laptopDTO.getDiskCapacity());
        laptop.setDiskDetail(laptopDTO.getDiskDetail());
        laptop.setScreenSize(laptopDTO.getScreenSize());
        laptop.setScreenDetail(laptopDTO.getScreenDetail());
        laptop.setOsVersion(osVersionService.getOsVersionById(laptopDTO.getOsVersionId()));
        laptop.setKeyboardType(laptopDTO.getKeyboardType());
        laptop.setBatteryCharger(laptopDTO.getBatteryCharger());
        laptop.setDesign(laptopDTO.getDesign());
        laptop.setOrigin(laptopDTO.getOrigin());
        laptop.setWarranty(laptopDTO.getWarranty());
        laptop.setPrice(laptopDTO.getPrice());
        laptop.setDiscountPercent(laptopDTO.getDiscountPercent());
        laptop.setStatus((short) 1);
        laptop.setCategories(laptopDTO.getCategories().stream().map(category -> {
            try {
                return categoryService.getCategoryById(category.getId());
            } catch (CategoryException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toSet()));
        laptop.setCreatedAt(LocalDateTime.now());

        Set<LaptopColor> laptopColors = new HashSet<>();
        for(LaptopColorDTO lcd: laptopDTO.getLaptopColors() ){
            LaptopColor laptopColor = LaptopColor.builder()
                    .color(colorService.getColorById(lcd.getColorId()))
                    .quantity(lcd.getQuantity())
                    .build();
            LaptopColor savedLaptopColor = laptopColorRepository.save(laptopColor);
            laptopColors.add(savedLaptopColor);
        }
        laptop.setLaptopColors(laptopColors);
        System.out.println("BREAKPOINT1");
        Laptop savedLaptop = laptopRepository.save(laptop);
        System.out.println("BREAKPOINT2");
        for(LaptopColor lc : laptopColors){
            lc.setLaptop(laptop);
            laptopColorRepository.save(lc);
        }

        return convertToDTO(savedLaptop);
    }

    @Override
    public LaptopDTO saveLaptopImages(Integer laptopId, Set<MultipartFile> files) throws LaptopException, OsVersionException, BrandException, CpuException, IOException, ColorException {
        LaptopDTO laptop = getLaptopById(laptopId);
        if (laptop != null) {
            Set<String> imageUrls = imageStorageService.saveFiles(laptop.getId(), files);
            laptop.setImageUrls(imageUrls);
            return updateLaptop(laptop.getId(), laptop);
        }
        return null;
    }

    @Override
    public LaptopDTO updateLaptop(Integer id, LaptopDTO laptopDTO) throws LaptopException, OsVersionException, IOException, ColorException, BrandException, CpuException {
        Laptop laptop = laptopRepository.findById(id).orElseThrow(() -> new LaptopException("Laptop not found"));
        laptop.setBrand(brandService.getBrandById(laptopDTO.getBrandId()));
        laptop.setModel(laptopDTO.getModel());
        laptop.setCpu(cpuService.getCpuById(laptopDTO.getCpu().getId()));
        laptop.setRamMemory(laptopDTO.getRamMemory());
        laptop.setRamDetail(laptopDTO.getRamDetail());
        laptop.setDiskCapacity(laptopDTO.getDiskCapacity());
        laptop.setDiskDetail(laptopDTO.getDiskDetail());
        laptop.setScreenSize(laptopDTO.getScreenSize());
        laptop.setScreenDetail(laptopDTO.getScreenDetail());
        laptop.setOsVersion(osVersionService.getOsVersionById(laptopDTO.getOsVersionId()));
        laptop.setKeyboardType(laptopDTO.getKeyboardType());
        laptop.setBatteryCharger(laptopDTO.getBatteryCharger());
        laptop.setDesign(laptopDTO.getDesign());
        laptop.setOrigin(laptopDTO.getOrigin());
        laptop.setWarranty(laptopDTO.getWarranty());
        laptop.setPrice(laptopDTO.getPrice());
        laptop.setDiscountPercent(laptopDTO.getDiscountPercent());
        laptop.setStatus(laptopDTO.getStatus());

//        Set<Gpu> updatedGpus = laptopDTO.getGpus().stream()
//            .map(dto -> {
//                try {
//                    return gpuService.getGpuById(dto.getId());
//                } catch (GpuException e) {
//                    throw new RuntimeException(e);
//                }
//            }).collect(Collectors.toSet());
//        laptop.getGpus().removeIf(existingGpu -> updatedGpus.stream().noneMatch(updatedGpu -> updatedGpu.equals(existingGpu)));
//        laptop.getGpus().addAll(updatedGpus);
        laptop.setGpus(laptopDTO.getGpus().stream().map(gpu -> {
            try {
                return gpuService.getGpuById(gpu.getId());
            } catch (GpuException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toSet()));

        Set<LaptopColorDTO> updatedColorsDTO = laptopDTO.getLaptopColors();
        Set<LaptopColor> existingLaptopColors = laptop.getLaptopColors();
        for (LaptopColorDTO dto : updatedColorsDTO) {
            LaptopColor existingColor = existingLaptopColors.stream()
                    .filter(lc -> lc.getColor().getId().equals(dto.getColorId()))
                    .findFirst()
                    .orElse(null);

            if (existingColor != null) {
                existingColor.setQuantity(dto.getQuantity());
            } else {
                LaptopColor newColor = new LaptopColor();
                newColor.setLaptop(laptop);
                newColor.setColor(colorService.getColorById(dto.getColorId()));
                newColor.setQuantity(dto.getQuantity());
                existingLaptopColors.add(newColor);
            }
        }
        existingLaptopColors.removeIf(existingColor ->
                updatedColorsDTO.stream().noneMatch(dto -> dto.getColorId().equals(existingColor.getColor().getId())));
        laptop.setLaptopColors(existingLaptopColors);

        laptop.setCategories(laptopDTO.getCategories().stream().map(category -> {
            try {
                return categoryService.getCategoryById(category.getId());
            } catch (CategoryException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toSet()));

        Set<String> existingImageUrls = laptop.getImageUrls();
        Set<String> updatedImageUrls = laptopDTO.getImageUrls();
        Set<String> imageUrlsToDelete = new HashSet<>(existingImageUrls);
        imageUrlsToDelete.removeAll(updatedImageUrls);
        imageStorageService.deleteFiles(id, imageUrlsToDelete);
        laptop.setImageUrls(updatedImageUrls);

        return convertToDTO(laptopRepository.save(laptop));
    }

    @Override
    public void deleteLaptop(Integer id) throws LaptopException {
        if (!laptopRepository.existsById(id)) {
            throw new LaptopException("Laptop not found");
        }
        Laptop laptop = laptopRepository.findById(id).get();
        laptop.setStatus((short) 0);
        laptopRepository.saveAndFlush(laptop);
    }

    @Override
    public Page<LaptopDTO> getAllLaptop(List<String> colors, String category, Float discountPercentMin, Float discountPercentMax, List<Float> screenSize, Long minPrice, Long maxPrice,
                                        Short stockStatus, String sortPrice, Byte minRamMemory, Byte maxRamMemory, Short cpuId, List<Short> gpuIds,
                                        Short minDiskCapacity, Short maxDiskCapacity, Byte brandId, Pageable pageable) {

        Specification<Laptop> specification = Specification.where(null);

        specification = addCondition(specification, colors != null && !colors.isEmpty(),
                (root, query, cb) -> {
                    Join<Object, Object> laptopColorsJoin = root.join("laptopColors");
                    Join<Object, Object> colorJoin = laptopColorsJoin.join("color");
                    return colorJoin.get("name").in(colors);
                });

        specification = addCondition(specification, category != null,
                (root, query, cb) -> cb.equal(root.join("categories").get("name"), category));

        specification = addCondition(specification, discountPercentMin != null,
                (root, query, cb) -> cb.between(root.get("discountPercent"), discountPercentMin, discountPercentMax));

        specification = addCondition(specification, stockStatus != null,
                (root, query, cb) -> {
                    Join<Object, Object> laptopColorsJoin = root.join("laptopColors");
                    if (stockStatus == 0) {
                        return cb.equal(laptopColorsJoin.get("quantity"), 0);
                    }
                    return cb.greaterThan(laptopColorsJoin.get("quantity"), 0);
                });

        specification = addCondition(specification, screenSize != null && !screenSize.isEmpty(),
                (root, query, cb) -> {
                    Predicate predicate = cb.disjunction();
                    for (Float size : screenSize) {
                        predicate = cb.or(predicate, cb.between(root.get("screenSize"), size - 0.05, size + 0.05));
                    }
                    return predicate;
                });

        specification = addCondition(specification, minPrice != null && maxPrice != null,
                (root, query, cb) -> cb.between(root.get("price"), minPrice, maxPrice));

        specification = addCondition(specification, sortPrice != null,
                (root, query, cb) -> {
                    if ("increase".equalsIgnoreCase(sortPrice)) {
                        query.orderBy(cb.asc(root.get("price")));
                    } else if ("decrease".equalsIgnoreCase(sortPrice)) {
                        query.orderBy(cb.desc(root.get("price")));
                    }
                    return null;
                });

        specification = addCondition(specification, minRamMemory != null && maxRamMemory != null,
                (root, query, cb) -> cb.between(root.get("ramMemory"), minRamMemory, maxRamMemory));

        specification = addCondition(specification, cpuId != null,
                (root, query, cb) -> cb.equal(root.join("cpu").get("id"), cpuId));

        specification = addCondition(specification, gpuIds != null && !gpuIds.isEmpty(),
                (root, query, cb) -> root.join("gpus").get("id").in(gpuIds));

        specification = addCondition(specification, minDiskCapacity != null && maxDiskCapacity != null,
                (root, query, cb) -> cb.between(root.get("diskCapacity"), minDiskCapacity, maxDiskCapacity));

        specification = addCondition(specification, brandId != null,
                (root, query, cb) -> cb.equal(root.join("brand").get("id"), brandId));

        Page<Laptop> laptopPage = laptopRepository.findAll(specification, pageable);

        List<LaptopDTO> laptopDTOs = laptopPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(laptopDTOs, pageable, laptopPage.getTotalElements());
    }

    // Hàm addCondition bổ trợ
    private Specification<Laptop> addCondition(Specification<Laptop> specification, boolean condition,
                                               Specification<Laptop> newSpecification) {
        return condition ? specification.and(newSpecification) : specification;
    }


    @Override
    public List<LaptopDTO> searchLaptop(String query) {
        List<Laptop> laptops = laptopRepository.searchLaptop(query);
        return laptops.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<LaptopDTO> getTop10LaptopsByDiscount() {
        return laptopRepository.findTop10ByHighestDiscount()
                .stream()
                .limit(10)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public LaptopDTO convertToDTO(Laptop laptop) {
        return new LaptopDTO(laptop.getId(), laptop.getBrand().getId(),
                laptop.getBrand().getName(), laptop.getModel(),
                cpuService.convertToDTO(laptop.getCpu()),
                laptop.getGpus().stream().map(gpuService::convertToDTO)
                        .collect(Collectors.toSet()), laptop.getRamMemory(),
                laptop.getRamDetail(), laptop.getDiskCapacity(),
                laptop.getDiskDetail(), laptop.getScreenSize(),
                laptop.getScreenDetail(), laptop.getOsVersion().getId(),
                laptop.getOsVersion().getVersion(),
                laptop.getKeyboardType(),
                laptop.getBatteryCharger(),
                laptop.getDesign(),
                laptop.getLaptopColors().stream().map(laptopColor -> new LaptopColorDTO(
                        laptopColor.getId(),
                        laptopColor.getLaptop().getId(),
                        laptopColor.getLaptop().getModel(),
                        laptopColor.getColor().getId(),
                        laptopColor.getColor().getName(),
                        laptopColor.getQuantity()
                )).collect(Collectors.toSet()),
                laptop.getCategories().stream().map(categoryService::convertToDTO)
                        .collect(Collectors.toSet()),
                laptop.getOrigin(),
                laptop.getWarranty(),
                laptop.getPrice(),
                laptop.getImageUrls(),
                laptop.getDiscountPercent(),
                laptop.getStatus()
        );
    }
}
