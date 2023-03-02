package br.ufms.cpcx.gamersclub.controllers;

import br.ufms.cpcx.gamersclub.dtos.GameDto;
import br.ufms.cpcx.gamersclub.models.GameModel;
import br.ufms.cpcx.gamersclub.services.GameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("v2/game")
public class GameController {
    final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {

        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<Void> saveGame(@RequestBody @Valid GameDto... gameDto) {
        try {
            this.gameService.save(gameDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneGame(@PathVariable(value = "id") Long id) {
        Optional<GameModel> gameModelOptional = gameService.findById(id);
        if (!gameModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(gameModelOptional.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameModel> updateGame(@PathVariable(value = "id") Long id, @RequestBody @Valid GameDto gameDto) {
        try {
            Optional<GameModel> gameModelOptional = this.gameService.updateGame(id, gameDto);
            return gameModelOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }
//        Optional<GameModel> gameModelOptional = gameService .findById(id) ;
//        if (!gameModelOptional.isPresent()) {
//            return ResponseEntity. status(HttpStatus. NOT_FOUND).body("Game not found." );
//        }
//        var gameModel = new GameModel() ;
//        BeanUtils.copyProperties(gameDto, gameModel);
//        gameModel.setId(gameModelOptional.get().getId()) ;
//        return ResponseEntity. status(HttpStatus. OK).body(gameService .save(gameModel)) ;

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGame(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(this.gameService.deleteGameById(id).get());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
//        Optional<GameModel> gameModelOptional = gameService .findById(id) ;
//        if (!gameModelOptional.isPresent()) {
//            return ResponseEntity. status(HttpStatus. NOT_FOUND).body("Game not found." );
//        }
//        gameService .delete(gameModelOptional.get()) ;
//        return ResponseEntity. status(HttpStatus. OK).body("Game deleted successfully." );
    }
    @GetMapping
    public ResponseEntity<Page<GameModel>> findAllGames(Pageable pageable) {
        try {
            Page<GameModel> games = this.gameService.findAll(pageable);
            return ResponseEntity.ok(games);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<GameModel> findGameByid(@PathVariable Long id) {
        try {
            Optional<GameModel> game = this.gameService.findById(id);
            return game.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping
    public ResponseEntity<Page<GameModel>> getAllGames(
            @PageableDefault(page = 0, size = 10, sort = "id",
                    direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(gameService.findAll(pageable));
    }


}
