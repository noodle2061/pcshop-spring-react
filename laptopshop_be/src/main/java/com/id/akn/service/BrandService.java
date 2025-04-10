package com.id.akn.service;

import com.id.akn.exception.BrandException;
import com.id.akn.model.Brand;

import java.util.List;

public interface BrandService {
    List<Brand> getAllBrands();
    Brand getBrandById(Byte id) throws BrandException;
    Brand createBrand(Brand brand) throws BrandException;
    Brand updateBrand(Byte id, Brand brand) throws BrandException;
    void deleteBrand(Byte id);
}
