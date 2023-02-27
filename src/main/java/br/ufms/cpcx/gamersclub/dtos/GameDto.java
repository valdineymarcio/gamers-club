package br.ufms.cpcx.gamersclub.dtos;

import br.ufms.cpcx.gamersclub.models.ConsoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class GameDto {
    @NotBlank
    @Size(max = 100)
    private String name;
    private ConsoleEnum console;
    @NotBlank
    @Size(max = 100)
    private String owner;
    @NotBlank
    @Size(max = 15)
    private String ownerPhoneNumber ;
}
