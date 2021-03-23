package com.spring.file.operations.service;


import com.github.junrar.exception.RarException;
import com.spring.file.operations.model.FileInformationsDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ZipViewService {
    List<FileInformationsDto>  getZipInfo(MultipartFile multipartFile) throws IOException;
    List<FileInformationsDto> unPackRar(MultipartFile rarFile) throws IOException, RarException;
}
