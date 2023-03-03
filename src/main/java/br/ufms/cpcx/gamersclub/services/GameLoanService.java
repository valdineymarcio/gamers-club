package br.ufms.cpcx.gamersclub.services;

import br.ufms.cpcx.gamersclub.models.GameLoanModel;
import br.ufms.cpcx.gamersclub.repositories.GameLoanRepository;
import br.ufms.cpcx.gamersclub.repositories.GameRepository;
import br.ufms.cpcx.gamersclub.repositories.PartnerRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
public class GameLoanService {
    private final GameLoanRepository gameLoanRepository;
    private final GameRepository gameRepository;
    private final PartnerRepository partnerRepository;
    private final int maxGame = 5;

    public GameLoanService(GameLoanRepository gameLoanRepository, GameRepository gameRepository, PartnerRepository partnerRepository) {
        this.gameLoanRepository = gameLoanRepository;
        this.gameRepository = gameRepository;
        this.partnerRepository = partnerRepository;
    }


    public Set<GameLoanModel> findAllByPartnerId(Long id) {
        return this.gameLoanRepository.findAllByPartnerId(id);
    }

    @Transactional
    public GameLoanModel newGameLoan(Long gameId, Long partnerId) {
        var game = this.gameRepository.findById(gameId).orElseThrow();
        var gameLoanByPartner = this.gameLoanRepository.findAllByPartnerId(partnerId);

        if (gameLoanByPartner.size() >= maxGame) {
            throw new RuntimeException("Loan limit reached, let's return the games brother");
        }
        if(gameLoanByPartner.size() > 0){
            for (GameLoanModel gameLoan : gameLoanByPartner) {
                if (gameLoan.getLoanDate().isAfter(gameLoan.getReturnDate())) {
                    throw new RuntimeException("Game still not returned");
                }}
        }
        if(gameLoanByPartner.isEmpty()){
            var gameLoan = new GameLoanModel();
            gameLoan.setGames(game);
            gameLoan.setPartner(this.partnerRepository.findById(partnerId).orElseThrow());
            gameLoan.setLoanDate(LocalDate.now());
            gameLoan.setReturnDate(LocalDate.now().plusDays(7));
            return this.gameLoanRepository.saveAndFlush(gameLoan);
        }else{
            throw new RuntimeException("borrowed game");
        }
    }
    @Transactional
    public Optional<GameLoanModel> findById(Long id) {
        return this.gameLoanRepository.findById(id);
    }

    @Transactional
    public Page<GameLoanModel> findAll(Pageable page) {
        return this.gameLoanRepository.findAll((org.springframework.data.domain.Pageable) page);
    }

    @Transactional
    public void deleteGameLoan(Long id) {
        this.gameLoanRepository.deleteById(id);
    }


}
