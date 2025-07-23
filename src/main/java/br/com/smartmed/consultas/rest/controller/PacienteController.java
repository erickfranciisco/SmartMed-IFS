package br.com.smartmed.consultas.rest.controller;

import br.com.smartmed.consultas.model.PacienteModel;
import br.com.smartmed.consultas.rest.dto.request.PacienteDTO;
import br.com.smartmed.consultas.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> obterPorId(@PathVariable int id) {
        PacienteDTO pacienteDTO = pacienteService.obterPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteDTO);
    }

    @GetMapping()
    public ResponseEntity<List<PacienteDTO>> obterTodos() {
        List<PacienteDTO> pacienteDTOList = pacienteService.obterTodos();
        return ResponseEntity.ok(pacienteDTOList);
    }

    @PostMapping()
    public ResponseEntity<PacienteDTO> salvar(@Valid @RequestBody PacienteModel novoPaciente) {
        PacienteDTO novoPacienteDTO = pacienteService.salvar(novoPaciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPacienteDTO);
    }

    @PutMapping()
    public ResponseEntity<PacienteDTO> atualizar(@Valid @RequestBody PacienteModel pacienteExistente) {
        PacienteDTO pacienteExistenteDTO = pacienteService.atualizar(pacienteExistente);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteExistenteDTO);
    }

    @DeleteMapping()
    public void deletar(@Valid @RequestBody PacienteModel pacienteExistente) {
        pacienteService.deletar(pacienteExistente);
    }
}
