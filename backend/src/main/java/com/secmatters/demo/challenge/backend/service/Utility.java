package com.secmatters.demo.challenge.backend.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import org.apache.ddlutils.DatabaseOperationException;
import org.apache.ddlutils.Platform;
import org.apache.ddlutils.io.DatabaseIO;
import org.apache.ddlutils.io.DdlUtilsXMLException;
import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.platform.derby.DerbyPlatform;
import org.apache.ddlutils.task.WriteDataToDatabaseCommand;
import org.apache.tools.ant.BuildException;
import sun.misc.IOUtils;

public class Utility {

    private static final String DB_NAME = "sample";

    private static Database parseDatabaseFromString(String dbDef) throws DdlUtilsXMLException
    {
        DatabaseIO dbIO = new DatabaseIO();

//        dbIO.setUseInternalDtd(true);
        dbIO.setValidateXml(true);
        return dbIO.read(new StringReader(dbDef));
    }

    public static void importDatabase(IChallengeDAO challengeDAO) throws IOException {

       // This should be the proper way to create the platform, but once
       // the subTask asks for a connection it would fail
//        Map<String, Object> properties = factory.getProperties();
//        final Platform platform = PlatformFactory.createNewPlatformInstance(
//                (String)properties.get("javax.persistence.jdbc.driver"),
//                (String)properties.get("javax.persistence.jdbc.url")
//        );
        // Setting up a datasource for just passing it to the platform is tricky
        // However this shortcut works without using any datasource
        Platform platform = new DerbyPlatform() {
            @Override
            public Connection borrowConnection() throws DatabaseOperationException {
                return challengeDAO.getConnection();
            }
        };

        // Create the schema

        //String schemaPath = Utility.class.getResource("/db-schema.xml").getPath();
        String schemaXml;
        try (InputStream is = Utility.class.getResourceAsStream("/db-schema.xml")) {
            schemaXml = new String(IOUtils.readFully(is, -1, true));
        }
        Database model = parseDatabaseFromString(schemaXml);
        platform.setSqlCommentsOn(false);
        platform.createModel(model, false, false);

        // Inject data. Jetty cannot correctly resolve a file path located
        // in a jar. For this reason the file content is copied into a
        // temporary file that is later passed to the task as it wants.

        Path tmpFilePath = null;
        WriteDataToDatabaseCommand subTask = null;
        File tmpFile = null;
        try {
            tmpFilePath = Files.createTempFile("data-xml", ".tmp");
            try (InputStream is = Utility.class.getResourceAsStream("/data.xml")) {
                Files.copy(is, tmpFilePath, StandardCopyOption.REPLACE_EXISTING);
            }

            subTask = new WriteDataToDatabaseCommand() {
                @Override
                protected Platform getPlatform() throws BuildException {
                    // Avoid any check
                    return platform;
                }
            };
            subTask.setDataFile(tmpFilePath.toFile());
            subTask.setBatchSize(100);
            subTask.setFailOnError(true);
            subTask.setUseBatchMode(true);
            subTask.setEnsureForeignKeyOrder(true);
            subTask.execute(null, platform.readModelFromDatabase(challengeDAO.getConnection(), DB_NAME));
        } finally {
        	if (tmpFile != null) {
        		tmpFile.deleteOnExit();
        	}
        }
    }
}
