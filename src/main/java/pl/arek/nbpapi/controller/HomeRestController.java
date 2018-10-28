package pl.arek.nbpapi.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.arek.nbpapi.model.Nbp;
import pl.arek.nbpapi.service.HomeService;

import java.lang.reflect.Type;
import java.util.List;

@RestController
public class HomeRestController {

    @Autowired
    HomeService homeService;

    Gson gson = new Gson();

    @GetMapping(value = "/find/{code}/{startDate}/{endDate}")
    public String checkCurrency(@PathVariable String code
                                , @PathVariable  String startDate
                                , @PathVariable  String endDate){

        Type nbpListType = new TypeToken<List<Nbp>>() {
        }.getType();
        return gson.toJson(homeService.findCurrency(code, startDate, endDate), nbpListType);
    }
}
