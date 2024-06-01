package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.dto;

import org.jetbrains.annotations.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatchTenantInfoDto {

    @NotNull
    @Size(min = 1, max = 50)
    private String firstName;
    @NotNull
    @Size(min = 1, max = 50)
    private String lastName;
    @NotNull
    @Size(min = 9, max = 12)
    private String phoneNumber;
}