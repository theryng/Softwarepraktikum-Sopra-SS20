package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.dto.InstituteDTO;
import de.hohenheim.sopraproject.entity.Address;
import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Institute;
import de.hohenheim.sopraproject.repository.ContactRepository;
import de.hohenheim.sopraproject.repository.InstituteRepository;
import de.hohenheim.sopraproject.service.ContactFinder;
import de.hohenheim.sopraproject.service.InstituteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Controller
public class InstitutesController {


    @Autowired
    private InstituteService instituteService;

    @RequestMapping(value = "/institutes", method = RequestMethod.GET)
    public String institutes(Model model) {
        List<Institute> allInstitutes = instituteService.findAllInstitutes();
        InstituteDTO instituteDTO = new InstituteDTO();
        instituteDTO.setAllInstitutes(allInstitutes);
        instituteDTO.setViewInstituteTemp(new Institute());
        model.addAttribute("instituteDTO", instituteDTO);
        return "institutes";
    }

    /**
     * This method saves a new Institute
     *
     * This method saves a newly created institute to the database. Once the new institute was saved to the database the page will
     * be reloaded and the table will be updated. The new institute will now show up on the page institutes.
     *
     * @param
     * @return redirect:/institutes
     */
    @RequestMapping(value="/saveInstitute", method = RequestMethod.POST)
    public String saveInstitute(@Valid InstituteDTO instituteDTO, BindingResult result){
        if(result.hasErrors()){
            System.out.println("Fehler");
        }
        else{
            instituteService.saveInstitute(instituteDTO.getViewInstituteTemp());
        }
        return "redirect:/institutes";
    }

    /**
     * This method finds all Institutes.
     *
     * This method finds all existing institutes and returns them to the user
     *
     * @param model
     * @return contacts
     */
    @RequestMapping("/allInstitutes")
    public String allInstitutes(Model model) {
        model.addAttribute("allInstitutes", instituteService.findAllInstitutes());
        return "institutes";
    }

    /**
     * This method shows the details of a institute
     *
     * This method opens up the instituteDetails page of a specific institute the user wishes to see. Once the user clicks the
     * button that this method is bound to the window will open and allows him to view the details to this institute.
     *
     * @param institute
     * @return redirect:/instituteDetails
     */
    @RequestMapping("/viewInstitute")
    public String viewInstitute(Institute institute) {
        InstituteDetailsController.instituteID = institute.getInstituteID();
        return "redirect:/instituteDetails";
    }

    /**
     *  Method which can be used to search for a certain Institute.
     *  Calls the Contact Finder, and uses a searchWord to find a Institute.
     *  Reloads the Site at the very End.
     * @param searchWord
     * @return contactHistoryCreator1
     */
    @RequestMapping(value ="/searchInstitute", method = RequestMethod.POST)
    public String searchInstitutes(String searchWord) {
        /*ContactFinder findContact = new ContactFinder();
        Set<Institute> foundInstitutesTemp = findContact.findContacts(searchWord, contactRepository.findAll());
        if(foundContactsTemp.size()>0){
            foundContacts = foundContactsTemp;
        }
        else{
            foundContacts.clear();
        }*/
        return "redirect:/contacts";
    }
}
