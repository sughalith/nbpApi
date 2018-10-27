package pl.arek.nbpapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.arek.nbpapi.model.ApiSearchData;
import pl.arek.nbpapi.model.Nbp;
import pl.arek.nbpapi.service.HomeService;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private HomeService homeService;


    @GetMapping(value = "/")
    public ModelAndView addFood() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("search_view", new ApiSearchData());
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @PostMapping(value = "/")
    public ModelAndView addFood(@Valid ApiSearchData searchData) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("search_view", new ApiSearchData());
        Nbp nbp = homeService.getActualRate(searchData);
        modelAndView.addObject("result_list", nbp);
        modelAndView.addObject("details_list", homeService.calculateCurrencyDetails(nbp));
        modelAndView.setViewName("home");
        return modelAndView;
    }

}
