package configurations;

//import com.browserstack.local.Local;
import io.restassured.authentication.BasicAuthScheme;
import org.hamcrest.Matchers;
import org.openqa.selenium.html5.LocalStorage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.HashMap;
import java.util.Map;

public class StackHelper {

//    static Local bsLocal = new Local();
    static Map<String, String> bsLocalArgs = new HashMap<>();

    static {
        bsLocalArgs.put("key", Config.STACK_KEY);
    }

    public static void startInstance(){
        try {
//            bsLocal.start(bsLocalArgs);
//            System.out.println(bsLocal);
            System.out.println(bsLocalArgs);
//            System.out.println("?????? Is running "+bsLocal.isRunning());
        }
        catch (Exception e){
            System.out.println("problems with tunnelish thing");
        }
    }

    public static void stopInstance() {
        try {
//            bsLocal.stop(bsLocalArgs);
        }
        catch (Exception e){
            System.out.println("problems with stopping tunnelish thing");
        }    }

}
