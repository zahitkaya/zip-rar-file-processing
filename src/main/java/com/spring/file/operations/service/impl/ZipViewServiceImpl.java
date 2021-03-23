package com.spring.file.operations.service.impl;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;
import com.spring.file.operations.model.FileInformationsDto;
import com.spring.file.operations.service.ZipViewService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;

import java.util.zip.ZipInputStream;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class ZipViewServiceImpl implements ZipViewService {


    List<FileInformationsDto> list = new ArrayList<>();

    @Override
    public List<FileInformationsDto> getZipInfo(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(bytes));
        ZipEntry entry = null;
        list.clear();
        while ((entry = zis.getNextEntry()) != null) {
            String mimeType = getContentTypeByName(entry.getName());
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

    @Override
    public List<FileInformationsDto> unPackRar(MultipartFile rarFile) throws RarException, IOException {
        List<FileInformationsDto> files = new ArrayList<>();
        final Archive archive = new Archive(rarFile.getInputStream());
        for (FileHeader fileHeader=archive.nextFileHeader();fileHeader!=null;fileHeader=archive.nextFileHeader()){
            String fileName = fileHeader.getFileName();
            if (isValidFile(getContentTypeByName(fileName)+"")) {
                FileInformationsDto fileInformationsDto = FileInformationsDto.builder()
                        .fileName(fileName)
                        .size(fileHeader.getUnpSize())
                        .contentType(getContentTypeByName(fileName))
                        .build();
                files.add(fileInformationsDto);
            }
        }
        return files;
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

    public String getContentTypeByName(String fileName) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        return fileNameMap.getContentTypeFor(fileName);
    }

}

