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

    @Autowired
    private RelationshipService relationshipService;


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
        julia.setLastname("Mueller");
        julia.setOccupation("Sekretärin");
        julia.setEmail("juliaM@yahoo.de");
        julia.setCourseOfStudies("BWL");
        julia.setFreeText("Julia arbeitet 2 mal in der Woche ehrenamtlich im Altersheim (Stand: 01.02.2019");
        julia.setDayOfBirth(1977, 2, 12);
        julia.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "2"));
        julia.setHobby("Macht gerne Ballet");
        julia.setLinkToHomepage("");
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
        anna.setDayOfBirth(1989, 11, 8);
        anna.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "3"));
        anna.setHobby("Lesen");
        anna.setLinkToHomepage("");
        contactService.saveContact(anna);

        Contact max = new Contact();
        max.setFirstname("Max");
        max.setLastname("Mustermann");
        max.setOccupation("Projektmanager");
        max.setEmail("maxM@yahoo.de");
        max.setCourseOfStudies("Wirtschaftsinformatik");
        max.setFreeText("Semestersprecher des fünften Bachelor Semesters in SS99 ");
        max.setDayOfBirth(1986, 1, 01);
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
        jose.setDayOfBirth(1998, 10, 10);
        jose.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "4"));
        jose.setHobby("Schreibt gerne Fabeln");
        jose.setLinkToHomepage("");
        contactService.saveContact(jose);

        Contact luisa = new Contact();
        luisa.setFirstname("Luisa");
        luisa.setLastname("Mayer");
        luisa.setOccupation("Doktorantin");
        luisa.setEmail("luisaM@yahoo.de");
        luisa.setCourseOfStudies("Chemie");
        luisa.setFreeText("Promovierte 2020 im Gebiet der organischen Chemie");
        luisa.setDayOfBirth(1992, 5, 13);
        luisa.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "5"));
        luisa.setHobby("");
        luisa.setLinkToHomepage("");
        contactService.saveContact(luisa);

        Contact paulina = new Contact();
        paulina.setFirstname("Paulina");
        paulina.setLastname("Josefine");
        paulina.setOccupation("Angestellte");
        paulina.setEmail("PaulinaJ@gmail.com");
        paulina.setCourseOfStudies("Chemie");
        paulina.setFreeText("");
        paulina.setDayOfBirth(1989, 9, 30);
        paulina.setAddress(new Address("12345", "Musterstadt", "Hans-Otto-Rüdiger-Straße", "6"));
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
        peter.setDayOfBirth(2001, 9, 28);
        peter.setAddress(new Address("1234", "Muster-Stadt", "Sigmaringerstraße", "154/1"));
        peter.setHobby("");
        peter.setLinkToHomepage("");
        contactService.saveContact(peter);

        Contact jonas = new Contact();
        jonas.setFirstname("Jonas Alfred");
        jonas.setLastname("Homm");
        jonas.setOccupation("Professor");
        jonas.setEmail("JonasH@yahoo.de");
        jonas.setCourseOfStudies("Informatik");
        jonas.setFreeText("Forschungsbereiche: Informationstechnologie");
        jonas.setDayOfBirth(1996, 1, 1);
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
        florian.setDayOfBirth( 1991, 6, 6);
        florian.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "9"));
        florian.setHobby("karate");
        florian.setLinkToHomepage("");
        contactService.saveContact(florian);

        Contact sabine = new Contact();
        sabine.setFirstname("Sabine");
        sabine.setLastname("Mueller");
        sabine.setOccupation("Manager");
        sabine.setEmail("SabineM@gmail.com");
        sabine.setCourseOfStudies("Informatik");
        sabine.setFreeText("");
        sabine.setDayOfBirth(1985, 1, 9);
        sabine.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "10"));
        sabine.setHobby("Turnt gerne in ihrer Freizeit");
        sabine.setLinkToHomepage("");
        contactService.saveContact(sabine);

        Contact jana = new Contact();
        jana.setFirstname("Jana");
        jana.setLastname("Mueller");
        jana.setOccupation("Angestellte");
        jana.setEmail("JanaM@yahoo.de");
        jana.setCourseOfStudies("Informatik");
        jana.setFreeText("");
        jana.setDayOfBirth(1966, 11, 19);
        jana.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "11"));
        jana.setHobby("Schwimmen");
        jana.setLinkToHomepage("");
        contactService.saveContact(jana);

        Contact tristan = new Contact();
        tristan.setFirstname("Tristan");
        tristan.setLastname("Mueller");
        tristan.setOccupation("Doktorant (Stand: 02.03.2020)");
        tristan.setEmail("TristanM@yahoo.de");
        tristan.setCourseOfStudies("Informatik");
        tristan.setFreeText("Forschungsgebiet: Simulationswissenschaft");
        tristan.setDayOfBirth(1996, 8, 28);
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
        alfred.setDayOfBirth(1957, 3, 3);
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
        aleyna.setDayOfBirth(1992, 1, 01);
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
        milan.setDayOfBirth(1980, 9, 10);
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
        maxW.setDayOfBirth(1979, 7, 7);
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
        alex.setDayOfBirth(1990, 5, 7);
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
        min.setDayOfBirth(1976, 9, 8);
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
        sofia.setDayOfBirth(1993, 1, 1);
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
        marlene.setDayOfBirth(1995, 10, 1);
        marlene.setAddress(new Address("12345", "Musterstadt", "Musterstraße", "20"));
        marlene.setHobby("Spielt gerne Geige");
        marlene.setLinkToHomepage("");
        contactService.saveContact(marlene);

        //Example contacthistories
        //Creates new entry of Contacthistory Dates. An entry can contain multiple Contacts, Connects Contacthistory to Contact.
        Contacthistory historyOneDates = new Contacthistory();
        historyOneDates.addContacthistoryContact(max);
        historyOneDates.addContacthistoryContact(julia);
        historyOneDates.setText("Beim Kaffetrinken kennengelernt");
        historyOneDates.setDate(2018, 04, 13);
        contacthistoryService.saveContacthistory(historyOneDates);

        Contacthistory historyTwoDates = new Contacthistory();
        historyTwoDates.addContacthistoryContact(alfred);
        historyTwoDates.addContacthistoryContact(tristan);
        historyTwoDates.addContacthistoryContact(sabine);
        historyTwoDates.setDate(2020, 9, 30);
        historyTwoDates.setText("Beim Teetrinken getroffen");
        contacthistoryService.saveContacthistory(historyTwoDates);

        Contacthistory historyThreeDates = new Contacthistory();
        historyThreeDates.addContacthistoryContact(alex);
        historyThreeDates.addContacthistoryContact(anna);
        historyThreeDates.addContacthistoryContact(jana);
        historyThreeDates.addContacthistoryContact(peter);
        historyThreeDates.setDate(2019, 12, 12);
        historyThreeDates.setText("Auf der Weihnachtsfeier gesehen");
        contacthistoryService.saveContacthistory(historyThreeDates);

        Contacthistory historyFourDates = new Contacthistory();
        historyFourDates.addContacthistoryContact(marlene);
        historyFourDates.addContacthistoryContact(aleyna);
        historyFourDates.addContacthistoryContact(florian);
        historyFourDates.setDate(2019, 07, 27);
        historyFourDates.setText("In der Stadt getroffen");
        contacthistoryService.saveContacthistory(historyFourDates);

        Set<Contacthistory> historyOne = new HashSet<>();
        historyOne.add(historyOneDates);

        Set<Contacthistory> historyTwo = new HashSet<>();
        historyTwo.add(historyTwoDates);

        Set<Contacthistory> historyThree = new HashSet<>();
        historyThree.add(historyThreeDates);

        Set<Contacthistory> historyFour = new HashSet<>();
        historyFour.add(historyFourDates);

        //Connects Contact to Contacthistories. One Contact can have multiple entries in Contacthistories.
        max.setContacthistories(historyOne);
        contactService.saveContact(max);

        julia.setContacthistories(historyOne);
        contactService.saveContact(julia);

        alfred.setContacthistories(historyTwo);
        contactService.saveContact(alfred);

        tristan.setContacthistories(historyTwo);
        contactService.saveContact(tristan);

        sabine.setContacthistories(historyTwo);
        contactService.saveContact(sabine);

        alex.setContacthistories(historyThree);
        contactService.saveContact(alex);

        anna.setContacthistories(historyThree);
        contactService.saveContact(anna);

        jana.setContacthistories(historyThree);
        contactService.saveContact(jana);

        peter.setContacthistories(historyThree);
        contactService.saveContact(peter);


        marlene.setContacthistories(historyFour);
        contactService.saveContact(marlene);

        aleyna.setContacthistories(historyFour);
        contactService.saveContact(aleyna);

        florian.setContacthistories(historyFour);
        contactService.saveContact(florian);

        //example events
        Event picknick = new Event();
        picknick.setDate(2020, 06, 06);
        picknick.setEventName("Picknick");
        picknick.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "201"));
        picknick.addEventContact(jana);
        picknick.addEventContact(florian);
        picknick.setText("Florian und Jana haben sich auf einer Firmenfeier kennengelernt.");
        eventService.saveEvent(picknick);

        Event joseBdayEvent = new Event();
        joseBdayEvent.setDate(2019, 10, 11);
        joseBdayEvent.setEventName("Josés 21 Geburtstag");
        joseBdayEvent.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "202"));
        joseBdayEvent.addEventContact(max);
        joseBdayEvent.addEventContact(florian);
        joseBdayEvent.addEventContact(anna);
        joseBdayEvent.addEventContact(paulina);
        joseBdayEvent.addEventContact(peter);
        joseBdayEvent.setText("José hat seinen 21 Geburtstag im Studentenwohnheim zusammen mit seinen engsten Freunden ");
        eventService.saveEvent(joseBdayEvent);

        Event karateSport = new Event();
        karateSport.setDate(2019, 5, 01);
        karateSport.setEventName("Uni Sport karate für Einsteiger");
        karateSport.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "203"));
        karateSport.addEventContact(jana);
        karateSport.addEventContact(florian);
        karateSport.addEventContact(max);
        karateSport.addEventContact(jose);
        karateSport.setText("Wöchentliches karate training für Beginner");
        eventService.saveEvent(karateSport);

        Event yogaSport = new Event();
        yogaSport.setDate(2019, 05, 01);
        yogaSport.setEventName("Uni Sport Yoga für Fortgeschrittene");
        yogaSport.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "204"));
        yogaSport.addEventContact(jana);
        yogaSport.addEventContact(anna);
        yogaSport.addEventContact(sofia);
        yogaSport.addEventContact(marlene);
        yogaSport.setText("Wöchentliches Yoga für Fortgeschrittene");
        eventService.saveEvent(yogaSport);

        //Example institutes
        Institute deutscheBahnBerlin = new Institute();
        deutscheBahnBerlin.setName("Deutsche Bahn");
        deutscheBahnBerlin.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "101"));
        instituteService.saveInstitute(deutscheBahnBerlin);

        Institute allianzStuttgart = new Institute();
        allianzStuttgart.setName("Allianz AG, Niederlassung in Stuttgart");
        allianzStuttgart.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "102"));
        instituteService.saveInstitute(allianzStuttgart);

        Institute porscheStuttgart = new Institute();
        porscheStuttgart.setName("Porsche Niederlassung in Stuttgart");
        porscheStuttgart.addInstitutionContacts(luisa);
        porscheStuttgart.addInstitutionContacts(paulina);
        porscheStuttgart.addInstitutionContacts(peter);
        porscheStuttgart.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "103"));
        instituteService.saveInstitute(porscheStuttgart);

        Institute boschLeinfelden = new Institute();
        boschLeinfelden.setName("Bosch Niederlassung in Leinfelden");
        boschLeinfelden.addInstitutionContacts(jonas);
        boschLeinfelden.addInstitutionContacts(florian);
        boschLeinfelden.addInstitutionContacts(sabine);
        boschLeinfelden.addInstitutionContacts(jana);
        boschLeinfelden.addInstitutionContacts(tristan);
        boschLeinfelden.addInstitutionContacts(alfred);
        boschLeinfelden.addInstitutionContacts(aleyna);
        boschLeinfelden.addInstitutionContacts(milan);
        boschLeinfelden.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "104"));
        instituteService.saveInstitute(boschLeinfelden);

        Institute mercedesStuttgart = new Institute();
        mercedesStuttgart.setName("Daimler Benz Niederlassung Feuerbach");
        mercedesStuttgart.addInstitutionContacts(max);
        mercedesStuttgart.addInstitutionContacts(anna);
        mercedesStuttgart.addInstitutionContacts(julia);
        mercedesStuttgart.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "105"));
        instituteService.saveInstitute(mercedesStuttgart);

        Institute mahleStuttgart = new Institute();
        mahleStuttgart.setName("MAHLE GmbH");
        mahleStuttgart.addInstitutionContacts(jose);
        mahleStuttgart.addInstitutionContacts(alex);
        mahleStuttgart.addInstitutionContacts(min);
        mahleStuttgart.addInstitutionContacts(maxW);
        mahleStuttgart.addInstitutionContacts(sofia);
        mahleStuttgart.addInstitutionContacts(marlene);
        mahleStuttgart.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "106"));
        instituteService.saveInstitute(mahleStuttgart);

        //Test data for relationships between contacts
        Relationship heirat = new Relationship();
        heirat.setContactA(max);
        heirat.setContactB(anna);
        heirat.setSince(2010, 01, 30);
        heirat.setTypeOfRelationship("Verheiratet");
        relationshipService.saveRelationship(heirat);
    }
}