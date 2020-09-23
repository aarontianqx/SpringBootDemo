package net.aarontian.springboot.demo.readinglist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/")
public class ReadingListController {
    private ReaderRepository readerRepository;
    private ReadingListRepository readingListRepository;
    private AmazonProperties amazonProperties;

    @Autowired
    public ReadingListController(ReaderRepository readerRepository,
                                 ReadingListRepository readingListRepository,
                                 AmazonProperties amazonProperties) {
        this.readerRepository = readerRepository;
        this.readingListRepository = readingListRepository;
        this.amazonProperties = amazonProperties;
    }

    @RequestMapping(method=RequestMethod.GET, value="/fail")
    public void fail() {
        throw new RuntimeException();
    }

    @ExceptionHandler(value=RuntimeException.class)
    @ResponseStatus(value= HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
    public String error() {
        return "error";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (userName == null) {
            return "redirect:/login";
        } else {
            return "redirect:/readingList";
        }
    }

    @RequestMapping(value = "/readingList", method=RequestMethod.GET)
    public String readersBooks(Model model) {
        String userName = SecurityContextHolder.getContext().getAuthentication()
                .getName();
        Reader reader = readerRepository.findById(userName).get();
        List<Book> readingList = readingListRepository.findByReader(reader);
        if (readingList != null) {
            model.addAttribute("books", readingList);
            model.addAttribute("reader", reader);
            model.addAttribute("amazonID", amazonProperties.getAssociateId());
        }
        return "readingList";
    }

    @RequestMapping(value = "/readingList", method=RequestMethod.POST)
    public String addToReadingList(Book book) {
        Reader reader = readerRepository.findById(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        book.setReader(reader);
        readingListRepository.save(book);
        return "redirect:/readingList";
    }
}
