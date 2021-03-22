package com.spring.file.operations.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileInformationsDto {
    String fileName;
    String contentType;
    Long size;
}
