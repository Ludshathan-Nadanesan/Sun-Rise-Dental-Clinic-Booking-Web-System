package com.sunrise.junittest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

import com.sunrise.config.DBConnection;

public class DBConnectionTest {
	@Test
    void testGetConnection() {

        try {
            Connection con = DBConnection.getConnection();

            assertNotNull(con);
            assertFalse(con.isClosed());

            con.close();

        } catch (Exception e) {
            fail("Database connection failed : " + e.getMessage());
        }

    }
}
