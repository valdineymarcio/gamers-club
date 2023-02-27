package br.ufms.cpcx.gamersclub.repositories;

import br.ufms.cpcx.gamersclub.models.ConsoleEnum;
import br.ufms.cpcx.gamersclub.models.GameModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Console;
import java.util.Optional;
import java.util.Set;

public interface GameRepository extends JpaRepository<GameModel,Long> {
    boolean existsByNameAndConsoleAndOwner(String nome, ConsoleEnum consoleEnum,String owner);
    Optional<GameModel> findByNameAndConsole(String name, ConsoleEnum console);
    Set<GameModel> findAllByPartnerId(Long id);
}
