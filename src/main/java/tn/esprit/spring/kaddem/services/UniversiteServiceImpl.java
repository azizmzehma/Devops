package tn.esprit.spring.kaddem.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;

import java.util.List;
import java.util.Set;

@Service
public class UniversiteServiceImpl implements IUniversiteService {

    private static final Logger logger = LogManager.getLogger(UniversiteServiceImpl.class);

    @Autowired
    UniversiteRepository universiteRepository;

    @Autowired
    DepartementRepository departementRepository;

    public UniversiteServiceImpl() {
        logger.debug("UniversiteServiceImpl instantiated.");
    }

    public List<Universite> retrieveAllUniversites() {
        logger.debug("Retrieving all universities.");
        return (List<Universite>) universiteRepository.findAll();
    }

    public Universite addUniversite(Universite u) {
        try {
            logger.debug("Adding university: {}", u);
            return universiteRepository.save(u);
        } catch (Exception e) {
            logger.error("Error adding university: ", e);
            return null;
        }
    }

    public Universite updateUniversite(Universite u) {
        try {
            logger.debug("Updating university: {}", u);
            return universiteRepository.save(u);
        } catch (Exception e) {
            logger.error("Error updating university: ", e);
            return null;
        }
    }

    public Universite retrieveUniversite(Integer idUniversite) {
        logger.debug("Retrieving university with ID: {}", idUniversite);
        return universiteRepository.findById(idUniversite)
                .orElseThrow(() -> {
                    logger.error("University with ID {} not found", idUniversite);
                    return new RuntimeException("University not found");
                });
    }

    public void deleteUniversite(Integer idUniversite) {
        try {
            logger.debug("Deleting university with ID: {}", idUniversite);
            universiteRepository.delete(retrieveUniversite(idUniversite));
        } catch (Exception e) {
            logger.error("Error deleting university with ID: {}", idUniversite, e);
        }
    }

    public void assignUniversiteToDepartement(Integer idUniversite, Integer idDepartement) {
        try {
            logger.debug("Assigning department with ID {} to university with ID {}", idDepartement, idUniversite);
            Universite u = universiteRepository.findById(idUniversite).orElse(null);
            Departement d = departementRepository.findById(idDepartement).orElse(null);
            if (u != null && d != null) {
                u.getDepartements().add(d);
                universiteRepository.save(u);
                logger.debug("Department assigned successfully.");
            } else {
                logger.error("University or Department not found.");
            }
        } catch (Exception e) {
            logger.error("Error assigning department to university: ", e);
        }
    }

    public Set<Departement> retrieveDepartementsByUniversite(Integer idUniversite) {
        logger.debug("Retrieving departments for university with ID: {}", idUniversite);
        Universite u = universiteRepository.findById(idUniversite).orElse(null);
        if (u != null) {
            return u.getDepartements();
        } else {
            logger.error("University with ID {} not found.", idUniversite);
            return null;
        }
    }
}
