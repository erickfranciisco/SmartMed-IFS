package br.com.smartmed.consultas.rest.dto.request;

import java.time.LocalDate;

public class RecepcionistaDTO {
    private int id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private LocalDate dataAdmissao;
    private LocalDate dataDemissao;
    private String telefone;
    private String email;
    private boolean ativo;
}
