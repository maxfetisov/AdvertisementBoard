package com.advertisementboard.data.dto.advertisement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class AdvertisementRequestDto {

    @NonNull
    private String heading;

    private String text;

    @NonNull
    private String userLogin;

    @NonNull
    private Long categoryId;

}
