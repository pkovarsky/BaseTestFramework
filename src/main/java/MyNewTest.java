
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class MyNewTest extends BaseWebDriverTest{


    @Test(groups = {"smoke","regression"},
            priority = 1,
            enabled = true,
            timeOut = 1000,
            dataProvider = "newProvider", threadPoolSize = 5)

    @DataProvider(name = "newProvider",parallel = true)
    public Object[][] newProvider(){
        return new Object[][]{
                {"Харьков"},
                {"Киев"},
                {"Львов"}

        };

    }

    @DataProvider(name = "passwordProvider",parallel = true)
    public Object[][] passwordProvider(){
        return new Object[][]{
                {"Харьков","JJJ"},
                {"Киев","HHH"},
                {"Львов","dfdf"}

        };

    }

    @Test(dataProvider = "passwordProvider")
    public void testJetBrainsLogin(String username, String password){

    }



}
