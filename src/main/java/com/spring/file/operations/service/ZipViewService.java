package com.spring.file.operations.service;


import com.spring.file.operations.model.FileInformationsDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ZipViewService {
    List<FileInformationsDto>  getZipInfo(MultipartFile multipartFile) throws IOException;
}
