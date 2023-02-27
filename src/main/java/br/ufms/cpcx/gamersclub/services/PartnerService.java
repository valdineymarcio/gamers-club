package br.ufms.cpcx.gamersclub.services;

import br.ufms.cpcx.gamersclub.dtos.GameDto;
import br.ufms.cpcx.gamersclub.dtos.PartnerDto;
import br.ufms.cpcx.gamersclub.models.GameModel;
import br.ufms.cpcx.gamersclub.models.PartnerModel;
import br.ufms.cpcx.gamersclub.repositories.PartnerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;
    private final GameService gameService;

    @Autowired
    public PartnerService(PartnerRepository partnerRepository, GameService gameService) {
        this.partnerRepository = partnerRepository;
        this.gameService = gameService;
    }

    @Transactional
    public Set<GameModel> newPartner(PartnerDto partnerDto) {
        PartnerModel partner = new PartnerModel();
        partner.setName(partnerDto.getName());
        partner.setPhoneNumber(partnerDto.getPhoneNumber());
        Set<GameModel> games = findGames(partnerDto.getGames(), partner);
        if (games.isEmpty()) {
            throw new RuntimeException("Nenhum jogo encontrado");
        }
        return games;
    }

    private Set<GameModel> findGames(Set<GameDto> games, PartnerModel partner) {
        Set<GameModel> listGame = new HashSet<>();
        for (GameDto gameDto : games) {
            Optional<GameModel> gameModel = this.gameService.findByNameAndConsole(gameDto.getName(), gameDto.getConsole());
            if (gameModel.isEmpty()) {
                this.gameService.save(gameDto);
            }
            if (gameModel.get().getPartnerModel()==null) {
                gameModel.get().setPartnerModel(partner);
                listGame.add(gameModel.get());
                partner.setGames(listGame);
                this.partnerRepository.saveAndFlush(partner);
            }else {
                throw new RuntimeException("Game com Dono");
            }
        }
        return listGame;
    }

    public Page<PartnerModel> findAllPartners(Pageable pageable) {
        return this.partnerRepository.findAll((org.springframework.data.domain.Pageable) pageable);
    }

    @Transactional
    public void deletePartner(Long id) {
        this.partnerRepository.deleteById(id);
    }

}
