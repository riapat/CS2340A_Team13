package com.example.cs2340a_team13;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.cs2340a_team13.model.User;

public class RiaPatelTests {
// Testing Singleton DatabaseAccess class
    @Test
    public void testSingletonProperty() {
        DatabaseAccess databaseAccess1 = DatabaseAccess.getInstance();
        DatabaseAccess databaseAccess2 = DatabaseAccess.getInstance();
        assertSame("Both instances should be the same",databaseAccess1, databaseAccess2);
    }

// Testing writeToUserDB method in DatabaseAccess class
    @Test

}
