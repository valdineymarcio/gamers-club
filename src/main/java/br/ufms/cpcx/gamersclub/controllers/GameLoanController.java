package br.ufms.cpcx.gamersclub.controllers;

import br.ufms.cpcx.gamersclub.dtos.IdDto;
import br.ufms.cpcx.gamersclub.models.GameLoanModel;
import br.ufms.cpcx.gamersclub.services.GameLoanService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.Optional;

@RestController
@RequestMapping("v2/game-loan")

public class GameLoanController {
    private final GameLoanService gameLoanService;


    public GameLoanController(GameLoanService gameLoanService) {
        this.gameLoanService = gameLoanService;
    }

    @PostMapping("/{partnerId}")
    public ResponseEntity<GameLoanModel> newGameLoan(@PathVariable Long partnerId, @RequestBody IdDto id) {
        try {
            GameLoanModel gameLoan = this.gameLoanService.newGameLoan(partnerId, id.getId());
            return ResponseEntity.ok(gameLoan);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<GameLoanModel>> findGameLoanById(@PathVariable Long id) {
        try {
            Optional<GameLoanModel> gameLoan = this.gameLoanService.findById(id);
            return ResponseEntity.ok(gameLoan);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping
    public ResponseEntity<Page<GameLoanModel>> findAllGameLoans(Pageable pageable) {
        try {
            Page<GameLoanModel> gameLoans = this.gameLoanService.findAll(pageable);
            return ResponseEntity.ok(gameLoans);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameLoan(@PathVariable Long id) {
        try {
            this.gameLoanService.deleteGameLoan(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
