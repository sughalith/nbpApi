package pl.arek.nbpapi.service;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import pl.arek.nbpapi.model.ApiSearchData;
import pl.arek.nbpapi.model.Nbp;
import pl.arek.nbpapi.model.NbpDetails;
import pl.arek.nbpapi.model.Rate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class HomeService {

    public Nbp getActualRate(ApiSearchData apiSearchData) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("http://api.nbp.pl/api/exchangerates/rates/a/")
                    .append(apiSearchData.getCode())
                    .append("/")
                    .append(dateFormatter(apiSearchData.getStartDate()))
                    .append("/")
                    .append(dateFormatter(apiSearchData.getEndDate()))
                    .append("/");
            HttpURLConnection connection = (HttpURLConnection) new URL(sb.toString()).openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");
            String jsonString = readStream(connection.getInputStream());

            Gson gson = new Gson();

            Nbp currencyResponse = gson.fromJson(jsonString, Nbp.class);
            return currencyResponse;
        } catch (Exception e) {
            return new Nbp();
        }
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

    private String dateFormatter(Date date){
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public NbpDetails calculateCurrencyDetails(Nbp nbp){
        if(nbp.getRates() == null)
            return new NbpDetails();
        NbpDetails nbpDetails = new NbpDetails();
        double tempValue = 0;
        double avgValue;
        int counter = 0;
        for(Rate rate : nbp.getRates()){
            tempValue += rate.getMid();
            counter ++;
        }
        avgValue = tempValue/counter;
        nbpDetails.setAvgCurrency(avgValue);
        nbpDetails.setDeviation(Math.sqrt(calculateVariance(nbp.getRates(), counter, avgValue)));
        return nbpDetails;
    }

    public double calculateVariance(List<Rate> rates, int counter, double avgValue){
        double tempValue = 0;
        for(Rate rate : rates){
            tempValue += Math.pow(rate.getMid() - avgValue, 2);
        }
        return tempValue/counter;
    }
}
