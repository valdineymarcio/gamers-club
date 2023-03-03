package br.ufms.cpcx.gamersclub.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TB_PARTNER",
        uniqueConstraints = {@UniqueConstraint(name = "partner_uq", columnNames = {
                "name", "phoneNumber"})})
public class PartnerModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 15)
    private String phoneNumber;
   @OneToMany(mappedBy = "TB_PARTNER", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GameModel> games = new HashSet<>();
    @JsonIgnore
    public void setGames(Set<GameModel> games) {

        this.games.addAll(games);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PartnerModel partner = (PartnerModel) o;
        return id != null && Objects.equals(id, partner.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
