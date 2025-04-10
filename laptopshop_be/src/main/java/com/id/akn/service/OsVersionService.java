package com.id.akn.service;


import com.id.akn.exception.OsVersionException;
import com.id.akn.model.OsVersion;

import java.util.List;

public interface OsVersionService {
    List<OsVersion> getAllOsVersions();
    OsVersion getOsVersionById(Short id) throws OsVersionException;
    OsVersion createOsVersion(OsVersion osVersion) throws OsVersionException;
    OsVersion updateOsVersion(Short id, OsVersion osVersion) throws OsVersionException;
    void deleteOsVersion(Short id);
}
