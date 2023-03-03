package br.ufms.cpcx.gamersclub.models;

import br.ufms.cpcx.gamersclub.models.GameModel;
import br.ufms.cpcx.gamersclub.models.PartnerModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class GameLoanModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private int id;
    @OneToOne
    private PartnerModel partner;
    @OneToOne
    private GameModel games;
    @Column(nullable = false, length = 10)
    private LocalDate loanDate;
    @Column(nullable = false, length = 10)
    private LocalDate scheduledReturnDate;
    @Column(nullable = false, length = 10)
    private LocalDate returnDate;


    public GameLoanModel(GameModel game, PartnerModel partner) {
        this.games = game;
        this.partner = partner;
        this.loanDate = LocalDate.now();
        this.scheduledReturnDate = LocalDate.now().plusDays(7);
    }

}

