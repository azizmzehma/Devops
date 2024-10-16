package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.services.DepartementServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartementServiceImplTest {

    @Mock
    DepartementRepository departementRepository;

    @InjectMocks
    DepartementServiceImpl departementService;

    private Departement departement;

    @BeforeEach
    void setUp() {
        departement = new Departement();
        departement.setIdDepart(1);
        departement.setNomDepart("Informatique");
    }

    @Test
    void retrieveAllDepartementsTest() {
        // Create a list of departements
        List<Departement> departementList = new ArrayList<>();
        departementList.add(departement);

        // Mock the behavior of departementRepository.findAll()
        when(departementRepository.findAll()).thenReturn(departementList);

        // Call the method under test
        List<Departement> retrievedDepartementList = departementService.retrieveAllDepartements();

        // Use assertThat to verify the result
        assertThat(retrievedDepartementList).hasSize(1);
        verify(departementRepository).findAll();
    }

    @Test
    void addDepartementTest() {
        // Mock the behavior of departementRepository.save()
        when(departementRepository.save(Mockito.any(Departement.class))).thenReturn(departement);

        // Call the method under test
        Departement savedDepartement = departementService.addDepartement(departement);

        // Assertions
        assertThat(savedDepartement).isNotNull();
        verify(departementRepository).save(Mockito.any(Departement.class));
    }

    @Test
    void retrieveDepartementByIdTest() {
        Integer departementId = 1;

        // Mock the behavior of departementRepository.findById()
        when(departementRepository.findById(departementId)).thenReturn(Optional.of(departement));

        // Call the method under test
        Departement retrievedDepartement = departementService.retrieveDepartement(departementId);

        // Assertions
        assertThat(retrievedDepartement).isNotNull();
        verify(departementRepository).findById(departementId);
    }

    // @Test
    //void deleteDepartementTest() {
    // Integer departementId = 1;

    // Mock the behavior of departementRepository.deleteById()
    // doNothing().when(departementRepository).deleteById(departementId);

    // Call the method under test
    //departementService.deleteDepartement(departementId);

    // Verify that deleteById was called once
    // verify(departementRepository, times(1)).deleteById(departementId);
    //}
}