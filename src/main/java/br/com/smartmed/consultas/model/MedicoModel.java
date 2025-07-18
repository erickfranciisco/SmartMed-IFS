package br.com.smartmed.consultas.model;

import br.com.smartmed.consultas.rest.dto.MedicoDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medico")
public class MedicoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

    @Column(name = "crm", length = 8, nullable = false, unique = true)
    private String crm;

    @Column(name = "telefone", length = 11, nullable = false)
    @Length(min = 11, max = 11, message = "O campo deve ter exatamente 11 dígitos. Ex.: 79912345678")
    @NotNull(message = "O telefone não pode ser nulo.")
    @NotBlank(message = "O telefone é obrigatório.")
    private String telefone;

    @Column(name = "email", length = 64, nullable = true)
    @Email(message = "Email inválido!")
    private String email;

    @Column(name = "valorConsultaReferencia", nullable = false)
    private float valorConsultaReferencia;

    @Column(name = "ativo", nullable = false)
    private boolean ativo;

    @Column(name = "especialidadeID", nullable = false)
    private int especialidadeId;

    public MedicoDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, MedicoDTO.class);
    }

}
