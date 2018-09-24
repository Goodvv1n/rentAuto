package ru.pleshkov.rentAuto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.pleshkov.rentAuto.entity.Auto;
import ru.pleshkov.rentAuto.entity.Client;
import ru.pleshkov.rentAuto.restBean.Rent;
import ru.pleshkov.rentAuto.restBean.NewAuto;
import ru.pleshkov.rentAuto.restBean.NewClient;
import ru.pleshkov.rentAuto.restBean.NewRent;
import ru.pleshkov.rentAuto.service.AutoService;
import ru.pleshkov.rentAuto.service.ClientService;
import ru.pleshkov.rentAuto.service.RentService;

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
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/client")
    @ResponseBody
    public ResponseEntity deleteClient(@RequestParam String name){
        try{
            clientService.deleteClient(name);
        } catch (SAPIException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/auto")
    @ResponseBody
    public ResponseEntity addNewAuto (@RequestBody NewAuto newAuto) {
        try{
            autoService.addAuto(newAuto);
        } catch (SAPIException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path="/auto")
    @ResponseBody
    public ResponseEntity<List<Auto>> getAutoList(@RequestParam(required = false) String brand) {
        List<Auto> results = autoService.getAutoList(brand);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @DeleteMapping("/auto")
    @ResponseBody
    public ResponseEntity deleteAuto(@RequestParam String brand){
        try{
            autoService.deleteAuto(brand);
        } catch (SAPIException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path="/rent")
    @ResponseBody
    public ResponseEntity getRentList(@RequestParam String clientName,
                                  @RequestParam String autoBrand) {
        Rent rent;
        try{
            rent = rentService.findRentActions(clientName, autoBrand);
        } catch (SAPIException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(rent, HttpStatus.OK);
    }

    @PostMapping(path="/rent")
    @ResponseBody
    public ResponseEntity addRent(@RequestBody NewRent newRent) {
        try{
           rentService.addRentAction(newRent);
        } catch (SAPIException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(path="/rent")
    @ResponseBody
    public ResponseEntity deleteRent(@RequestParam String clientName,
                                     @RequestParam String autoBrand) {
        try{
            rentService.deleteRentAction(clientName, autoBrand);
        } catch (SAPIException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
