package subereproject.scrawler.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import subereproject.scrawler.services.FindnBookService;
import subereproject.scrawler.services.UpdateBookDBService;

@RestController
@RequestMapping("/api/db")
public class TestDBApi {
    @Autowired
    UpdateBookDBService updateBookDBService;
    @Autowired
    FindnBookService findnBookService;

    @RequestMapping("/update")
    public String update(){
//        updateBookDBService.updateBooks();
        findnBookService.FindNewBook();
        return "finsish";
    }
}
