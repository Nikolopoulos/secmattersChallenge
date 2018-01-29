package com.secmatters.demo.challenge.backend.entity;

import com.secmatters.demo.challenge.backend.service.ChallengeDAO;
import com.secmatters.demo.challenge.backend.service.IChallengeDAO;
import com.secmatters.demo.challenge.backend.service.Utility;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;


public class EntityTestBase {

    private static final String PERSISTENCE_UNIT_NAME = "com.secmatters.demo.challenge.persistence_unit_embedded";

    protected static IChallengeDAO challengeDAO;

    public EntityTestBase() {
    }

    @BeforeClass
    public static void setUpClass() {
        challengeDAO = new ChallengeDAO(PERSISTENCE_UNIT_NAME);
        try {
            Utility.importDatabase(challengeDAO);
        } catch (IOException ex) {
            Logger.getLogger(EntityTestBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterClass
    public static void tearDownClass() {
        if (challengeDAO != null) {
            challengeDAO.close();
        }
    }
}
