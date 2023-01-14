package uk.ac.ucl.shell.apps;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uk.ac.ucl.shell.app.AppFactory;


public class AppFactoryTest {
    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Test
    public void MakeShellAppInvalidAppNameTest() throws Exception {
        System.out.printf("App factory with invalid app name.....................");
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("invalidAppName: unknown application");
        System.out.println("OK");
        AppFactory af = new AppFactory();
        af.makeShellApp("invalidAppName");
    }
}
