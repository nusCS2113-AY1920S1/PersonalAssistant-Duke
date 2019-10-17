package gazeeebo.commands.Modules;

public class ModuleDescriptions {
    /**
     * Gives a brief description of Computer Engineering modules and exam details
     */
    public ModuleDescriptions() {
        String LINE_BREAK = "------------------------------------------------------------------------------------\n";

        /*Module description for CS2113T*/
        System.out.println(LINE_BREAK + "C2113T: Software Engineering & Object-Oriented Programming\n");
        System.out.println(" This module introduces the necessary skills for systematic and rigorous development of software systems. " +
                "It covers requirements, design, implementation, quality assurance, and project management aspects of small-to-medium " +
                "size multi-person software projects. The module uses the Object Oriented Programming paradigm. Students of this module " +
                "will receive hands-on practice of tools commonly used in the industry, such as test automation tools, build automation tools, " +
                "and code revisioning tools will be covered.\n");

        System.out.println("Semester 1 Exam\n" +
                " 23-11-2019 1:00 PM - 2 hrs\n" +
                "\n" +
                "Semester 2 Exam\n" +
                " 02-05-2020 1:00 PM - 2 hrs");


        /*Module description for CS2101*/
        System.out.println(LINE_BREAK + "CS2101: Effective Communication for Computing Professionals\n");
        System.out.println(" This module aims to equip students with the skills needed to communicate technical information" +
                " to technical and nontechnical audiences, and to create comprehensible software documentation. " +
                "A student-centric approach will be adopted to encourage independent and collaborative learning " +
                "while engaging students in team-based projects. Students will learn interpersonal and" +
                "intercultural communication skills as well as hone their oral and written communication skills. " +
                "Assessment modes include a variety of oral and written communication tasks such as reports, software guides," +
                "oral presentations, software demonstrations and project blogs.\n");

        System.out.println("No Exams");

        /*Module description for CG2027*/
        System.out.println(LINE_BREAK + "CG2027: Transistor-level Digital Circuits\n");
        System.out.println(" Building on the basic circuit concepts introduced through CG1111 and CG1112, this module introduces the" +
                " fundamental concept of carriers, operating principles of PN diodes and MOSFETs. Their IV characteristic in " +
                "different operating region and their impact on the performance of logic gate will also be discussed. " +
                "It explains the foundational concepts of inverters and analyses their performance in terms of power and delay trade-off. " +
                "It also introduces logic synthesis and the fundamental timing analysis of logic gates. Besides the static CMOS logic, " +
                "it will also cover pass logics or transmission gates logics.\n");

        System.out.println("No Exams");

        /*Module description for CG2028*/
        System.out.println(LINE_BREAK + "CG2028: Computer Organization\n");
        System.out.println(" This module teaches students computer organization concepts and how to write efficient " +
                "microprocessor programs using assembly language. The course covers computer microarchitecture and memory system fundamentals, " +
                "and the ARM microprocessor instruction set. The course culminates in an assignment in which students design " +
                "and implement an efficient assembly language solution to an engineering problem.\n");

        System.out.println("No Exams");

        /*Module description for CG2271*/
        System.out.println(LINE_BREAK + "CG2271: Real-Time Operating Systems\n");
        System.out.println(" Real-time systems must respond quickly to inputs from the environment in order to work effectively and safely, " +
                "and realtime operating systems (RTOS) are a critical part of such systems. In this course the student is exposed to " +
                "basic RTOS concepts like tasks, scheduling algorithms, RTOS customisation and concurrent real-time programming. " +
                "By the end of this course a student will not only understand how an RTOS is built, " +
                "but will also gain practical hands-on experience in customising RTOSs and in writing real-time programs.\n");

        System.out.println("Semester 1 Exam\n" +
                " 26-11-2019 5:00 PM - 2 hrs\n" +
                "\n" +
                "Semester 2 Exam\n" +
                " 04-05-2020 1:00 PM - 2 hrs\n" + LINE_BREAK);
    }


}
