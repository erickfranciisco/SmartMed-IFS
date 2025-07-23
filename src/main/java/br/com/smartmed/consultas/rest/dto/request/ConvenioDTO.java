package br.com.smartmed.consultas.rest.dto.request;

import lombok.Data;

@Data
public class ConvenioDTO {
    private int id;
    private String nome;
    private String cnpj;
    private String telefone;
    private String email;
    private boolean ativo;
}
