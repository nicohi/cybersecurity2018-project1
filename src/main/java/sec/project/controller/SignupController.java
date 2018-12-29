package sec.project.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.db.MessageDAO;
import org.springframework.web.bind.annotation.PathVariable;
import sec.project.domain.Message;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;

    @Autowired
    private MessageDAO messageDAO;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }
	 
	//</span><script>alert(document.cookie)</script>
    @RequestMapping(value = "/signups", method = RequestMethod.GET)
    public String getSignups(Model model) {
        model.addAttribute("signups", signupRepository.findAll());
        model.addAttribute("messages", messageDAO.getMessages());
        return "signups";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address, @RequestParam String secret) {
		Signup s = new Signup(name, address, secret);
        signupRepository.save(s);
        return "redirect:/done/" + s.getId();
    }

    @RequestMapping(value = "/done/{id}", method = RequestMethod.GET)
    public String done(Model model, @PathVariable Long id) {
		model.addAttribute("signup", signupRepository.getOne(id));
        return "done";
    }

	//'); DROP TABLE Message; '
    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public String submitMessage(@RequestParam String name, @RequestParam String content) {
		if (name.length() > 0) messageDAO.runQuery("INSERT INTO Message (name, content) VALUES ('" + name + "', '" + content + "');");
        return "redirect:/signups";
    }

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public String getMessages(Model model) {
        model.addAttribute("messages", messageDAO.getMessages());
        return "redirect:/signups";
    }


}
