package gr.accepted.gamemanagement.controllers;

import gr.accepted.gamemanagement.dto.MatchInputDTO;
import gr.accepted.gamemanagement.dto.MatchOddInputDTO;
import gr.accepted.gamemanagement.dto.MatchOddResponseDTO;
import gr.accepted.gamemanagement.dto.MatchResponseDTO;
import gr.accepted.gamemanagement.model.Match;
import gr.accepted.gamemanagement.model.MatchOdd;
import gr.accepted.gamemanagement.model.Sport;
import gr.accepted.gamemanagement.persistence.MatchOddRepository;
import gr.accepted.gamemanagement.persistence.MatchRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Controller for
 * game management Rest API.
 */
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("match")
public class MatchController {

    private final MatchRepository matchRepository;

    private final MatchOddRepository matchOddRepository;

    private final ModelMapper mapper;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MatchResponseDTO> findMatch(@PathVariable Long id) {
        Optional<Match> match = matchRepository.findById(id);
        return match.map(value -> new ResponseEntity<>(mapper.map(match.get(), MatchResponseDTO.class), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MatchResponseDTO> createMatch(@Valid @RequestBody MatchInputDTO matchInputDto){
        Match match = new Match();
        match.setTime(matchInputDto.getTime());
        match.setDate(matchInputDto.getDate());
        match.setDescription(matchInputDto.getDescription());
        match.setSport(Sport.valueOf(matchInputDto.getSport()));
        match.setTeamA(matchInputDto.getTeamA());
        match.setTeamB(matchInputDto.getTeamB());
        try {
            Match matchPersisted = matchRepository.save(match);
            return new ResponseEntity<>(mapper.map(matchPersisted, MatchResponseDTO.class), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchResponseDTO> updateMatchDetails(@PathVariable("id") Long id, @Valid @RequestBody MatchInputDTO matchInputDto) {
        Optional<Match> matchData = matchRepository.findById(id);
        if (matchData.isPresent()) {
            Match newMatch = matchData.get();
            newMatch.setDate(matchInputDto.getDate());
            newMatch.setDescription(matchInputDto.getDescription());
            newMatch.setTeamA(matchInputDto.getTeamA());
            newMatch.setTeamB(matchInputDto.getTeamB());
            newMatch.setSport(Sport.valueOf(matchInputDto.getSport()));
            newMatch.setTime(matchInputDto.getTime());
            Match matchPersisted = matchRepository.save(newMatch);
            return new ResponseEntity<>(mapper.map(matchPersisted, MatchResponseDTO.class), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMatch(@PathVariable("id") Long id) {
        try {
            matchRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/odd/{id}")
    public ResponseEntity<MatchOddResponseDTO> createMatchOdd(@PathVariable("id") Long id, @RequestBody MatchOddInputDTO matchOddInputDto) {
        Optional<Match> matchData = matchRepository.findById(id);
        if (matchData.isPresent()) {
            MatchOdd newMatchOdd = new MatchOdd();
            newMatchOdd.setOdd(matchOddInputDto.getOdd());
            newMatchOdd.setSpecifier(matchOddInputDto.getSpecifier());
            newMatchOdd.setMatch(matchData.get());
            MatchOdd matchOdd = matchOddRepository.save(newMatchOdd);
            if(matchOdd != null && matchOdd.getId() != null){
                return new ResponseEntity<>(mapper.map(matchOdd, MatchOddResponseDTO.class), HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/odd/{id}")
    public ResponseEntity<MatchOddResponseDTO> updateMatchOddDetails(@PathVariable("id") Long id, @RequestBody MatchOddInputDTO matchOddInputDto) {
        Optional<MatchOdd> matchOddData = matchOddRepository.findById(id);
        if (matchOddData.isPresent()) {
            MatchOdd newMatchOdd = matchOddData.get();
            newMatchOdd.setOdd(matchOddInputDto.getOdd());
            newMatchOdd.setSpecifier(matchOddInputDto.getSpecifier());
            MatchOdd matchOdd = matchOddRepository.save(newMatchOdd);
            return new ResponseEntity<>(mapper.map(matchOdd, MatchOddResponseDTO.class), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/odd/{id}")
    public ResponseEntity<HttpStatus> deleteMatchOdd(@PathVariable("id") Long id) {
        try {
            matchOddRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
