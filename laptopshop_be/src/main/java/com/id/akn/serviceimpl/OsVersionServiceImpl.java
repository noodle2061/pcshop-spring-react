package com.id.akn.serviceimpl;

import com.id.akn.exception.ColorException;
import com.id.akn.exception.OsVersionException;
import com.id.akn.model.Color;
import com.id.akn.model.OsVersion;
import com.id.akn.repository.OsVersionRepository;
import com.id.akn.service.OsVersionService;
import com.id.akn.util.NormalizeString;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OsVersionServiceImpl implements OsVersionService {
    private OsVersionRepository osVersionRepository;
    @Override
    public List<OsVersion> getAllOsVersions() {
        return osVersionRepository.findAll();
    }

    @Override
    public OsVersion getOsVersionById(Short id) throws OsVersionException {
        return osVersionRepository.findById(id)
                .orElseThrow(() -> new OsVersionException("OS version not found"));
    }

    @Override
    public OsVersion createOsVersion(OsVersion osVersion) throws OsVersionException {
        String normalizedOsVersion = NormalizeString.normalize(osVersion.getVersion());
//        if (normalizedOsVersion == null || normalizedOsVersion.isEmpty()) {
//            throw new OsVersionException("OS version name cannot be empty.");
//        }
        if (osVersionRepository.findByNameNormalize(normalizedOsVersion) != null) {
            throw new IllegalArgumentException("OS version with the name '" + osVersion.getVersion() + "' already exists.");
        }
        osVersion.setVersion(osVersion.getVersion().trim().replaceAll("\\s+", " "));
        return osVersionRepository.save(osVersion);
    }

    @Override
    public OsVersion updateOsVersion(Short id, OsVersion osVersionDetails) throws OsVersionException {
        OsVersion osVersion = getOsVersionById(id);
        String normalizedOsVersion = NormalizeString.normalize(osVersionDetails.getVersion());
//        if (normalizedOsVersion == null || normalizedOsVersion.isEmpty()) {
//            throw new OsVersionException("OS version name cannot be empty.");
//        }
        OsVersion existingOsVersion = osVersionRepository.findByNameNormalize(normalizedOsVersion);
        if (existingOsVersion != null && !existingOsVersion.getId().equals(id)) {
            throw new OsVersionException("OS version with the name '" + osVersionDetails.getVersion() + "' already exists.");
        }
        osVersion.setVersion(osVersion.getVersion().trim().replaceAll("\\s+", " "));
        return osVersionRepository.save(osVersion);
    }

    @Override
    public void deleteOsVersion(Short id) {
        osVersionRepository.deleteById(id);
    }
}
