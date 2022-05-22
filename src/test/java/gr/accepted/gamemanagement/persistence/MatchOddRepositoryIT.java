package gr.accepted.gamemanagement.persistence;

import gr.accepted.gamemanagement.GameManagementApplication;
import gr.accepted.gamemanagement.model.Match;
import gr.accepted.gamemanagement.model.MatchOdd;
import gr.accepted.gamemanagement.model.Sport;
import org.junit.Assert;
import org.junit.Before;
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
 * Integration test for {@link MatchOddRepository}
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GameManagementApplication.class)
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureMockMvc
public class MatchOddRepositoryIT {


    @Autowired
    MatchOddRepository matchOddRepository;

    @Autowired
    MatchRepository matchRepository;

    Match persistedMatch;

    @Before
    public void beforeTest(){
        Match match = generateMatch();
        persistedMatch = matchRepository.save(match);
    }

    @Test
    public void testSave(){
        MatchOdd odd= generateMatchOdd();
        odd.setMatch(persistedMatch);
        persistedMatch.setMatchOdds(new HashSet<>(List.of(odd)));
        MatchOdd persistedMatchOdd = matchOddRepository.save(odd);
        Assert.assertNotNull(persistedMatchOdd.getId());
        matchRepository.deleteById(persistedMatch.getId());
    }

    @Test
    public void testUpdate(){
        MatchOdd odd= generateMatchOdd();
        odd.setMatch(persistedMatch);
        persistedMatch.setMatchOdds(new HashSet<>(List.of(odd)));
        MatchOdd persistedMatchOdd = matchOddRepository.save(odd);
        persistedMatchOdd.setOdd(3.5);
        persistedMatchOdd = matchOddRepository.save(persistedMatchOdd);
        Assert.assertNotNull(persistedMatchOdd.getId());
        Assert.assertEquals(3.5d, persistedMatchOdd.getOdd(), 0);
        matchRepository.deleteById(persistedMatch.getId());
    }

    @Test
    public void testDelete(){
        MatchOdd odd= generateMatchOdd();
        odd.setMatch(persistedMatch);
        MatchOdd persistedMatchOdd = matchOddRepository.save(odd);
        matchOddRepository.delete(persistedMatchOdd.getId());
        Optional<MatchOdd> matchOddOptional = matchOddRepository.findById(persistedMatchOdd.getId());
        Assert.assertFalse(matchOddOptional.isPresent());
        matchRepository.deleteById(persistedMatch.getId());
    }

    @Test
    public void testFind(){
        MatchOdd odd= generateMatchOdd();
        odd.setMatch(persistedMatch);
        MatchOdd persistedMatchOdd = matchOddRepository.save(odd);
        Optional<MatchOdd> matchOddOptional = matchOddRepository.findById(persistedMatchOdd.getId());
        Assert.assertTrue(matchOddOptional.isPresent());
        matchRepository.deleteById(persistedMatch.getId());
    }

    private Match generateMatch() {
        Match match = new Match();
        match.setDescription("super final");
        match.setDate(LocalDate.of(2022, 5, 30));
        match.setTeamA("PAO");
        match.setTeamB("OSFP");
        match.setTime(LocalTime.of(21, 0));
        match.setSport(Sport.BASKETBALL);
        return match;
    }

    private MatchOdd generateMatchOdd(){
        MatchOdd matchOdd = new MatchOdd();
        matchOdd.setOdd(5.0);
        matchOdd.setSpecifier("X");
        return matchOdd;
    }
}
