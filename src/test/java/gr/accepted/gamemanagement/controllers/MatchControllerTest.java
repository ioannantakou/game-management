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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test for {@link MatchController}
 */
@RunWith(MockitoJUnitRunner.class)
public class MatchControllerTest {

    private MatchController matchController;

    ModelMapper mapper;

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private MatchOddRepository matchOddRepository;

    @Before
    public void setup() {
        matchRepository = Mockito.mock(MatchRepository.class);
        matchOddRepository = Mockito.mock(MatchOddRepository.class);
        mapper = new ModelMapper();
        matchController = new MatchController(matchRepository, matchOddRepository, mapper);
    }


    @Test
    public void testFindMatchFound(){
        Match match = generateMatch();
        Mockito.when(matchRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(match));
        ResponseEntity<MatchResponseDTO> matchResponseDTO = matchController.findMatch(1L);
        verify(matchRepository, times(1)).findById(Mockito.anyLong());
        Assert.assertNotNull(matchResponseDTO);
        Assert.assertNotNull(matchResponseDTO.getBody());
        Assert.assertEquals(HttpStatus.OK, matchResponseDTO.getStatusCode());
        Assert.assertEquals(match.getId(), matchResponseDTO.getBody().getId());
        //TODO assert all fields
    }

    @Test
    public void testFindMatchNotFound(){
        Mockito.when(matchRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        ResponseEntity<MatchResponseDTO> matchResponseDTO = matchController.findMatch(1L);
        verify(matchRepository, times(1)).findById(Mockito.anyLong());
        Assert.assertEquals(HttpStatus.NOT_FOUND, matchResponseDTO.getStatusCode());
    }

    @Test
    public void testCreateMatch(){
        MatchInputDTO matchInputDTO = generateMatchInputDTO();
        Match match = generateMatch();
        Mockito.when(matchRepository.save(Mockito.any(Match.class))).thenReturn(match);

        ResponseEntity<MatchResponseDTO> matchResponseDTOResponseEntity = matchController.createMatch(matchInputDTO);

        verify(matchRepository, times(1)).save(Mockito.any(Match.class));
        Assert.assertNotNull(matchResponseDTOResponseEntity);
        Assert.assertNotNull(matchResponseDTOResponseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, matchResponseDTOResponseEntity.getStatusCode());
        Assert.assertEquals(match.getId(), matchResponseDTOResponseEntity.getBody().getId());
    }

    @Test
    public void testCreateMatchFailure(){
        MatchInputDTO matchInputDTO = generateMatchInputDTO();
        Mockito.when(matchRepository.save(Mockito.any(Match.class))).thenThrow(new RuntimeException());

        ResponseEntity<MatchResponseDTO> matchResponseDTOResponseEntity = matchController.createMatch(matchInputDTO);

        Assert.assertNotNull(matchResponseDTOResponseEntity);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, matchResponseDTOResponseEntity.getStatusCode());
    }

    @Test
    public void testUpdateMatchDetails(){
        MatchInputDTO matchInputDTO = generateMatchInputDTO();
        Match match = generateMatch();
        Match updatedMatch = generateUpdatedMatch();
        Mockito.when(matchRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(match));
        Mockito.when(matchRepository.save(Mockito.any(Match.class))).thenReturn(updatedMatch);

        ResponseEntity<MatchResponseDTO> matchResponseDTOResponseEntity = matchController.updateMatchDetails(1L, matchInputDTO);

        Assert.assertNotNull(matchResponseDTOResponseEntity);
        Assert.assertNotNull(matchResponseDTOResponseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, matchResponseDTOResponseEntity.getStatusCode());
        Assert.assertEquals(updatedMatch.getId(), matchResponseDTOResponseEntity.getBody().getId());
        Assert.assertEquals(updatedMatch.getTeamB(), matchResponseDTOResponseEntity.getBody().getTeamB());
    }

