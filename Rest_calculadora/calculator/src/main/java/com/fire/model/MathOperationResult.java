package com.fire.model;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class MathOperationResult implements Serializable {
    private UUID operationId;

    private Double result;
}
