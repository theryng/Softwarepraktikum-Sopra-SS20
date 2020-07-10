package de.hohenheim.sopraproject.controller.institutes;

import de.hohenheim.sopraproject.dto.InstituteDTO;
import de.hohenheim.sopraproject.entity.*;
import de.hohenheim.sopraproject.repository.ContactRepository;
import de.hohenheim.sopraproject.repository.InstituteRepository;
import de.hohenheim.sopraproject.service.ContactFinder;
import de.hohenheim.sopraproject.service.InstituteService;
import de.hohenheim.sopraproject.service.TagsService;
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
    @Autowired
    private TagsService tagsService;

    @RequestMapping(value = "/institutes", method = RequestMethod.GET)
    public String institutes(Model model) {
        List<Institute> allInstitutes = instituteService.findAllInstitutes();
        InstituteDTO instituteDTO = new InstituteDTO();
        instituteDTO.setAllInstitutes(allInstitutes);
        instituteDTO.setInstitute(new Institute());
        model.addAttribute("instituteDTO", instituteDTO);
        model.addAttribute("allTags", tagsService.findAllTags());
        model.addAttribute("tag", new Tags());
        model.addAttribute("searchWord", "");

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
            instituteService.saveInstitute(instituteDTO.getInstitute());
        }
        return "redirect:/institutes";
    }

    /**
     *  Method which can be used to search for a certain Institute.
     *  Calls the Contact Finder, and uses a searchWord to find a Institute.
     *  Reloads the Site at the very End.
     * @param searchWord
     * @return contactHistoryCreator1
     */
    @RequestMapping(value ="/searchInstitute", method = RequestMethod.POST)
    public String searchInstitutes(String searchWord, Model model) {
        List<Institute> allInstitutes;
        ContactFinder findInstitute = new ContactFinder();

        LinkedList<Institute> foundInstitutesTemp = findInstitute.findInstitutes(searchWord, instituteService.findAllInstitutes());

        allInstitutes = foundInstitutesTemp;

        boolean showList = false;
        if(allInstitutes.size()>0){
            showList = true;
        }
        List<Institute> institutes = instituteService.findAllInstitutes();
        InstituteDTO instituteDTO = new InstituteDTO();
        instituteDTO.setAllInstitutes(allInstitutes);
        instituteDTO.setInstitute(new Institute());
        model.addAttribute("instituteDTO", instituteDTO);
        model.addAttribute("allTags", tagsService.findAllTags());
        model.addAttribute("tag", new Tags());
        model.addAttribute("searchWord", "");

        return "redirect:/institutes";
    }
}