    @Test
    public void testUpdateMatchDetailsNotFound() {
        MatchInputDTO matchInputDTO = generateMatchInputDTO();
        Mockito.when(matchRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        ResponseEntity<MatchResponseDTO> matchResponseDTOResponseEntity = matchController.updateMatchDetails(1L, matchInputDTO);

        Assert.assertNotNull(matchResponseDTOResponseEntity);
        Assert.assertEquals(HttpStatus.NOT_FOUND, matchResponseDTOResponseEntity.getStatusCode());
    }

    @Test
    public void testDelete(){
        Mockito.doNothing().when(matchRepository).deleteById(Mockito.anyLong());
        ResponseEntity<HttpStatus> responseEntity = matchController.deleteMatch(1L);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteFailure(){
        Mockito.doThrow(new RuntimeException()).when(matchRepository).deleteById(Mockito.anyLong());
        ResponseEntity<HttpStatus> responseEntity = matchController.deleteMatch(1L);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateMatchOdd(){
        MatchOddInputDTO matchOddInputDTO = generateMatchOddInputDTO();
        Match match = generateMatch();
        MatchOdd matchOdd = generateMatchOdd();
        matchOdd.setMatch(match);
        Mockito.when(matchRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(match));
        Mockito.when(matchOddRepository.save(Mockito.any(MatchOdd.class))).thenReturn(matchOdd);

        ResponseEntity<MatchOddResponseDTO> responseEntity = matchController.createMatchOdd(1L, matchOddInputDTO);

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(matchOdd.getId(), responseEntity.getBody().getId());

    }

    @Test
    public void testCreateMatchOddNotFound(){
        MatchOddInputDTO matchOddInputDTO = generateMatchOddInputDTO();
        Mockito.when(matchRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        ResponseEntity<MatchOddResponseDTO> responseEntity = matchController.createMatchOdd(1L, matchOddInputDTO);

        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

    }

    @Test
    public void testDeleteMatchOdd(){
        Mockito.doNothing().when(matchOddRepository).deleteById(Mockito.anyLong());
        ResponseEntity<HttpStatus> responseEntity = matchController.deleteMatchOdd(1L);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteMatchOddFailure(){
        Mockito.doThrow(new RuntimeException()).when(matchOddRepository).deleteById(Mockito.anyLong());
        ResponseEntity<HttpStatus> responseEntity = matchController.deleteMatchOdd(1L);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    private Match generateMatch() {
        Match match = new Match();
        match.setId(1L);
        match.setDescription("super final");
        match.setDate(LocalDate.of(2022, 5, 30));
        match.setTeamA("PAO");
        match.setTeamB("OSFP");
        match.setTime(LocalTime.of(21, 0));
        match.setSport(Sport.BASKETBALL);
        MatchOdd matchOdd = new MatchOdd();
        matchOdd.setId(1L);
        matchOdd.setMatch(match);
        matchOdd.setOdd(5.0);
        matchOdd.setSpecifier("X");
        match.setMatchOdds(new HashSet<>(List.of(matchOdd)));
        return match;
    }

    private MatchInputDTO generateMatchInputDTO(){
        MatchInputDTO matchInputDTO = new MatchInputDTO();
        matchInputDTO.setDescription("super final");
        matchInputDTO.setDate(LocalDate.of(2022, 5, 30));
        matchInputDTO.setTeamA("PAO");
        matchInputDTO.setTeamB("OSFP");
        matchInputDTO.setTime(LocalTime.of(21, 0));
        matchInputDTO.setSport("BASKETBALL");
        return matchInputDTO;
    }

    private MatchInputDTO generateInvalidMatchInputDTO(){
        MatchInputDTO matchInputDTO = new MatchInputDTO();
        matchInputDTO.setDescription("super final");
        matchInputDTO.setDate(LocalDate.of(2022, 5, 30));
        matchInputDTO.setTeamA("PAO");
        matchInputDTO.setTeamB("OSFP");
        matchInputDTO.setTime(LocalTime.of(21, 0));
        matchInputDTO.setSport("TENNIS");
        return matchInputDTO;
    }

    private Match generateUpdatedMatch() {
        Match match = new Match();
        match.setId(1L);
        match.setDescription("super final");
        match.setDate(LocalDate.of(2022, 5, 30));
        match.setTeamA("PAO");
        match.setTeamB("PAOK");
        match.setTime(LocalTime.of(21, 0));
        match.setSport(Sport.BASKETBALL);
        MatchOdd matchOdd = new MatchOdd();
        matchOdd.setId(1L);
        matchOdd.setMatch(match);
        matchOdd.setOdd(5.0);
        matchOdd.setSpecifier("X");
        match.setMatchOdds(new HashSet<>(List.of(matchOdd)));
        return match;
    }

    private MatchOddInputDTO generateMatchOddInputDTO(){
        MatchOddInputDTO matchOddInputDTO = new MatchOddInputDTO();
        matchOddInputDTO.setOdd(2.5);
        matchOddInputDTO.setSpecifier("X");
        return matchOddInputDTO;
    }

    private MatchOdd generateMatchOdd(){
        MatchOdd matchOdd = new MatchOdd();
        matchOdd.setId(1L);
        matchOdd.setOdd(5.0);
        matchOdd.setSpecifier("X");
        return matchOdd;
    }

}
