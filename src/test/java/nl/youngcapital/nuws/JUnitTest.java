
package nl.youngcapital.nuws;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class JUnitTest {
    
    public JUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testAdmin1(){
        Admin admin = new Admin();
        String testnaam = "Reindert Emmens";
        admin.setNaam(testnaam);
        assertEquals(admin.getNaam(), testnaam);  
    }
    
    @Test
    public void testAdmin2(){
        Admin admin = new Admin();
        String testpassword = "123456";
        admin.setPassword(testpassword);
        assertEquals(admin.getPassword(), testpassword);  
    }
}
