package ru.pleshkov.rentAuto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.pleshkov.rentAuto.entity.Client;
import ru.pleshkov.rentAuto.repository.ClientRepository;

/**
 * @author pleshkov on 19.09.2018.
 */
@Controller
public class MainController {

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/greeting")
    public @ResponseBody String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping(path="/add") // Map ONLY GET Requests
    public @ResponseBody String addNewUser (@RequestParam String name
            , @RequestParam String email) {
        Client client = new Client();
        client.setName(name);
        client.setEmail(email);
        clientRepository.save(client);
        return "Saved";
    }

    @GetMapping(path="/clientList")
    public @ResponseBody Iterable<Client> getClientList() {
        return clientRepository.findAll();
    }
}
