package br.ufms.cpcx.gamersclub.controllers;

import br.ufms.cpcx.gamersclub.dtos.PartnerDto;
import br.ufms.cpcx.gamersclub.models.GameModel;
import br.ufms.cpcx.gamersclub.models.PartnerModel;
import br.ufms.cpcx.gamersclub.services.GameService;
import br.ufms.cpcx.gamersclub.services.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.Set;

@RestController
@RequestMapping("v2/partner")
public class PartnerController {

    private final PartnerService partnerService;
    private final GameService gameService;

    @Autowired
    public PartnerController(PartnerService partnerService, GameService gameService) {
        this.partnerService = partnerService;
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<Set<GameModel>> newPartner(@RequestBody PartnerDto partnerDTO) {
        try {
            Set<GameModel> games = this.partnerService.newPartner(partnerDTO);
            return ResponseEntity.ok(games);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<PartnerModel>> findAllPartners(Pageable pageable) {
        try {
            Page<PartnerModel> partners = this.partnerService.findAllPartners(pageable);
            return ResponseEntity.ok(partners);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<Set<GameModel>> findGameByPartnerId(@PathVariable Long id) {
        try {
            Set<GameModel> gameByPartnerId = this.gameService.findGameByPartnerId(id);
            return ResponseEntity.ok(gameByPartnerId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id){
        try {
            this.partnerService.deletePartner(id);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
