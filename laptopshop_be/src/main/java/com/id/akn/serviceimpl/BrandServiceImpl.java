package com.id.akn.serviceimpl;

import com.id.akn.exception.BrandException;
import com.id.akn.model.Brand;
import com.id.akn.repository.BrandRepository;
import com.id.akn.service.BrandService;
import com.id.akn.util.NormalizeString;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BrandServiceImpl implements BrandService {
    private BrandRepository brandRepository;

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getBrandById(Byte id) throws BrandException {
        return brandRepository.findById(id)
                .orElseThrow(() -> new BrandException("Brand not found"));
    }

    @Override
    public Brand createBrand(Brand brand) throws BrandException {
        String normalizedBrandName = NormalizeString.normalize(brand.getName());
//        if (normalizedBrandName == null || normalizedBrandName.isEmpty()) {
//            throw new BrandException("Brand name cannot be empty.");
//        }
        if (brandRepository.findByNameNormalize(normalizedBrandName) != null) {
            throw new IllegalArgumentException("Brand with the name '" + brand.getName() + "' already exists.");
        }
        brand.setName(normalizedBrandName);
        return brandRepository.save(brand);
    }

    @Override
    public Brand updateBrand(Byte id, Brand brandDetails) throws BrandException {
        Brand brand = getBrandById(id);
        String normalizedBrandName = NormalizeString.normalize(brandDetails.getName());
//        if (normalizedBrandName == null || normalizedBrandName.isEmpty()) {
//            throw new BrandException("Brand name cannot be empty.");
//        }
        Brand existingBrand = brandRepository.findByNameNormalize(normalizedBrandName);
        if (existingBrand != null && !existingBrand.getId().equals(id)) {
            throw new BrandException("Brand with the name '" + brandDetails.getName() + "' already exists.");
        }
        brand.setName(normalizedBrandName);
        return brandRepository.save(brand);
    }

    @Override
    public void deleteBrand(Byte id) {
        brandRepository.deleteById(id);
    }
}
