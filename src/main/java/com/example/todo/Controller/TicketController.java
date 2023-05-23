package com.example.todo.Controller;
import com.example.todo.Model.Ticket;
import com.example.todo.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@Controller
public class TicketController {

    @Autowired
    TicketRepository ticketRepository;


    //open add info page
    @GetMapping("/gotoinfo")
    public String newTicket(Ticket ticket, Model model){

        return "add-info";
    }
    //open thanks page to confirm booking
    @GetMapping("/thankss")
    public String thanks(Ticket ticket, Model model){
        return "thanku";
    }

    //add the info to the repository and go back to index to show the info
    @PostMapping("/addticket")
    public String addInfo(@Valid Ticket ticket, BindingResult result, Model model) {
        if (result.hasErrors()){
            return "add-info";
        }
        //save in the repository
        ticketRepository.save(ticket);
        return "redirect:/index";
    }

    //show all tickets page
    @GetMapping("/index")
    public String showAllInfo(Model model){
        model.addAttribute("tickets", ticketRepository.findAll());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ticket Id:" + id));

        model.addAttribute("ticket", ticket);
        return "update-info";
    }
    @GetMapping("/delete/{id}")
    public String deleteInfo(@PathVariable("id") long id, Model model) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ticket Id:" + id));
        ticketRepository.delete(ticket);
        model.addAttribute("tickets", ticket);
        return "redirect:/index";
    }
    @PostMapping("/update/{id}")
    public String updateInfo(@PathVariable("id") long id, @Valid Ticket ticket,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            ticket.setId(id);
            return "update-info";
        }

        ticketRepository.save(ticket);
        return "redirect:/index";
    }
}
