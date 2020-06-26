package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.ContactHistory;
import de.hohenheim.sopraproject.entity.Institute;
import de.hohenheim.sopraproject.entity.Relationship;
import de.hohenheim.sopraproject.repository.ContactRepository;
import de.hohenheim.sopraproject.repository.ContactHistoryRepository;
import de.hohenheim.sopraproject.repository.InstituteRepository;
import de.hohenheim.sopraproject.repository.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

/**
 * This controller is used to handle all methods revolving around the html page instituteDetails
 *
 * It helps with various method to save new Institutes, delete existing ones, open up the InstituteContactCreator.
 *
 * @date 26.06.2020
 * @author Lukas Januschke
 */
@Controller
public class InstituteDetailsController {

    public static Integer instituteID;

    @Autowired
    private InstituteRepository instituteRepository;

    @Autowired
    private ContactRepository contactRepository;

    public static Institute institute;


    private boolean existingContacts = false;
    public boolean hasError = false;

    /**
     * Main method for Viewing of Institute Details Site, adds necessary Attributes
     * @param model
     * @return instituteDetails
     */
    @RequestMapping(value = "/instituteDetails", method = RequestMethod.GET)
    public String instituteDetails(Model model) {
        institute = instituteRepository.findByInstituteID(instituteID);
        checkTables(institute);
        model.addAttribute("institute", institute);
        model.addAttribute("hasError", hasError);
        model.addAttribute("institutes", new Institute());
        model.addAttribute("existingContacts", existingContacts);
        model.addAttribute("tempContact", new Contact());

        hasError = false;
        return "institutes/instituteDetails";
    }

    /**
     * This method saves the Institutes and saves it to the existing database
     *
     * It also checks if the given ID is already mapped to a different
     * existing institute. As long thats not the case a new institute will be saved to the database. Once the institute is saved
     * the page will be reloaded to update the table with the new given information/attributes.
     *
     * @param institute
     * @return redirect:/institutes
     */
    @RequestMapping(value = "/savingInstitute", method = RequestMethod.POST)
    public String savingInstitute(@Valid Institute institute, BindingResult result) {
        if(result.hasErrors()){
            hasError = true;
            return "redirect:/instituteDetails";
        }
        else{
            hasError = false;
            institute.setInstituteID(instituteID);
            if(!instituteRepository.findByInstituteID(institute.getInstituteID()).equals(institute)){
                instituteRepository.save(institute);
            }
            return "redirect:/instituteDetails";
        }
    }

    /**
     * This method deletes an existing institute inside the database
     *
     * An existing institute will be deleted. The corresponding contactID will also be deleted so new contacts can get this
     * ID in the future. Once the institute is deleted the page will be reloaded to update the contact table.
     *
     * @param institute
     * @return redirect:/institutes
     */

    @RequestMapping(value = "/deleteInstitute", method = RequestMethod.POST)
    public String deleteInstitute(Institute institute) {
        instituteRepository.deleteById(institute.getInstituteID());
        return "redirect:/institutes";
    }

    /**
     * This method exits the instituteDetails
     *
     * This method exits the instituteDetails page by clicking on a corresponding button bounded with this method. Once clicked
     * the page will be redirected to the institute page.
     *
     * @return redirect:/institutes
     */
    @RequestMapping(value = "/backInstituteDetails", method = RequestMethod.POST)
    public String backInstituteDetails() {
        instituteID = null;
        return "redirect:/institutes";
    }

    /**
     * This method removes the chosen Contact from the Contacts of the Institute
     *
     * @return redirect:/institutes
     */
    @RequestMapping(value = "/deleteContactFromInstitute", method = RequestMethod.POST)
    public String deleteContactFromInstitute(Contact contact) {
        System.out.println(contact.getContactID());
        Contact contactTemp = new Contact();
        Set<Contact> contactInstitute = institute.getContacts();
        System.out.println(contactInstitute.size());
        boolean exists = false;
        for(Contact con : contactInstitute){
            System.out.println(con.getContactID());
            if(con.getContactID() == (contact.getContactID())){
                exists = true;
                contactTemp = con;
                institute.setContacts(new HashSet<>());
                contactInstitute.remove(contactTemp);
                institute.setContacts(contactInstitute);
                instituteRepository.save(institute);
                return "redirect:/instituteDetails";
            }
            else{
                exists = false;
            }
        }

        System.out.println(contactInstitute.size());
        return "redirect:/instituteDetails";
    }
    /**
     * This method deletes the chosen Contact from the Contacts of the Institute
     * @param institute
     * @return redirect:/institutes
     */
    @RequestMapping(value ="/addContactsToInstitute", method = RequestMethod.POST)
    public String addContactsToInstitute(Institute institute) {
        InstituteContactCreatorController.insituteID = institute.getInstituteID();
        return "redirect:/instituteContactCreator";
    }
    /**
     * This method checks whether there are tables to display
     *
     * This method checks whether the institute has an existing contact. If the count for each of them is higher
     * than 0 it will return the boolean true
     *
     * @param institute
     */
    private void checkTables(Institute institute){
        if(institute.getContacts().size()>0){
            existingContacts = true;
        }
        else{
            existingContacts = false;
        }
    }
}