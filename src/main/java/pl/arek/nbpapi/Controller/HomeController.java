package pl.arek.nbpapi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.arek.nbpapi.Service.HomeService;

import java.util.Date;

@Controller
public class HomeController {

    @Autowired
    HomeService homeService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView homeView(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user_data", homeService.getActualRate("EUR", new Date(), new Date()));
        modelAndView.setViewName("home");
        return modelAndView;
    }

}
