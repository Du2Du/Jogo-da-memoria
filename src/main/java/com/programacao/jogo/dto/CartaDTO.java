package com.programacao.jogo.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CartaDTO implements Serializable {
    private String valor;
    private Integer codigo;
    private boolean virada = false;
}
