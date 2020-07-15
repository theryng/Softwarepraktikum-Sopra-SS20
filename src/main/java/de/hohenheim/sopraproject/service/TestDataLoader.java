package de.hohenheim.sopraproject.service;

import de.hohenheim.sopraproject.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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
    private ContactHistoryService contactHistoryService;

    @Autowired
    private EventService eventService;

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private TagsService tagsService;

    @Autowired
    private EditingHistoryService editingHistoryService;


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

        //Role data
        Role userRole = new Role("ROLE_USER");
        Role adminRole = new Role("ROLE_ADMIN");
        roleService.saveRole(userRole);
        roleService.saveRole(adminRole);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);

        //Userdata for the program
        User normalUser3 = new User();
        normalUser3.setUsername("mark1010");
        normalUser3.setPassword(passwordEncoder.encode("Wagner1"));
        normalUser3.setRoles(userRoles);
        normalUser3.setFirstName("Mark");
        normalUser3.setLastName("Wagner");
        userService.saveUser(normalUser3);

        User normalUser4 = new User();
        normalUser4.setUsername("Criss");
        normalUser4.setPassword(passwordEncoder.encode("Hallo1"));
        normalUser4.setRoles(userRoles);
        normalUser4.setFirstName("Chris");
        normalUser4.setLastName("Hasselbach");
        userService.saveUser(normalUser4);

        User normalUser5 = new User();
        normalUser5.setUsername("Rusk");
        normalUser5.setPassword(passwordEncoder.encode("Passwort1"));
        normalUser5.setRoles(userRoles);
        normalUser5.setFirstName("Sergej");
        normalUser5.setLastName("Bensack");
        userService.saveUser(normalUser5);

        User normalUser6 = new User();
        normalUser6.setUsername("_Luk");
        normalUser6.setPassword(passwordEncoder.encode("TollesPasswort7"));
        normalUser6.setRoles(userRoles);
        normalUser6.setFirstName("Lukas");
        normalUser6.setLastName("Januschke");
        userService.saveUser(normalUser5);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRoles(adminRoles);
        admin.setIsAdmin(true);
        admin.setFirstName("Max");
        admin.setLastName("Mustermann");
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
        luisa.setFirstname("Luisa-müller");
        luisa.setLastname("Mayer-Übler");
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
        ContactHistory historyOneDates = new ContactHistory();
        historyOneDates.addContactHistoryContact(max);
        historyOneDates.addContactHistoryContact(julia);
        historyOneDates.setText("Beim Kaffetrinken in der Mensa kennengelernt");
        historyOneDates.setDate(LocalDate.of(2018, 4, 13));
        contactHistoryService.saveContacthistory(historyOneDates);
        max.setLastContact(LocalDate.of(2018, 4, 13));
        julia.setLastContact(LocalDate.of(2018, 4, 13));

        ContactHistory historyTwoDates = new ContactHistory();
        historyTwoDates.addContactHistoryContact(alfred);
        historyTwoDates.addContactHistoryContact(tristan);
        historyTwoDates.addContactHistoryContact(sabine);
        historyTwoDates.setDate(LocalDate.of(2020, 9, 30));
        historyTwoDates.setText("Beim Teetrinken in der Mensa getroffen");
        contactHistoryService.saveContacthistory(historyTwoDates);
        alfred.setLastContact(LocalDate.of(2019, 9, 30));
        tristan.setLastContact(LocalDate.of(2019, 9, 30));
        sabine.setLastContact(LocalDate.of(2019, 9, 30));

        ContactHistory historyThreeDates = new ContactHistory();
        historyThreeDates.addContactHistoryContact(alex);
        historyThreeDates.addContactHistoryContact(anna);
        historyThreeDates.addContactHistoryContact(jana);
        historyThreeDates.addContactHistoryContact(peter);
        historyThreeDates.setDate(LocalDate.of(2019, 12, 12));
        historyThreeDates.setText("Auf der Weihnachtsfeier gesehen");
        contactHistoryService.saveContacthistory(historyThreeDates);
        alex.setLastContact(LocalDate.of(2019, 12, 12));
        anna.setLastContact(LocalDate.of(2019, 12, 12));
        jana.setLastContact(LocalDate.of(2019, 12, 12));
        peter.setLastContact(LocalDate.of(2019, 12, 12));

        ContactHistory historyFourDates = new ContactHistory();
        historyFourDates.addContactHistoryContact(marlene);
        historyFourDates.addContactHistoryContact(aleyna);
        historyFourDates.addContactHistoryContact(florian);
        historyFourDates.setDate(LocalDate.of(2019, 07, 27));
        historyFourDates.setText("In der Stadt getroffen");
        contactHistoryService.saveContacthistory(historyFourDates);
        marlene.setLastContact(LocalDate.of(2019, 07, 27));
        aleyna.setLastContact(LocalDate.of(2019, 07, 27));
        florian.setLastContact(LocalDate.of(2019, 07, 27));

        Set<ContactHistory> historyOne = new HashSet<>();
        historyOne.add(historyOneDates);

        Set<ContactHistory> historyTwo = new HashSet<>();
        historyTwo.add(historyTwoDates);

        Set<ContactHistory> historyThree = new HashSet<>();
        historyThree.add(historyThreeDates);

        Set<ContactHistory> historyFour = new HashSet<>();
        historyFour.add(historyFourDates);

        Tags student = new Tags();
        student.setName("Student");
        student.getContacts().add(max);
        student.getContacts().add(julia);
        student.getContacts().add(alfred);
        student.getContacts().add(tristan);
        student.getContacts().add(sabine);
        student.getContacts().add(alex);
        tagsService.saveTags(student);

        Tags mitarbeiter = new Tags();
        mitarbeiter.setName("Mitarbeiter");
        mitarbeiter.getContacts().add(jose);
        mitarbeiter.getContacts().add(anna);
        mitarbeiter.getContacts().add(jana);
        mitarbeiter.getContacts().add(florian);
        mitarbeiter.getContacts().add(sofia);
        mitarbeiter.getContacts().add(marlene);
        tagsService.saveTags(mitarbeiter);

        Tags tutor = new Tags();
        tutor.setName("Tutoren");
        tutor.getContacts().add(jose);
        tutor.getContacts().add(peter);
        tagsService.saveTags(tutor);

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
        picknick.setDate(2020, 06, 01);
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
        karateSport.setEventName("Karate für Einsteiger");
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
        yogaSport.setEventName("Yoga für Fortgeschrittene");
        yogaSport.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "204"));
        yogaSport.addEventContact(jana);
        yogaSport.addEventContact(anna);
        yogaSport.addEventContact(sofia);
        yogaSport.addEventContact(marlene);
        yogaSport.setText("Wöchentliches Yoga für Fortgeschrittene");
        eventService.saveEvent(yogaSport);

        Event zukunftDerLehre = new Event();
        zukunftDerLehre.setDate(2020, 7, 01);
        zukunftDerLehre.setEventName("Zukunft der Lehre");
        zukunftDerLehre.setAddress(new Address("70578", "Stuttgart", "Garbenstraße",
                "30"));
        zukunftDerLehre.addEventContact(florian);
        zukunftDerLehre.addEventContact(jana);
        zukunftDerLehre.addEventContact(sabine);
        zukunftDerLehre.addEventContact(peter);
        zukunftDerLehre.setText("Informationsveranstaltung wie die zukünftige Lehre aussehen könnte");
        eventService.saveEvent(zukunftDerLehre);

        Event frisbee = new Event();
        frisbee.setDate(2019, 5, 01);
        frisbee.setEventName("Frisbee werfen für Anfänger");
        frisbee.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "203"));
        frisbee.addEventContact(aleyna);
        frisbee.addEventContact(sabine);
        frisbee.addEventContact(luisa);
        frisbee.addEventContact(jose);
        frisbee.setText("'Schnupperkurs Frisbee werfen'");
        eventService.saveEvent(frisbee);

        //Example institutes
        Institute zalando = new Institute();
        zalando.setName("Zalando");
        zalando.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "1010"));
        zalando.setLinkToHomepage("www.zalando.de");
        zalando.setEmail("zalando@web.de");
        instituteService.saveInstitute(zalando);

        Institute deutscheBahnBerlin = new Institute();
        deutscheBahnBerlin.setName("Deutsche Bahn");
        deutscheBahnBerlin.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "101"));
        deutscheBahnBerlin.setLinkToHomepage("www.deutsch-Bahn-Berlin.de");
        deutscheBahnBerlin.setEmail("deutschebahn@web.de");
        instituteService.saveInstitute(deutscheBahnBerlin);

        Institute allianzStuttgart = new Institute();
        allianzStuttgart.setName("Allianz AG, Niederlassung in Stuttgart");
        allianzStuttgart.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "102"));
        allianzStuttgart.setLinkToHomepage("www.allianz.de");
        allianzStuttgart.setEmail("allianz@web.de");
        instituteService.saveInstitute(allianzStuttgart);

        Institute porscheStuttgart = new Institute();
        porscheStuttgart.setName("Porsche Niederlassung in Stuttgart");
        porscheStuttgart.addInstitutionContacts(luisa);
        porscheStuttgart.addInstitutionContacts(paulina);
        porscheStuttgart.addInstitutionContacts(peter);
        porscheStuttgart.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "103"));
        porscheStuttgart.setEmail("prosche@web.de");
        porscheStuttgart.setLinkToHomepage("www.Porsche.de");
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
        boschLeinfelden.setEmail("bosch@web.de");
        boschLeinfelden.setLinkToHomepage("www.Bosch.de");
        instituteService.saveInstitute(boschLeinfelden);

        Institute mercedesStuttgart = new Institute();
        mercedesStuttgart.setName("Daimler Benz Niederlassung Feuerbach");
        mercedesStuttgart.addInstitutionContacts(max);
        mercedesStuttgart.addInstitutionContacts(anna);
        mercedesStuttgart.addInstitutionContacts(julia);
        mercedesStuttgart.setAddress(new Address("12345", "Musterstadt", "Musterstraße",
                "105"));
        mercedesStuttgart.setEmail("mercedes@web.de");
        mercedesStuttgart.setLinkToHomepage("www.Mercedes.de");
        instituteService.saveInstitute(mercedesStuttgart);

        Institute sap = new Institute();
        sap.setName("SAP");
        sap.addInstitutionContacts(jana);
        sap.addInstitutionContacts(peter);
        sap.setAddress(new Address("55554", "Fritzhausen", "Carl-Otto-Weg", "1"));
        sap.setLinkToHomepage("www.sap.de");
        sap.setEmail("sap@web.de");
        instituteService.saveInstitute(sap);

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
        mahleStuttgart.setEmail("mahle@web.de");
        mahleStuttgart.setLinkToHomepage("www.mahle.de");
        instituteService.saveInstitute(mahleStuttgart);

        //Test data for relationships between contacts
        Relationship heirat = new Relationship();
        heirat.setContactA(max);
        heirat.setContactB(anna);
        heirat.setSinceDate(2010, 01, 30);
        heirat.setTypeOfRelationship("Verheiratet");
        relationshipService.saveRelationship(heirat);

        Project covid = new Project();
        covid.setName("Corona tracking App");
        covid.setDescription("Entwickeln einer Corona tracking App to flatten the curve");
        covid.setSince(2020, 04, 20);
        covid.addProjectContacts(max);
        covid.addProjectContacts(jana);
        covid.addProjectInstitutes(sap);
        projectService.saveProject(covid);

        Meeting erstieEinführung = new Meeting();
        erstieEinführung.setDate(LocalDate.now());
        erstieEinführung.setDescription("Blaue Krawatte anziehen");
        erstieEinführung.setStartTime(9,30);
        erstieEinführung.setEndTime(10,30);
        erstieEinführung.setTitle("Erstsemester Begrüßung");
        erstieEinführung.addContact(jana);
        meetingService.saveMeeting(erstieEinführung);

        Project campus = new Project();
        campus.setName("Campussystem Erneuerung");
        campus.setDescription("Das komplette Campussystem wird erneuert");
        campus.setSince(2020, 06, 30);
        campus.addProjectContacts(florian);
        campus.addProjectContacts(alex);
        campus.addProjectInstitutes(sap);
        projectService.saveProject(campus);

        Project liquidLehre = new Project();
        liquidLehre.setName("Liquid Lehre App");
        liquidLehre.setDescription("Entwickeln einer App zur besseren Kommunikation zwischen Studierenden und Dozenten.");
        liquidLehre.setSince(2018, 10, 20);
        liquidLehre.addProjectContacts(aleyna);
        liquidLehre.addProjectContacts(jana);
        liquidLehre.addProjectInstitutes(sap);
        projectService.saveProject(liquidLehre);

        Project verschoenern = new Project();
        verschoenern.setName("Verschönern des Campus");
        verschoenern.setDescription("Der Campus der Uni Hohenheim soll schöner und grüner werden.");
        verschoenern.setSince(2020, 04, 20);
        verschoenern.addProjectContacts(jonas);
        verschoenern.addProjectContacts(jose);
        projectService.saveProject(verschoenern);

        Project umweltschutz = new Project();
        umweltschutz.setName("Umweltschutzengagement");
        umweltschutz.setDescription("Rettet die Bäume! Esst mehr Biber");
        umweltschutz.setSince(2020  ,05 , 14);
        umweltschutz.addProjectContacts(alex);
        umweltschutz.addProjectContacts(aleyna);
        umweltschutz.addProjectContacts(alfred);
        umweltschutz.addProjectContacts(anna);
        projectService.saveProject(umweltschutz);

        Project gegenRassismus = new Project();
        gegenRassismus.setName("Uni gegen Rassismus");
        gegenRassismus.setDescription("Sensibilisieren der Kommilitonen bezüglich des Themas Rassismus");
        gegenRassismus.setSince(2020, 07, 20);
        gegenRassismus.addProjectContacts(peter);
        gegenRassismus.addProjectContacts(sofia);
        gegenRassismus.addProjectContacts(sabine);
        projectService.saveProject(gegenRassismus);

        Meeting meditation = new Meeting();
        meditation.setDate(LocalDate.now());
        meditation.setDescription("");
        meditation.setStartTime(10,40);
        meditation.setEndTime(11,0);
        meditation.setTitle("Begleitender Meditationskurs für den Stressabbau");
        meetingService.saveMeeting(meditation);

        Meeting mensa = new Meeting();
        mensa.setDate(LocalDate.now());
        mensa.setDescription("");
        mensa.setStartTime(14,10);
        mensa.setEndTime(14,30);
        mensa.setTitle("Mittagessen mit Herr Fischer");
        meetingService.saveMeeting(mensa);

        Meeting mcFit = new Meeting();
        mcFit.setDate(LocalDate.now());
        mcFit.setDescription("Wilhelmsplatz 11, 70182 Stuttgart");
        mcFit.setStartTime(13,10);
        mcFit.setEndTime(14,20);
        mcFit.setTitle("Probetraining McFit");
        meetingService.saveMeeting(mcFit);

        Meeting meetingGerdt = new Meeting();
        meetingGerdt.setDate(LocalDate.now());
        meetingGerdt.setDescription("Klimaschutz");
        meetingGerdt.setStartTime(8,30);
        meetingGerdt.setEndTime(9,20);
        meetingGerdt.setTitle("Webex Meeting mit Prof. Dr. Gerdt");
        meetingService.saveMeeting(meetingGerdt);

        Meeting vortragMüller = new Meeting();
        vortragMüller.setDate(LocalDate.now());
        vortragMüller.setDescription("Prof. Dr. Müller, Audimax");
        vortragMüller.setStartTime(12,30);
        vortragMüller.setEndTime(13,0);
        vortragMüller.setTitle("Treibhausgas Emissionssenkung");
        vortragMüller.addContact(jonas);
        vortragMüller.addContact(max);
        meetingService.saveMeeting(vortragMüller);

        Meeting begrüßungGastprofessoren = new Meeting();
        begrüßungGastprofessoren.setDate(LocalDate.now());
        begrüßungGastprofessoren.setDescription("Aus Schweden, Geschenk nicht Vergessen!");
        begrüßungGastprofessoren.setStartTime(15,45);
        begrüßungGastprofessoren.setEndTime(16,0);
        begrüßungGastprofessoren.setTitle("Gastprofessorin Prof. Dr. Andersson begrüßen");
        meetingService.saveMeeting(begrüßungGastprofessoren);

        Meeting professorenMeeting = new Meeting();
        professorenMeeting.setDate(LocalDate.now());
        professorenMeeting.setDescription("Zum Informationsaustausch");
        professorenMeeting.setStartTime(17,30);
        professorenMeeting.setEndTime(19,30);
        professorenMeeting.setTitle("Meeting mit Prof. Dr. Smith \"empirischen Forschung\"");
        meetingService.saveMeeting(professorenMeeting);

        Meeting vortragMayer = new Meeting();
        vortragMayer.setDate(LocalDate.now());
        vortragMayer.setDescription("Wissenschaftlicher Vortrag");
        vortragMayer.setStartTime(19,30);
        vortragMayer.setEndTime(20,0);
        vortragMayer.setTitle("Vortrag Prof. Dr. Mayer");
        meetingService.saveMeeting(vortragMayer);

        EditingHistory editing1 = new EditingHistory();
        editing1.setObjectEdited("Kontakt: Peter Lustig");
        editing1.setDate("2020-07-15");
        editing1.setUser("admin");

        EditingHistory editing2 = new EditingHistory();
        editing2.setObjectEdited("Event: Kaffeetrinken");
        editing2.setDate("2019-09-02");
        editing2.setUser("user2");

        EditingHistory editing3 = new EditingHistory();
        editing3.setObjectEdited("Kontakt: Jana Mueller");
        editing3.setDate("2020-07-12");
        editing3.setUser("admin");

        EditingHistory editing4 = new EditingHistory();
        editing4.setObjectEdited("Kontakt: Klaus Peter");
        editing4.setDate("2020-07-10");
        editing4.setUser("user1");

        editingHistoryService.saveEditingHistory(editing1);
        editingHistoryService.saveEditingHistory(editing2);
        editingHistoryService.saveEditingHistory(editing3);
        editingHistoryService.saveEditingHistory(editing4);
    }
}