package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;
import tn.esprit.spring.kaddem.services.UniversiteServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UniversiteServiceImplTest {

    @Mock
    UniversiteRepository universiteRepository;

    @InjectMocks
    UniversiteServiceImpl universiteService;

    private Universite universite;

    @BeforeEach
    void setUp() {
        universite = new Universite();
        universite.setIdUniv(1);
        universite.setNomUniv("Université de Tunis");
    }

    @Test
    void retrieveAllUniversitesTest() {
        // Create a list of universites
        List<Universite> universitesList = new ArrayList<>();
        universitesList.add(universite);

        // Mock the behavior of universiteRepository.findAll()
        when(universiteRepository.findAll()).thenReturn(universitesList);

        // Call the method under test
        List<Universite> retrievedUniversitesList = universiteService.retrieveAllUniversites();

        // Use assertThat to verify the result
        assertThat(retrievedUniversitesList).hasSize(1);
        verify(universiteRepository).findAll();
    }

    @Test
    void addUniversiteTest() {
        // Mock the behavior of universiteRepository.save()
        when(universiteRepository.save(Mockito.any(Universite.class))).thenReturn(universite);

        // Call the method under test
        Universite savedUniversite = universiteService.addUniversite(universite);

        // Assertions
        assertThat(savedUniversite).isNotNull();
        verify(universiteRepository).save(Mockito.any(Universite.class));
    }

    @Test
    void retrieveUniversiteByIdTest() {
        Integer universiteId = 1;

        // Mock the behavior of universiteRepository.findById()
        when(universiteRepository.findById(universiteId)).thenReturn(Optional.of(universite));

        // Call the method under test
        Universite retrievedUniversite = universiteService.retrieveUniversite(universiteId);

        // Assertions
        assertThat(retrievedUniversite).isNotNull();
        verify(universiteRepository).findById(universiteId);
    }

    // @Test
    // void deleteUniversiteTest() {
    //  Integer universiteId = 1;

    // Mock the behavior of universiteRepository.deleteById()
    // doNothing().when(universiteRepository).deleteById(universiteId);

    // Call the method under test
    // universiteService.deleteUniversite(universiteId);

    // Verify that deleteById was called once
    // verify(universiteRepository, times(1)).deleteById(universiteId);
    // }
}