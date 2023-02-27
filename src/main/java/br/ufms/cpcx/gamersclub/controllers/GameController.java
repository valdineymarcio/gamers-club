package br.ufms.cpcx.gamersclub.controllers;

import br.ufms.cpcx.gamersclub.dtos.GameDto;
import br.ufms.cpcx.gamersclub.models.GameModel;
import br.ufms.cpcx.gamersclub.services.GameService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v2/game")
public class GameController {
    final GameService gameService;
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }
    @PostMapping
    public ResponseEntity<Object> saveGame(@RequestBody @Valid GameDto gameDto){
        if(gameService.existsByNameAndConsoleAndOwner(gameDto.getName(), gameDto.getConsole(), gameDto.getOwner())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: This game already exists!");
        }
        var gameModel = new GameModel();
        BeanUtils.copyProperties(gameDto, gameModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.save(gameModel));
    }
    @GetMapping ("/{id}")
    public ResponseEntity<Object> getOneGame(@PathVariable (value = "id") Long id){
        Optional<GameModel> gameModelOptional = gameService .findById(id) ;
        if (!gameModelOptional.isPresent()) {
            return ResponseEntity. status(HttpStatus. NOT_FOUND).body("Game not found." );
        }
        return ResponseEntity. status(HttpStatus. OK).body(gameModelOptional.get()) ;
    }
    @PutMapping ("/{id}")
    public ResponseEntity<Object> updateGame(@PathVariable (value = "id") Long id,
                                             @RequestBody @Valid GameDto gameDto){
        Optional<GameModel> gameModelOptional = gameService .findById(id) ;
        if (!gameModelOptional.isPresent()) {
            return ResponseEntity. status(HttpStatus. NOT_FOUND).body("Game not found." );
        }
        var gameModel = new GameModel() ;
        BeanUtils.copyProperties(gameDto, gameModel);
        gameModel.setId(gameModelOptional.get().getId()) ;
        return ResponseEntity. status(HttpStatus. OK).body(gameService .save(gameModel)) ;
    }
    @DeleteMapping ("/{id}")
    public ResponseEntity<Object> deleteGame(@PathVariable (value = "id") Long id){
        Optional<GameModel> gameModelOptional = gameService .findById(id) ;
        if (!gameModelOptional.isPresent()) {
            return ResponseEntity. status(HttpStatus. NOT_FOUND).body("Game not found." );
        }
        gameService .delete(gameModelOptional.get()) ;
        return ResponseEntity. status(HttpStatus. OK).body("Game deleted successfully." );
    }
    @GetMapping
    public ResponseEntity<Page<GameModel>> getAllGames(
            @PageableDefault(page = 0, size = 10, sort = "id",
                    direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(gameService.findAll(pageable));
    }

}
