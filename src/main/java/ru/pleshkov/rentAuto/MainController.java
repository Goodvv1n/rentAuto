package ru.pleshkov.rentAuto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.pleshkov.rentAuto.entity.Auto;
import ru.pleshkov.rentAuto.entity.Client;
import ru.pleshkov.rentAuto.impl.AutoService;
import ru.pleshkov.rentAuto.impl.ClientService;
import ru.pleshkov.rentAuto.impl.RentService;
import ru.pleshkov.rentAuto.restBean.NewAuto;
import ru.pleshkov.rentAuto.restBean.NewClient;

import java.util.List;

/**
 * @author pleshkov on 19.09.2018.
 */
@Controller
public class MainController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AutoService autoService;

    @Autowired
    private RentService rentService;

    @GetMapping(path="/client")
    @ResponseBody
    public ResponseEntity<List<Client>> getClientList(@RequestParam(required = false) String name) {
        List<Client> results = clientService.getClientList(name);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("/client")
    @ResponseBody
    public ResponseEntity addNewClient (@RequestBody NewClient newClient) {
        try{
            clientService.addClient(newClient);
        } catch (SAPIException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping("/auto")
    @ResponseBody
    public ResponseEntity addNewAuto (@RequestBody NewAuto newAuto) {
        try{
            autoService.addAuto(newAuto);
        } catch (SAPIException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping(path="/auto")
    @ResponseBody
    public ResponseEntity<List<Auto>> getAutoList(@RequestParam(required = false) String brand) {
        List<Auto> results = autoService.getAutoList(brand);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }



}
