package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Address;
import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.repository.ContactRepository;
import de.hohenheim.sopraproject.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * This controller contains the methods to create a new contact
 *
 * This controller contains all important methods to create a new contact and saving it inside the database.
 * It allows to get all informations about a contact, to save a newly created contact and to look up all the details
 * about a certain contact.
 *
 * @date 26.06.2020
 * @author Lukas Januschke
 */
@Controller
public class ContactsController {

    private static Contact viewContactTemp;
    @Autowired
    private ContactRepository contactRepository;

    private static boolean hasError = false;

    /**
     * This method gets all the information about a contact
     *
     * This method gives out all the information about the contacts stored inside the database
     *
     * @param model
     * @return contacts
     */
    private ContactService contactService;
    @RequestMapping(value ="/contacts", method = RequestMethod.GET)
    public String contacts(Model model) {
        model.addAttribute("contact", new Contact());
        model.addAttribute("hasError", hasError);
        model.addAttribute("allContacts", contactRepository.findAll());
        return "contacts";
    }

    /**
     * This method saves a new contact
     *
     * This method saves a newly created contact to the database. It also merges the temporary attributes of ZipCode, City,
     * Street and houseNumber to one single attribute called address. Once the new contact was saved to the database the page will
     * be reloaded and the table will be updated. The new contact will no show up on the page contacts.
     *
     * @param contact
     * @return redirect:/contacts
     */
    @RequestMapping(value="/saveContact", method = RequestMethod.POST)
    public String saveContact(@Valid Contact contact, BindingResult result){
        if(result.hasErrors()){
            System.out.println("Fehler");
            hasError = true;
            return "redirect:/contacts";
        }
        else{
            hasError = false;
            contact.setAddress(new Address(contact.getTempZipCode(), contact.getTempCity(), contact.getTempStreet() , contact.getTempHouseNmbr()));
            contactRepository.save(contact);
            return "redirect:/contacts";
        }
    }

    /**
     * This method finds all Contacts
     *
     * This method finds all existing contacts and gives them out to the user
     *
     * @param model
     * @return contacts
     */
    @RequestMapping("/allContacts")
    public String allContacts(Model model) {
        model.addAttribute("allContacts", contactRepository.findAll());
        return "contacts";
    }

    /**
     * This method shows the details of a contact
     *
     * This method opens up the contactDetails page of a specific contact the user wishes to see. Once the user clicks the
     * button that this method is bound to the window will open and allows him to view the details to this contact.
     *
     * @param contactID
     * @return redirect:/contactDetails
     */
    @RequestMapping("/viewContact")
    public String viewContact(Contact contactID) {
        ContactDetailsController.contactID = contactID.getContactID();
        return "redirect:/contactDetails";
    }
}