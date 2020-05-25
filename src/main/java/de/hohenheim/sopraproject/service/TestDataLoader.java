package de.hohenheim.sopraproject.service;

import com.sun.xml.fastinfoset.tools.XML_SAX_StAX_FI;
import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Institute;
import de.hohenheim.sopraproject.entity.Role;
import de.hohenheim.sopraproject.entity.User;
import de.hohenheim.sopraproject.service.RoleService;
import de.hohenheim.sopraproject.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class TestDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(TestDataLoader.class);

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private InstituteService instituteService;

    /**
     * This method is used to define test Arguments for the database. The method will be execute when the Spring context
     * is initialized that means it will be executed whenever the server is (re-)started. There are several Sets in this
     * methods, which represents all the person which leads to the respective institute. Some of them will be empty
     * because there there are no contact objects which are part of these institutes. nevertheless the sets are
     * initialized for completeness and for easier adding of the contact objects
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event){
        logger.info("Initialisiere Datenbank mit Testdaten...");

        Role userRole = new Role("ROLE_USER");
        Role adminRole = new Role("ROLE_ADMIN");
        roleService.saveRole(userRole);
        roleService.saveRole(adminRole);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);

        User normalUser1 = new User();
        normalUser1.setUsername("user1");
        normalUser1.setPassword(passwordEncoder.encode("1234"));
        normalUser1.setRoles(userRoles);
        userService.saveUser(normalUser1);

        User normalUser2 = new User();
        normalUser2.setUsername("user2");
        normalUser2.setPassword(passwordEncoder.encode("1234"));
        normalUser2.setRoles(userRoles);
        userService.saveUser(normalUser2);

        User normalUser3 = new User();
        normalUser3.setUsername("user3");
        normalUser3.setPassword(passwordEncoder.encode("1234"));
        normalUser3.setRoles(userRoles);
        userService.saveUser(normalUser3);

        User normalUser4 = new User();
        normalUser4.setUsername("user4");
        normalUser4.setPassword(passwordEncoder.encode("1234"));
        normalUser4.setRoles(userRoles);
        userService.saveUser(normalUser4);

        User normalUser5 = new User();
        normalUser5.setUsername("user5");
        normalUser5.setPassword(passwordEncoder.encode("1234"));
        normalUser5.setRoles(userRoles);
        userService.saveUser(normalUser5);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRoles(adminRoles);
        userService.saveUser(admin);

        //Example contacts
        Contact max = new Contact();
        max.setFirstname("Max");
        max.setLastname("Mustermann");
        //max.setAge(23);
        max.setOccupation("Student");
        max.setEmail("maxM@stud.uni-stuttgart.de");
        max.setCourseOfStudies("Wirtschaftsinformatik");
        max.setFreeText("Semestersprecher des fünften Bachelor Semesters");
        max.setFormatDateOfBirth(1996, 0, 01);
        contactService.saveContact(max);

        Contact julia = new Contact();
        julia.setFirstname("Julia");
        julia.setLastname("Müller");
        //julia.setAge(22);
        julia.setOccupation("Student");
        julia.setEmail("juliaM@stud.uni-stuttgart.de");
        julia.setCourseOfStudies("BWL");
        julia.setFreeText("Julia arbeitet 2 mal in der Woche ehrenamtlich im Altersheim (Stand: 01.02.2019");
        julia.setFormatDateOfBirth(1997, 2, 12);
        contactService.saveContact(julia);

        Contact anna = new Contact();
        anna.setFirstname("Anna");
        anna.setLastname("Maria");
        //anna.setAge(25);
        anna.setOccupation("Student");
        anna.setEmail("annaM@stud.uni-stuttgart.de");
        anna.setCourseOfStudies("International Business");
        anna.setFreeText("");
        anna.setFormatDateOfBirth(1999, 11, 8);
        contactService.saveContact(anna);

        Contact jose = new Contact();
        jose.setFirstname("José");
        jose.setLastname("Rodriguez");
        //jose.setAge(23);
        jose.setOccupation("Student");
        jose.setEmail("joseR@stud.uni-stuttgart.de");
        jose.setCourseOfStudies("Philosophie");
        jose.setFreeText("José ist in 2019 nach Deutschland gekommen. Er studierte 4 Semester lang Philosophie in Chile");
        jose.setFormatDateOfBirth(1998, 10, 10);
        contactService.saveContact(jose);

        Contact luisa = new Contact();
        luisa.setFirstname("Luisa");
        luisa.setLastname("Mayer");
        //luisa.setAge(25);
        luisa.setOccupation("Doktorantin");
        luisa.setEmail("luisaM@stud.uni-stuttgart.de");
        luisa.setCourseOfStudies("Chemi");
        luisa.setFreeText("Promovierte 2020 im Gebiet der organischen Chemie");
        luisa.setFormatDateOfBirth(1992, 5, 13);
        contactService.saveContact(luisa);

        Contact paulina = new Contact();
        paulina.setFirstname("Paulina");
        paulina.setLastname("Josefine");
        //paulina.setAge(20);
        paulina.setOccupation("Studentin");
        paulina.setEmail("PaulinaJ@stud.uni-stuttgart.de");
        paulina.setCourseOfStudies("Chemi");
        paulina.setFreeText("");
        paulina.setFormatDateOfBirth(1996, 9, 30);
        contactService.saveContact(paulina);

        Contact peter = new Contact();
        peter.setFirstname("Peter");
        peter.setLastname("Lustig");
        //peter.setAge(23);
        peter.setOccupation("Student");
        peter.setEmail("PeterL@stud.uni-stuttgart.de");
        peter.setCourseOfStudies("Chemi");
        peter.setFreeText("");
        peter.setFormatDateOfBirth(2001, 9, 28);
        contactService.saveContact(peter);

        Contact jonas = new Contact();
        jonas.setFirstname("Jonas Alfred");
        jonas.setLastname("Homm");
        //jonas.setAge(45);
        jonas.setOccupation("Professor");
        jonas.setEmail("JonasH@prof.uni-stuttgart.de");
        jonas.setCourseOfStudies("Informatik");
        jonas.setFreeText("Forschungsbereiche: Informationstechnologie");
        jonas.setFormatDateOfBirth(1996, 0, 01);
        contactService.saveContact(jonas);

        Contact florian = new Contact();
        florian.setFirstname("Florian");
        florian.setLastname("Wagner");
        //florian.setAge(19);
        florian.setOccupation("Student");
        florian.setEmail("FlorianW@stud.uni-stuttgart.de");
        florian.setCourseOfStudies("Informatik");
        florian.setFreeText("");
        florian.setFormatDateOfBirth(2000, 6, 6);
        contactService.saveContact(florian);

        Contact sabine = new Contact();
        sabine.setFirstname("Sabine");
        sabine.setLastname("Müller");
        //sabine.setAge(19);
        sabine.setOccupation("Studentin");
        sabine.setEmail("SabineM@stud.uni-stuttgart.de");
        sabine.setCourseOfStudies("Informatik");
        sabine.setFreeText("");
        sabine.setFormatDateOfBirth(1995, 0, 9);
        contactService.saveContact(sabine);

        Contact jana = new Contact();
        jana.setFirstname("Jana");
        jana.setLastname("Müller");
        //jana.setAge(23);
        jana.setOccupation("Studentin");
        jana.setEmail("JanaM@stud.uni-stuttgart.de");
        jana.setCourseOfStudies("Informatik");
        jana.setFreeText("");
        jana.setFormatDateOfBirth(1996, 11, 19);
        contactService.saveContact(jana);

        Contact tristan = new Contact();
        tristan.setFirstname("Tristan");
        tristan.setLastname("Müller");
        //tristan.setAge(26);
        tristan.setOccupation("Doktorant (Stand: 02.03.2020)");
        tristan.setEmail("TristanM@stud.uni-stuttgart.de");
        tristan.setCourseOfStudies("Informatik");
        tristan.setFreeText("Forschungsgebiet: Simulationswissenschaft");
        tristan.setFormatDateOfBirth(1996, 8, 28);
        contactService.saveContact(tristan);

        Contact alfred = new Contact();
        alfred.setFirstname("Alfred");
        alfred.setLastname("Sigmar");
        //alfred.setAge(28);
        alfred.setOccupation("Student");
        alfred.setEmail("AlfredS@stud.uni-stuttgart.de");
        alfred.setCourseOfStudies("Informatik");
        alfred.setFreeText("");
        alfred.setFormatDateOfBirth(1997, 3, 3);
        contactService.saveContact(alfred);

        Contact aleyna = new Contact();
        aleyna.setFirstname("Aleyna");
        aleyna.setLastname("Turgut");
        //aleyna.setAge(20);
        aleyna.setOccupation("Studentin");
        aleyna.setEmail("AleynaT@stud.uni-stuttgart.de");
        aleyna.setCourseOfStudies("Informatik");
        aleyna.setFreeText("");
        aleyna.setFormatDateOfBirth(1998, 0, 01);
        contactService.saveContact(aleyna);

        Contact milan = new Contact();
        milan.setFirstname("Milan");
        milan.setLastname("Russo");
        //milan.setAge(20);
        milan.setOccupation("Student");
        milan.setEmail("MilanR@stud.uni-stuttgart.de");
        milan.setCourseOfStudies("Informatik");
        milan.setFreeText("");
        milan.setFormatDateOfBirth(2000, 9, 10);
        contactService.saveContact(milan);

        Contact maxW = new Contact();
        maxW.setFirstname("Max");
        maxW.setLastname("Williams");
        //maxW.setAge(23);
        maxW.setOccupation("Student");
        maxW.setEmail("MaxW@stud.uni-stuttgart.de");
        maxW.setCourseOfStudies("Philosophie");
        maxW.setFreeText("");
        maxW.setFormatDateOfBirth(1999, 7, 7);
        contactService.saveContact(maxW);

        Contact alex = new Contact();
        alex.setFirstname("Alex");
        alex.setLastname("Johnson");
        //alex.setAge(23);
        alex.setOccupation("Student");
        alex.setEmail("AlexJ@stud.uni-stuttgart.de");
        alex.setCourseOfStudies("Philosophie");
        alex.setFreeText("");
        alex.setFormatDateOfBirth(1999, 5, 7);
        contactService.saveContact(alex);

        Contact min = new Contact();
        min.setFirstname("Min");
        min.setLastname("Lee");
        //min.setAge(25);
        min.setOccupation("Studentin");
        min.setEmail("MinL@stud.uni-stuttgart.de");
        min.setCourseOfStudies("Philosophie");
        min.setFreeText("");
        min.setFormatDateOfBirth(1996, 9, 8);
        contactService.saveContact(min);

        //Example institutes plus the contacts which leads to them
        Set<Contact> ibksContacts = new HashSet<>();

        Institute ibk = new Institute();
        ibk.setLocation("Campus Stadtmitte");
        ibk.setName("Institut für Baukonstruktion (IBK)");
        ibk.setContacts(ibksContacts);
        instituteService.saveInstitute(ibk);

        Set<Contact> philoConacts = new HashSet<>();
        philoConacts.add(jose);
        philoConacts.add(alex);
        philoConacts.add(min);
        philoConacts.add(maxW);

        Institute philo = new Institute();
        philo.setLocation("Campus Stadtmitte");
        philo.setName("Institut für Philosophie (PHILO)");
        philo.setContacts(philoConacts);
        instituteService.saveInstitute(philo);

        Set<Contact> iaasContacts = new HashSet<>();

        Institute iaas = new Institute();
        iaas.setLocation("Campus Vaihingen");
        iaas.setName("Institut für Architektur von Anwendungssystemen (IAAS)");
        iaas.setContacts(iaasContacts);
        instituteService.saveInstitute(iaas);

        Set<Contact> iocContacts = new HashSet<>();
        iocContacts.add(luisa);
        iocContacts.add(paulina);
        iocContacts.add(peter);

        Institute ioc = new Institute();
        ioc.setLocation("Campus Vaihingen");
        ioc.setName("Institut für organische Chemie (IOC)");
        ioc.setContacts(iocContacts);
        instituteService.saveInstitute(ioc);

        Set<Contact> fmiContacts = new HashSet<>();
        fmiContacts.add(jonas);
        fmiContacts.add(florian);
        fmiContacts.add(sabine);
        fmiContacts.add(jana);
        fmiContacts.add(tristan);
        fmiContacts.add(alfred);
        fmiContacts.add(aleyna);
        fmiContacts.add(milan);

        Institute fmi = new Institute();
        fmi.setLocation("Campus Vaihingen");
        fmi.setName("Institut für Formale Methoden der Informatik (FMI)");
        fmi.setContacts(fmiContacts);
        instituteService.saveInstitute(fmi);

        Set<Contact> bwiConacts = new HashSet<>();
        bwiConacts.add(max);
        bwiConacts.add(anna);
        bwiConacts.add(julia);

        Institute bwi = new Institute();
        bwi.setLocation("Campus Stadtmitte");
        bwi.setName("Betriebswirtschaftliches Institut (BWI)");
        bwi.setContacts(bwiConacts);
        instituteService.saveInstitute(bwi);
    }
}