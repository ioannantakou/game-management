package gr.accepted.gamemanagement.persistence;

import gr.accepted.gamemanagement.GameManagementApplication;
import gr.accepted.gamemanagement.model.Match;
import gr.accepted.gamemanagement.model.MatchOdd;
import gr.accepted.gamemanagement.model.Sport;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * Integration test for {@link MatchRepository}
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GameManagementApplication.class)
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureMockMvc
public class MatchRepositoryIT {

    @Autowired
    MatchRepository matchRepository;

    @Test
    public void testSave() {
        Match match = generateMatch();

        Match persistedMatch = matchRepository.save(match);

        Assert.assertNotNull(persistedMatch.getId());
        Assert.assertEquals(match.getDescription(), persistedMatch.getDescription());
        Assert.assertEquals(match.getDate(), persistedMatch.getDate());
        Assert.assertEquals(match.getSport(), persistedMatch.getSport());
        Assert.assertEquals(match.getTeamA(), persistedMatch.getTeamA());
        Assert.assertEquals(match.getTeamB(), persistedMatch.getTeamB());
        Assert.assertEquals(match.getTime(), persistedMatch.getTime());
        Assert.assertEquals(1, persistedMatch.getMatchOdds().size());

        Optional<MatchOdd> matchOdd = match.getMatchOdds().stream().findFirst();
        Optional<MatchOdd> persistedMatchOdd = persistedMatch.getMatchOdds().stream().findFirst();
        Assert.assertTrue(persistedMatchOdd.isPresent());
        Assert.assertTrue(matchOdd.isPresent());
        Assert.assertNotNull(persistedMatchOdd.get().getId());
        Assert.assertEquals(matchOdd.get().getSpecifier(), persistedMatchOdd.get().getSpecifier());

        matchRepository.deleteById(persistedMatch.getId());
    }

    @Test
    public void findById() {
        Match match = generateMatch();
        Match persistedMatch = matchRepository.save(match);

        Optional<Match> matchFound = matchRepository.findById(persistedMatch.getId());

        Assert.assertTrue(matchFound.isPresent());
        Assert.assertNotNull(matchFound.get().getId());
        Assert.assertEquals(match.getDescription(), matchFound.get().getDescription());
        Assert.assertEquals(match.getDate(), matchFound.get().getDate());
        Assert.assertEquals(match.getSport(), matchFound.get().getSport());
        Assert.assertEquals(match.getTeamA(), matchFound.get().getTeamA());
        Assert.assertEquals(match.getTeamB(), matchFound.get().getTeamB());
        Assert.assertEquals(match.getTime(), matchFound.get().getTime());
        Assert.assertEquals(1, matchFound.get().getMatchOdds().size());

        Optional<MatchOdd> matchOdd = match.getMatchOdds().stream().findFirst();
        Optional<MatchOdd> matchOddFound = matchFound.get().getMatchOdds().stream().findFirst();
        Assert.assertTrue(matchOddFound.isPresent());
        Assert.assertTrue(matchOdd.isPresent());
        Assert.assertNotNull(matchOddFound.get().getId());
        Assert.assertEquals(matchOdd.get().getSpecifier(), matchOddFound.get().getSpecifier());

        matchRepository.deleteById(matchFound.get().getId());

    }

    @Test
    public void testDelete() {
        Match match = generateMatch();
        Match persistedMatch = matchRepository.save(match);

        matchRepository.deleteById(persistedMatch.getId());

        Optional<Match> matchFound = matchRepository.findById(persistedMatch.getId());
        Assert.assertFalse(matchFound.isPresent());
    }

    @Test
    public void testUpdate() {
        Match match = generateMatch();

        Match newMatch = new Match();
        newMatch.setDescription("new description");
        newMatch.setSport(Sport.FOOTBALL);
        newMatch.setTeamB("PAOK");
        newMatch.setTeamA("ARIS");
        newMatch.setDate(LocalDate.of(2022, 6, 1));
        newMatch.setTime(LocalTime.of(23,0));

        match.setDescription(newMatch.getDescription());
        match.setSport(newMatch.getSport());
        match.setTeamB(newMatch.getTeamB());
        match.setTeamA(newMatch.getTeamA());
        match.setDate(newMatch.getDate());
        match.setTime(newMatch.getTime());

        Match matchUpdated = matchRepository.save(match);

        Assert.assertEquals(newMatch.getDescription(), matchUpdated.getDescription());
        Assert.assertEquals(newMatch.getDate(), matchUpdated.getDate());
        Assert.assertEquals(newMatch.getSport(), matchUpdated.getSport());
        Assert.assertEquals(newMatch.getTeamA(), matchUpdated.getTeamA());
        Assert.assertEquals(newMatch.getTeamB(), matchUpdated.getTeamB());
        Assert.assertEquals(newMatch.getTime(), matchUpdated.getTime());

        matchRepository.deleteById(matchUpdated.getId());

    }


    private Match generateMatch() {
        Match match = new Match();
        match.setDescription("super final");
        match.setDate(LocalDate.of(2022, 5, 30));
        match.setTeamA("PAO");
        match.setTeamB("OSFP");
        match.setTime(LocalTime.of(21, 0));
        match.setSport(Sport.BASKETBALL);
        MatchOdd matchOdd = new MatchOdd();
        matchOdd.setMatch(match);
        matchOdd.setOdd(5.0);
        matchOdd.setSpecifier("X");
        match.setMatchOdds(new HashSet<>(List.of(matchOdd)));
        return match;
    }

}
