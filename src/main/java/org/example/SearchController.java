package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private BasicWebCrawler crawler;

    @GetMapping
    public String getSearchPage(){
        return "search";
    }

    @PostMapping
    public String makeSearch(String search, ModelMap model) {
        String result = crawler.getResult(search);
        System.out.println(result);
        model.addAttribute("result", result);
        return "search";
    }
}
