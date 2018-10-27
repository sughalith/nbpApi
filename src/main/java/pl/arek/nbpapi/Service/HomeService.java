package pl.arek.nbpapi.Service;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import pl.arek.nbpapi.Model.Nbp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

@Service
public class HomeService {

    public Nbp getActualRate(String currency, Date startDate, Date endDate) {
        try {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://api.nbp.pl/api/exchangerates/rates/a/eur/2012-01-20/2012-01-31/").openConnection();
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("GET");
        String jsonString = readStream(connection.getInputStream());

         Gson gson = new Gson();

         Nbp currencyResponse = gson.fromJson(jsonString, Nbp.class);
         return currencyResponse;
        }
        catch (Exception e){
            return new Nbp();
        }



//

    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }


}
