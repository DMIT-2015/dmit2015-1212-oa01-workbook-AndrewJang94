package ca.nait.dmit.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlbertaCovid19CaseManagerTest {

    AlbertaCovid19CaseManager caseManager;

    @BeforeEach
    void beforeEach() throws IOException {
        caseManager = AlbertaCovid19CaseManager.getInstance();
    }

    @Test
    void getAlbertaCovid19CaseList() throws IOException {
//        AlbertaCovid19CaseManager caseManager = new AlbertaCovid19CaseManager();
        AlbertaCovid19CaseManager caseManager = AlbertaCovid19CaseManager.getInstance();
        assertEquals(484400, caseManager.getAlbertaCovid19CaseList().size());
    }
    @Test
    void activeCases() throws IOException {
        AlbertaCovid19CaseManager caseManager = AlbertaCovid19CaseManager.getInstance();
        assertEquals(44_301, caseManager.countTotalActiveCases());
    }

    @Test
    void activeCasesByZone() throws IOException {
        assertEquals(20_779, caseManager.countActiveCases("Calgary Zone"));
        assertEquals(14_219, caseManager.countActiveCases("Edmonton Zone"));
    }

    @Test
    void distinctAhsZone() throws IOException {
        List<String> ahsZoneList = caseManager.distinctAhsZone();
        ahsZoneList.forEach(System.out::println);
        assertEquals(6, ahsZoneList.size());

    }



}