package br.com.smartmed.consultas.rest.dto.request;

import lombok.Data;

@Data
public class EspecialidadeDTO {
    private int id;
    private String nome;
    private String descricao;
}
