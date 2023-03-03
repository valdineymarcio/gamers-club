package br.ufms.cpcx.gamersclub.repositories;

import br.ufms.cpcx.gamersclub.models.GameLoanModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface GameLoanRepository extends JpaRepository<GameLoanModel,Long> {
    Optional<GameLoanModel> findByGames_Id(Long id);

    void deleteById(Long id);
    Set<GameLoanModel> findAllByPartnerId(Long id);


}
