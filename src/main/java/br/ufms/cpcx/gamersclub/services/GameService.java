package br.ufms.cpcx.gamersclub.services;

import br.ufms.cpcx.gamersclub.dtos.GameDto;
import br.ufms.cpcx.gamersclub.models.ConsoleEnum;
import br.ufms.cpcx.gamersclub.models.GameModel;
import br.ufms.cpcx.gamersclub.repositories.GameRepository;
import br.ufms.cpcx.gamersclub.repositories.PartnerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Optional;
import java.util.Set;

@Service
public class GameService {
    final GameRepository gameRepository;
    final PartnerRepository partnerRepository;

    @Autowired
    public GameService(GameRepository gameRepository, PartnerRepository partnerRepository) {
        this.gameRepository = gameRepository;
        this.partnerRepository = partnerRepository;
    }

    @Transactional
    public GameModel save(GameDto gameDto){
        GameModel save= new GameModel();
        BeanUtils.copyProperties(gameDto, save);
        this.gameRepository.save(save);
        return this.gameRepository.save(save);
    }
    @Transactional
    public void save(GameDto ... gameDto){
        for(GameDto newSave:gameDto){
            GameModel save= new GameModel();
            BeanUtils.copyProperties(newSave, save);
            this.gameRepository.save(save);
        }
    }

//    @Transactional
//    public GameModel save(GameModel gameModel) {
//        return gameRepository.save(gameModel);
//    }

    @Transactional
    public void delete(GameModel gameModel) {
        gameRepository.delete(gameModel);
    }

    public Page<GameModel> findAll(Pageable pageable) {
        return gameRepository.findAll((org.springframework.data.domain.Pageable) pageable);
    }

    public Optional<GameModel> findById(Long id) {
        return gameRepository.findById(id);
    }

    public boolean existsByNameAndConsoleAndOwner(String name, ConsoleEnum consoleEnum, String owner) {
        return gameRepository.existsByNameAndConsoleAndOwner(name, consoleEnum, owner);
    }

    public Set<GameModel> findGameByPartnerId(Long id) {
        return this.gameRepository.findAllByPartnerId(id);

    }

    public Optional<GameModel> findByNameAndConsole(String name, ConsoleEnum console) {
        return this.gameRepository.findByNameAndConsole(name, console);
    }


}
