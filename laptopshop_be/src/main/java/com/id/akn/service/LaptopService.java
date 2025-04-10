package com.id.akn.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.id.akn.exception.*;
import com.id.akn.request.LaptopDTO;
import org.springframework.data.domain.Page;

import com.id.akn.request.LaptopDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface LaptopService {
	List<LaptopDTO> getAllLaptops();
	LaptopDTO getLaptopById(Integer id) throws LaptopException;
	LaptopDTO createLaptop(LaptopDTO laptopDTO) throws LaptopException, OsVersionException, BrandException, CpuException, ColorException;
	LaptopDTO saveLaptopImages(Integer laptopId, Set<MultipartFile> files) throws LaptopException, OsVersionException, BrandException, CpuException, IOException, ColorException;
	LaptopDTO updateLaptop(Integer id, LaptopDTO laptopDTO) throws LaptopException, OsVersionException, BrandException, CpuException, IOException, ColorException;
	void deleteLaptop(Integer id) throws LaptopException, IOException;

	List<LaptopDTO> searchLaptop(String query);
	List<LaptopDTO> getTop10LaptopsByDiscount() ;

	Page<LaptopDTO> getAllLaptop(List<String> colors, String category, Float discountPercentMin, Float discountPercentMax, List<Float> screenSize, Long minPrice, Long maxPrice,
								 Short stockStatus, String sortPrice, Byte minRamMemory, Byte maxRamMemory, Short cpuId, List<Short> gpuIds,
								 Short minDiskCapacity, Short maxDiskCapacity, Byte brandId, Pageable pageable);
}
