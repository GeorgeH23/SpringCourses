package com.george.xjmsmessaging.model;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HelloWorldMessage implements Serializable {

    static final long serialVersionUID = -4424496662924608382L;

    private UUID id;
    private String message;
}
