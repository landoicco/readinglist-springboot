package lando.spring.readinglist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/readingList")
public class ReadingListController {

    private static final String reader = "lando";

    private ReadingListRepository readingListRepository;
    private AppInfo appInfo;

    @Autowired
    public ReadingListController(ReadingListRepository readingListRepository,
                                 AppInfo appInfo) {
        this.readingListRepository = readingListRepository;
        this.appInfo = appInfo;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String readersBooks(Model model) {
        List<Book> readingList = readingListRepository.findByReader(reader);
        if (readingList != null) {
            model.addAttribute("books", readingList);
            model.addAttribute("devName", appInfo.getDevName());
        }
        return "readingList";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addToReadingList(Book book) {
        book.setReader(reader);
        readingListRepository.save(book);
        return "redirect:/readingList";
    }
}
