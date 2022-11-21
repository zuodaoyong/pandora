package com.pandora.core.biz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistryParam implements Serializable {
    private static final long serialVersionUID = -8338572437672208551L;

    private String registryGroup;

    private String registryKey;

    private String registryValue;

}
