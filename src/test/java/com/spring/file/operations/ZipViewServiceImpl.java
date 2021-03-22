package com.spring.file.operations;

import com.spring.file.operations.model.FileInformationsDto;
import com.spring.file.operations.service.ZipViewService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class ZipViewServiceImpl implements ZipViewService {




    @Override
    public List<FileInformationsDto> getZipInfo(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(bytes));
        ZipEntry entry = null;
        List<FileInformationsDto> list = new ArrayList<>();
        while ((entry = zis.getNextEntry()) != null) {
            FileNameMap fileNameMap = URLConnection.getFileNameMap();
            String mimeType = fileNameMap.getContentTypeFor(entry.getName());
            // "process each file, based on what it is and whether its a directory etc."
            if (!entry.isDirectory() && mimeType != null && (isValidFile(mimeType))) {
                FileInformationsDto fileInformationsDto = FileInformationsDto.builder()
                        .size(entry.getSize())
                        .fileName(entry.getName())
                        .contentType(mimeType)
                        .build();
                list.add(fileInformationsDto);
            }
        }
        return list;
    }

    public boolean isValidFile(String fileName) {
        List<String> validMimeTypes = new ArrayList<>();
        validMimeTypes.add("image/png");
        validMimeTypes.add("application/xml");
        validMimeTypes.add("application/pdf");
        validMimeTypes.add("text/html");
        validMimeTypes.add("text/xml");
        validMimeTypes.add("image/jpeg");
        validMimeTypes.add("application/json");
        validMimeTypes.add("application/xhtml+xml");
        validMimeTypes.add("application/zip");

        for (String file : validMimeTypes) {
            if (file.equals(fileName.toLowerCase()))
                return true;
        }
        return false;
    }

}



      /*
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/download/")
                .path(fileName)
                .toUriString();

         */

