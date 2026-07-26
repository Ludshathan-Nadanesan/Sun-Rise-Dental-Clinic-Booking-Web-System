package com.sunrise.junittest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.sunrise.dao.UserDAO;
import com.sunrise.model.User;

class LoginTest {
	
	UserDAO obj = new UserDAO();

	@Test
    void testAuthenticateSuccess() {

        User user = obj.authenticate(
                "admin@gmail.com",
                "admin@1234"
        );

        assertNotNull(user);

        assertEquals(
                "admin@gmail.com",
                user.getEmail()
                
                
        );

        assertEquals(
                "admin",
                user.getRole()
        );

    }
	
	@Test
	void testWrongPassword() {
		
		User user = obj.authenticate(
	            "admin@gmail.com",
	            "wrongpassword"
	    );
	    assertNull(user);
	}
	
	@Test
	void testWrongEmail() {

	    User user = obj.authenticate(
	            "abc@gmail.com",
	            "admin@1234"
	    );
	    assertNull(user);
	}

//	@Test
//	void testInactiveUser() {
//	    User user = obj.authenticate(
//	            "admin@gmail.com",
//	            "admin@1234"
//	    );
//	    assertNull(user);
//
//	}
	
}
