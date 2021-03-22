package com.spring.file.operations.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.hateoas.RepresentationModel;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ZipDto extends RepresentationModel<ZipDto> {
    String dowladUrl;
}
