package br.ufms.cpcx.gamersclub.dtos;

import br.ufms.cpcx.gamersclub.models.ConsoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class GameDto {
    @NotBlank
    @Size(max = 100)
    private String name;
    @NotNull
    @NotBlank
    private ConsoleEnum console;

}
