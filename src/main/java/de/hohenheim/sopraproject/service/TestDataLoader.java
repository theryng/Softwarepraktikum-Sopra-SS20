package de.hohenheim.sopraproject.service;

import de.hohenheim.sopraproject.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

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

    @Autowired
    private ContacthistoryService contacthistoryService;

    @Autowired
    private EventService eventService;

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
        Contact julia = new Contact();
        julia.setFirstname("Julia");
        julia.setLastname("Müller");
        julia.setOccupation("Sekretärin");
        julia.setEmail("juliaM@yahoo.de");
        julia.setCourseOfStudies("BWL");
        julia.setFreeText("Julia arbeitet 2 mal in der Woche ehrenamtlich im Altersheim (Stand: 01.02.2019");
        julia.setFormatDateOfBirth(1977, 2, 12);
        julia.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "2"));
        julia.setHobby("Macht gerne Ballet");
        julia.setLinkToHomepage("");
        //julia.setInstitute(allianzStuttgart);
        contactService.saveContact(julia);

        Set<Contact> maxWife = new HashSet<>();
        maxWife.add(julia);

        Contact anna = new Contact();
        anna.setFirstname("Anna");
        anna.setLastname("Maria");
        anna.setOccupation("Finanzbeauftragter");
        anna.setEmail("annaM@gmail.com");
        anna.setCourseOfStudies("International Business");
        anna.setFreeText("");
        anna.setFormatDateOfBirth(1989, 11, 8);
        anna.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "3"));
        anna.setHobby("Lesen");
        anna.setLinkToHomepage("");
        //anna.setInstitute(porscheStuttgart);
        contactService.saveContact(anna);

        Contact max = new Contact();
        max.setFirstname("Max");
        max.setLastname("Mustermann");
        max.setOccupation("Projektmanager");
        max.setEmail("maxM@yahoo.de");
        max.setCourseOfStudies("Wirtschaftsinformatik");
        max.setFreeText("Semestersprecher des fünften Bachelor Semesters in SS99 ");
        max.setFormatDateOfBirth(1986, 0, 01);
        max.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "1"));
        max.setHobby("Kickboxen");
        max.setLinkToHomepage("https://de.wikipedia.org/wiki/Mustermann");
        contactService.saveContact(max);

        Contact jose = new Contact();
        jose.setFirstname("José");
        jose.setLastname("Rodriguez");
        jose.setOccupation("angestellter");
        jose.setEmail("joseR@gmail.com");
        jose.setCourseOfStudies("Philosophie");
        jose.setFreeText("José ist in 2019 nach Deutschland gekommen. Er studierte 4 Semester lang Philosophie in Chile");
        jose.setFormatDateOfBirth(1998, 10, 10);
        jose.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "4"));
        jose.setHobby("Schreibt gerne Fabeln");
        jose.setLinkToHomepage("");
        //jose.setInstitute(mercedesStuttgart);
        contactService.saveContact(jose);

        Contact luisa = new Contact();
        luisa.setFirstname("Luisa");
        luisa.setLastname("Mayer");
        luisa.setOccupation("Doktorantin");
        luisa.setEmail("luisaM@yahoo.de");
        luisa.setCourseOfStudies("Chemie");
        luisa.setFreeText("Promovierte 2020 im Gebiet der organischen Chemie");
        luisa.setFormatDateOfBirth(1992, 5, 13);
        luisa.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "5"));
        luisa.setHobby("");
        luisa.setLinkToHomepage("");
        //luisa.setInstitute(mercedesStuttgart);
        contactService.saveContact(luisa);

        Contact paulina = new Contact();
        paulina.setFirstname("Paulina");
        paulina.setLastname("Josefine");
        paulina.setOccupation("Angestellte");
        paulina.setEmail("PaulinaJ@gmail.com");
        paulina.setCourseOfStudies("Chemie");
        paulina.setFreeText("");
        paulina.setFormatDateOfBirth(1996, 9, 30);
        paulina.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "6"));
        paulina.setHobby("Sumo Ringen");
        paulina.setLinkToHomepage("");
        contactService.saveContact(paulina);

        Contact peter = new Contact();
        peter.setFirstname("Peter");
        peter.setLastname("Lustig");
        peter.setOccupation("Angestellter");
        peter.setEmail("PeterL@gmail.com");
        peter.setCourseOfStudies("Chemie");
        peter.setFreeText("");
        peter.setFormatDateOfBirth(2001, 9, 28);
        peter.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "7"));
        peter.setHobby("");
        peter.setLinkToHomepage("");
        // peter.setEvents();
        contactService.saveContact(peter);

        Contact jonas = new Contact();
        jonas.setFirstname("Jonas Alfred");
        jonas.setLastname("Homm");
        jonas.setOccupation("Professor");
        jonas.setEmail("JonasH@yahoo.de");
        jonas.setCourseOfStudies("Informatik");
        jonas.setFreeText("Forschungsbereiche: Informationstechnologie");
        jonas.setFormatDateOfBirth(1996, 0, 01);
        jonas.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "8"));
        jonas.setHobby("");
        jonas.setLinkToHomepage("");
        contactService.saveContact(jonas);

        Contact florian = new Contact();
        florian.setFirstname("Florian");
        florian.setLastname("Wagner");
        florian.setOccupation("Manager");
        florian.setEmail("FlorianW@gmail.com");
        florian.setCourseOfStudies("Informatik");
        florian.setFreeText("");
        florian.setFormatDateOfBirth( 1991, 6, 6);
        florian.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "9"));
        florian.setHobby("karate");
        florian.setLinkToHomepage("");
        contactService.saveContact(florian);

        Contact sabine = new Contact();
        sabine.setFirstname("Sabine");
        sabine.setLastname("Müller");
        sabine.setOccupation("Manager");
        sabine.setEmail("SabineM@gmail.com");
        sabine.setCourseOfStudies("Informatik");
        sabine.setFreeText("");
        sabine.setFormatDateOfBirth(1985, 0, 9);
        sabine.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "10"));
        sabine.setHobby("Turnt gerne in ihrer Freizeit");
        sabine.setLinkToHomepage("");
        contactService.saveContact(sabine);

        Contact jana = new Contact();
        jana.setFirstname("Jana");
        jana.setLastname("Müller");
        jana.setOccupation("Angestellte");
        jana.setEmail("JanaM@yahoo.de");
        jana.setCourseOfStudies("Informatik");
        jana.setFreeText("");
        jana.setFormatDateOfBirth(1966, 11, 19);
        jana.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "11"));
        jana.setHobby("Schwimmen");
        jana.setLinkToHomepage("");
        contactService.saveContact(jana);

        Contact tristan = new Contact();
        tristan.setFirstname("Tristan");
        tristan.setLastname("Müller");
        tristan.setOccupation("Doktorant (Stand: 02.03.2020)");
        tristan.setEmail("TristanM@yahoo.de");
        tristan.setCourseOfStudies("Informatik");
        tristan.setFreeText("Forschungsgebiet: Simulationswissenschaft");
        tristan.setFormatDateOfBirth(1996, 8, 28);
        tristan.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "12"));
        tristan.setHobby("Entwickelt gerne spiele Apps für Android");
        tristan.setLinkToHomepage("");
        contactService.saveContact(tristan);

        Contact alfred = new Contact();
        alfred.setFirstname("Alfred");
        alfred.setLastname("Sigmar");
        alfred.setOccupation("Manager");
        alfred.setEmail("AlfredS@gmail.com");
        alfred.setCourseOfStudies("Informatik");
        alfred.setFreeText("");
        alfred.setFormatDateOfBirth(1957, 3, 3);
        alfred.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "13"));
        alfred.setHobby("");
        alfred.setLinkToHomepage("");
        contactService.saveContact(alfred);

        Contact aleyna = new Contact();
        aleyna.setFirstname("Aleyna");
        aleyna.setLastname("Turgut");
        aleyna.setOccupation("Angestellte");
        aleyna.setEmail("AleynaT@yahoo.de");
        aleyna.setCourseOfStudies("Informatik");
        aleyna.setFreeText("");
        aleyna.setFormatDateOfBirth(1992, 0, 01);
        aleyna.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "14"));
        aleyna.setHobby("Reiten");
        aleyna.setLinkToHomepage("");
        contactService.saveContact(aleyna);

        Contact milan = new Contact();
        milan.setFirstname("Milan");
        milan.setLastname("Russo");
        milan.setOccupation("Angestellter");
        milan.setEmail("MilanR@yahoo.de");
        milan.setCourseOfStudies("Informatik");
        milan.setFreeText("");
        milan.setFormatDateOfBirth(1980, 9, 10);
        milan.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "15"));
        milan.setHobby("");
        milan.setLinkToHomepage("");
        contactService.saveContact(milan);

        Contact maxW = new Contact();
        maxW.setFirstname("Max");
        maxW.setLastname("Williams");
        maxW.setOccupation("Angestellter");
        maxW.setEmail("MaxW@gmx.com");
        maxW.setCourseOfStudies("Philosophie");
        maxW.setFreeText("");
        maxW.setFormatDateOfBirth(1979, 7, 7);
        maxW.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "16"));
        maxW.setHobby("Joggen");
        maxW.setLinkToHomepage("");
        contactService.saveContact(maxW);

        Contact alex = new Contact();
        alex.setFirstname("Alex");
        alex.setLastname("Johnson");
        alex.setOccupation("Angestellter");
        alex.setEmail("AlexJ@yahoo.de");
        alex.setCourseOfStudies("Philosophie");
        alex.setFreeText("");
        alex.setFormatDateOfBirth(1990, 5, 7);
        alex.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "17"));
        alex.setHobby("Sammelt Briefmarken");
        alex.setLinkToHomepage("");
        contactService.saveContact(alex);

        Contact min = new Contact();
        min.setFirstname("Min");
        min.setLastname("Lee");
        min.setOccupation("Dolmetscherin");
        min.setEmail("MinL@yahoo.de");
        min.setCourseOfStudies("Philosophie");
        min.setFreeText("");
        min.setFormatDateOfBirth(1976, 9, 8);
        min.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "18"));
        min.setHobby("");
        min.setLinkToHomepage("");
        contactService.saveContact(min);

        Contact sofia = new Contact();
        sofia.setFirstname("Sofia");
        sofia.setLastname("Hengst");
        sofia.setOccupation("Angestellte");
        sofia.setEmail("SofiaH@gmx.com");
        sofia.setCourseOfStudies("Philosophie");
        sofia.setFreeText("");
        sofia.setFormatDateOfBirth(1993, 1, 1);
        sofia.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "19"));
        sofia.setHobby("Geht in ihrer Freizeit ins Fitnessstudio");
        sofia.setLinkToHomepage("");
        contactService.saveContact(sofia);

        Contact marlene = new Contact();
        marlene.setFirstname("Marlene");
        marlene.setLastname("Hengst");
        marlene.setOccupation("Angestellte");
        marlene.setEmail("MarleneH@gmx.com");
        marlene.setCourseOfStudies("Philosophie");
        marlene.setFreeText("");
        marlene.setFormatDateOfBirth(1995, 10, 1);
        marlene.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "20"));
        marlene.setHobby("Spielt gerne Geige");
        marlene.setLinkToHomepage("");
        contactService.saveContact(marlene);

        //example events
        Set<Contact> janaFlorian = new HashSet<>();
        janaFlorian.add(jana);
        janaFlorian.add(florian);

        Set<Contact> joseGuests = new HashSet<>();
        joseGuests.add(max);
        joseGuests.add(florian);
        joseGuests.add(anna);
        joseGuests.add(paulina);
        joseGuests.add(peter);

        Set<Contact> karate = new HashSet<>();
        karate.add(jana);
        karate.add(florian);
        karate.add(max);
        karate.add(jose);

        Set<Contact> yoga = new HashSet<>();
        yoga.add(jana);
        yoga.add(anna);
        yoga.add(sofia);
        yoga.add(marlene);

        Event picknick = new Event();
        picknick.setFormatDateOfEvent(2020, 6, 1);
        picknick.setEventName("Picknick");
        picknick.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "201"));
        picknick.setContacts(janaFlorian);
        picknick.setText("Florian und Jana haben sich in einer Vorlesung an der Uni Stuttgart kennengelernt.");
        eventService.saveEvent(picknick);

        Event joseBdayEvent = new Event();
        joseBdayEvent.setFormatDateOfEvent(2019, 11, 10);
        joseBdayEvent.setEventName("Josés 21 Geburtstag");
        joseBdayEvent.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "202"));
        joseBdayEvent.setContacts(joseGuests);
        joseBdayEvent.setText("José hat seinen 21 Geburtstag im Studentenwohnheim zusammen mit seinen engsten Freunden ");
        eventService.saveEvent(joseBdayEvent);

        Event karateSport = new Event();
        karateSport.setFormatDateOfEvent(2019, 05, 1);
        karateSport.setEventName("Uni Sport karate für Einsteiger");
        karateSport.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "203"));
        karateSport.setContacts(karate);
        karateSport.setText("Wöchentliches karate training für beginner");
        eventService.saveEvent(karateSport);

        Event yogaSport = new Event();
        yogaSport.setFormatDateOfEvent(2019, 05, 1);
        yogaSport.setEventName("Uni Sport Yoga für Fortgeschrittene");
        yogaSport.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "204"));
        yogaSport.setContacts(karate);
        yogaSport.setText("Wöchentliches Yoga für Fortgeschrittene");
        eventService.saveEvent(yogaSport);

        Set<Event> events = new HashSet<>();
        events.add(joseBdayEvent);
        events.add(picknick);
        events.add(karateSport);
        events.add(yogaSport);
        events.add(picknick);
        events.add(picknick);

        Contacthistory historyOne = new Contacthistory();
        historyOne.setText("Beim Kaffetrinken kennengelernt");
        historyOne.setContact(max);
        historyOne.setDate("13.04.2018");
        contacthistoryService.saveContacthistory(historyOne);

        //Example institutes plus the contacts which leads to them
        Set<Contact> deutscheBahnBerlinContacts = new HashSet<>();

        Institute deutscheBahnBerlin = new Institute();
        deutscheBahnBerlin.setName("Deutsche Bahn");
        deutscheBahnBerlin.setContacts(deutscheBahnBerlinContacts);
        deutscheBahnBerlin.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "101"));
        instituteService.saveInstitute(deutscheBahnBerlin);

        Set<Contact> mahleStuttgartContacts = new HashSet<>();
        mahleStuttgartContacts.add(jose);
        mahleStuttgartContacts.add(alex);
        mahleStuttgartContacts.add(min);
        mahleStuttgartContacts.add(maxW);
        mahleStuttgartContacts.add(sofia);
        mahleStuttgartContacts.add(marlene);

        Set<Contact> allianzStuttgartContacts = new HashSet<>();

        Institute allianzStuttgart = new Institute();
        allianzStuttgart.setName("Allianz AG, Niederlassung in Stuttgart");
        allianzStuttgart.setContacts(allianzStuttgartContacts);
        allianzStuttgart.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "102"));
        instituteService.saveInstitute(allianzStuttgart);

        Set<Contact> porscheStuttgartContacts = new HashSet<>();
        porscheStuttgartContacts.add(luisa);
        porscheStuttgartContacts.add(paulina);
        porscheStuttgartContacts.add(peter);

        Institute porscheStuttgart = new Institute();
        porscheStuttgart.setName("Porsche Niederlassung in Stuttgart");
        porscheStuttgart.setContacts(porscheStuttgartContacts);
        porscheStuttgart.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "103"));
        instituteService.saveInstitute(porscheStuttgart);

        Set<Contact> boschleinfeldenContacts = new HashSet<>();
        boschleinfeldenContacts.add(jonas);
        boschleinfeldenContacts.add(florian);
        boschleinfeldenContacts.add(sabine);
        boschleinfeldenContacts.add(jana);
        boschleinfeldenContacts.add(tristan);
        boschleinfeldenContacts.add(alfred);
        boschleinfeldenContacts.add(aleyna);
        boschleinfeldenContacts.add(milan);

        Institute boschLeinfelden = new Institute();
        boschLeinfelden.setName("Bosch Niederlassung in Leinfelden");
        boschLeinfelden.setContacts(boschleinfeldenContacts);
        boschLeinfelden.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "104"));
        instituteService.saveInstitute(boschLeinfelden);

        Set<Contact> mercedesFeuerbachContacts = new HashSet<>();
        mercedesFeuerbachContacts.add(max);
        mercedesFeuerbachContacts.add(anna);
        mercedesFeuerbachContacts.add(julia);

        Institute mercedesStuttgart = new Institute();
        mercedesStuttgart.setName("Daimler Benz Niederlassung Feuerbach");
        mercedesStuttgart.setContacts(mercedesFeuerbachContacts);
        mercedesStuttgart.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "105"));
        instituteService.saveInstitute(mercedesStuttgart);

        julia.addInstitutes(mercedesStuttgart);

        Institute mahleStuttgart = new Institute();
        mahleStuttgart.setName("MAHLE GmbH");
        mahleStuttgart.setContacts(mahleStuttgartContacts);
        mahleStuttgart.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "106"));
        instituteService.saveInstitute(mahleStuttgart);
    }
}