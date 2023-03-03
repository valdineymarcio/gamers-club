package br.ufms.cpcx.gamersclub.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(
        name = "TB_GAME",
        uniqueConstraints = {@UniqueConstraint(name = "TB_GAME_UQ", columnNames = {
                "name", "console"})
        })

public class GameModel implements Serializable {
        private static final long serialVersionUID = 1L;
        @ManyToOne
        @JsonIgnore
        protected PartnerModel partnerModel;


        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;
        @Column(nullable = false, length = 100)
        private String name;
        @Enumerated (EnumType.STRING)
        @Column(nullable = false, length = 50)
        private ConsoleEnum console;
        @Column(nullable = false, length = 100)
        private String owner;
        @Column(nullable = false, length = 15)
        private String ownerPhoneNumber ;

        public void add(GameModel game) {

                this.setPartnerModel(game.getPartnerModel());
        }




}
