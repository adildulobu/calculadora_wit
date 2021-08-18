package com.fire.model;


import lombok.*;

import com.fire.utils.Operator;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class MathOperation implements Serializable {

    private UUID uuid;

    private int a;

    private int b;

    private Operator operator;
}
