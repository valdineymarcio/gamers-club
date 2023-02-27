package br.ufms.cpcx.gamersclub.repositories;

import br.ufms.cpcx.gamersclub.models.PartnerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<PartnerModel, Long> {
}
