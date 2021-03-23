package com.spring.file.operations.controller.rest;

import com.github.junrar.exception.RarException;
import com.spring.file.operations.model.FileInformationsDto;
import com.spring.file.operations.model.ZipDto;
import com.spring.file.operations.service.impl.ZipViewServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import net.lingala.zip4j.core.ZipFile;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("v1")
public class ZipViewController {

    final ZipViewServiceImpl zipViewService;

    @PostMapping(path = "/getZipInformations",consumes = { "multipart/form-data" })
    public List<FileInformationsDto> uploadToLocalFileSystem(@RequestParam("file") MultipartFile file) throws IOException {
        return zipViewService.getZipInfo(file);
    }

    @PostMapping(path = "/getRarInformations",consumes = { "multipart/form-data" })
    public List<FileInformationsDto> a(@RequestParam("file") MultipartFile file) throws RarException, IOException {
         return zipViewService.unPackRar(file);
    }








}
