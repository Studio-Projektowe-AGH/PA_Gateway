package controllers;

import org.junit.BeforeClass;
import org.junit.Test;
import play.mvc.Result;
import play.test.FakeRequest;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

public class ProfileServiceConnectorTest {

    static play.test.FakeApplication application;

    @BeforeClass
    public static void setUpClass() {
        application = fakeApplication();
    }

    @Test
    public void testGetProfile() throws Exception {
        running(fakeApplication(), () -> {
            // this token is for bussiness user: krzysiek@gmail.com pass: krzysiek@gmail.com
            String token = "eyJjdHkiOiJ0ZXh0XC9wbGFpbiIsImFsZyI6IkhTMjU2In0.eyJ1c2VySWQiOiI1NTU2MTc0YmU0YjAxY2EyNDViMjdlZDIiLCJ1c2VyUm9sZSI6ImJ1c2luZXNzIn0.DYFNXv7e8zwTOhi9eEV5apb7IZafiryuDf_wlYvS1ec";
            String idforThisToken = "5556174be4b01ca245b27ed2";
            //
            String tokenKawiarni = "eyJjdHkiOiJ0ZXh0XC9wbGFpbiIsImFsZyI6IkhTMjU2In0.eyJ1c2VySWQiOiI1NTRmYzg1OWU0YjBkYjFhZTc1MGI0M2YiLCJ1c2VyUm9sZSI6ImJ1c2luZXNzIn0.Xo3xCNWA38T-mVGaRISzRdydpzlo8ApvnO4FDwmMuMI";
            String idKawiarni = "554fc859e4b0db1ae750b43f";
            FakeRequest request = fakeRequest("GET", "/profiles/business").withHeader("Authorization","Bearer "+tokenKawiarni);
            Result result = route(request);

            assertEquals("When correct token in get request: "+ contentAsString(result),
                    200, status(result));
            assertEquals("{\"_id\":{\"$oid\":\"554fc859e4b0db1ae750b43f\"},\"name\":\"kawiarnia\",\"category_list\":null,\"about\":null,\"location\":{\"city\":null,\"country\":null,\"street\":null},\"locationCoordinates\":{\"xCoordinate\":null,\"yCoordinate\":null},\"website\":null,\"music_genres\":null,\"phone\":null,\"picture_url\":null}", contentAsString(result));
            System.out.println(contentAsString(result));

        });
    }
}