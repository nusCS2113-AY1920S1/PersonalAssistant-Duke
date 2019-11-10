//@@author e0323290

package gazeeebo.commands.specialization;

import java.util.ArrayList;
import java.util.Map;

/**
 * Pushes all specializations and technical electives to specMap.
 */
public class ListOfSpecializationAndModules {
    /**
     * Stores all the technical electives and specializations.
     * in this case storing data in the expenses map
     *
     * @param specMap the map that map list of technical electives to their
     *                respective specializations
     */
    public ListOfSpecializationAndModules(final Map<String,
            ArrayList<ModuleCategory>> specMap) {
        final int commsAndNetworkingIndex = 1;
        final int embeddedComputingIndex = 2;
        final int intelligentSystemsIndex = 3;
        final int interactiveDigitalMediaIndex = 4;
        final int largeScaleComputingIndex = 5;
        final int sysDesignIndex = 6;
        ArrayList<String> specList = new ArrayList<>();
        specList.add("Communications & Networking"); //index 0
        specList.add("Embedded Computing"); //index 1
        specList.add("Intelligent Systems"); //index 2
        specList.add("Interactive Digital Media"); //index 3
        specList.add("Large-Scale Computing"); //index 4
        specList.add("System-On-A-Chip Design"); //index 5

        ArrayList<ModuleCategory> commsAndNetworkingBD = new ArrayList();
        ArrayList<ModuleCategory> embComputingBD = new ArrayList();
        ArrayList<ModuleCategory> intSystemsBD = new ArrayList();
        ArrayList<ModuleCategory> intDigMediaBD = new ArrayList();
        ArrayList<ModuleCategory> largeScaleComputingBD = new ArrayList();
        ArrayList<ModuleCategory> sysDesignBD = new ArrayList();

        String cs2107 = "CS2107 Introduction to Information System";
        String cs3103 = "CS3103 Computer Networks Practice";
        String ee3131C = "EE3131C Communication Systems";
        String cs4222 = "CS4222 Wireless Networking";
        String cs4226 = "CS4226 Internet Architecture";
        String ee4210 = "EE4210 Network Protocols and Applications";
        String cs5223 = "CS5223 Distributed Systems";
        String cs5321 = "CS5321 Network Security";
        String eE5135 = "EE5135 Digital Communications";

        String cg3207 = "CG3207 Computing Architecture";
        String cs3211 = "CS3211 Parallel and Concurrent Programming";
        String ee3731C = "EE3731C Signal Processing Methods";
        String cs4223 = "CS4223 Multi-core Architectures";
        String ee4218 = "EE4218 Embedded Hardware System Design";
        String ee4415 = "EE4218 Embedded Hardware System Design";
        String cs5272 = "CS5272 Embedded Software Design";
        String ee5903 = "EE5903 Real-time Systems";

        String cs3243 = "CS3243 Introduction to Artificial Intelligence";
        String cs3244 = "CS3244 Machine Learning";
        String ee3331C = "EE3331C Feedback Control Systems";
        String cs4244 = "CS4244 Knowledge-based Systems";
        String cs4246 = "CS4246 AI Planning and Decision Making";
        String cs4248 = "CS4248 Natural Language Processing";
        String ee4305 = "EE4305 Fuzzy/Neural Systems for Intelligent Robotics";
        String ee4308 = "EE4308 Autonomous Robot Systems";
        String cs5242 = "CS5242 Neural Networks & Deep Learning";
        String cs5339 = "CS5339 Theory and Algorithms for Machine Learning";
        String ee5904 = "EE5904 Neural Networks";
        String ee5907 = "EE5907 Pattern Recognition";

        String cs2108 = "CS2108 Introduction to Media Computing";
        String cs3240 = "CS3240 Interaction Design";
        String cs3241 = "CS3241 Computer Graphics";
        String cs3242 = "CS3242 3D Modeling and Animation";
        String cs3247 = "CS3247 Game Development";
        String cs4240 = "CS4240 Interaction Design"
                + "for Virtual and Augmented Reality";
        String cs4243 = "CS4243 Computer Vision and Pattern Recognition";
        String cs4247 = "CS4247 Graphics Rendering Techniques";
        String cs4249 = "CS4249 Phenomena and Theories of"
                + "Human-Computer Interaction";
        String cs4347 = "CS4347 Sound and Music Computing";
        String cs4351 = "CS4351 Real-Time Graphics";
        String ee4212 = "EE4212 Computer Vision";
        String ee4604 = "EE4604 Biological Perception in Digital Media";
        String ee4704 = "EE4704 Introduction to"
                + "Computer Vision and Image Processing";

        String cs2101 = "CS2101 Database Systems";
        String cs3210 = "CS3210 Parallel Computing";
        String cs3230 = "CS3230 Design & Analysis of Algorithms";
        String cs3235 = "CS3235 Computer Security";
        String cs3223 = "CS3223 Database Systems Implementation";
        String cs4211 = "CS4211 Formal Methods for Software Engineering";
        String cs4221 = "CS4221 Database Applications Design and Tuning";
        String cs4224 = "CS4224 Distributed Databases";
        String cs4231 = "CS4231 Parallel &"
                + "Distributed Algorithms";

        String ee3104C = "EE3104C Introduction to RF "
                + "and Microwave Systems & Circuit";
        String ee3408C = "EE3408C Integrated Analog Design";
        String ee4104 = "EE4101 Microwave Circuits & Devices";
        String ee4112 = "EE4112 Radio Frequency Design and Systems";
        String ee4505 = "EE4505 Power Semiconductor Devices & ICs";
        String ee5518 = "EE5518 VLSI Digital Circuit Design";

        ModuleCategory cn1 = new ModuleCategory(cs2107);
        cn1.isBreadth = true;

        ModuleCategory cn2 = new ModuleCategory(cs3103);
        cn2.isBreadth = true;

        ModuleCategory cn3 = new ModuleCategory(ee3131C);
        cn3.isBreadth = true;

        ModuleCategory cn4 = new ModuleCategory(cs4222);
        cn4.isDepth = true;

        ModuleCategory cn5 = new ModuleCategory(cs4226);
        cn5.isDepth = true;

        ModuleCategory cn6 = new ModuleCategory(ee4210);
        cn6.isDepth = true;

        ModuleCategory cn7 = new ModuleCategory(cs5223);
        cn7.isDepth = true;

        ModuleCategory cn8 = new ModuleCategory(cs5321);
        cn8.isDepth = true;

        ModuleCategory cn9 = new ModuleCategory(eE5135);
        cn9.isDepth = true;

        commsAndNetworkingBD.add(cn1);
        commsAndNetworkingBD.add(cn2);
        commsAndNetworkingBD.add(cn3);
        commsAndNetworkingBD.add(cn4);
        commsAndNetworkingBD.add(cn5);
        commsAndNetworkingBD.add(cn6);
        commsAndNetworkingBD.add(cn7);
        commsAndNetworkingBD.add(cn8);
        commsAndNetworkingBD.add(cn9);

        specMap.put(specList.get(commsAndNetworkingIndex - 1),
                commsAndNetworkingBD);

        ModuleCategory emb1 = new ModuleCategory(cg3207);
        emb1.isBreadth = true;

        ModuleCategory emb2 = new ModuleCategory(cs2107);
        emb2.isBreadth = true;

        ModuleCategory emb3 = new ModuleCategory(cs3211);
        emb3.isBreadth = true;

        ModuleCategory emb4 = new ModuleCategory(ee3731C);
        emb4.isBreadth = true;

        ModuleCategory emb5 = new ModuleCategory(cs4222);
        emb5.isDepth = true;

        ModuleCategory emb6 = new ModuleCategory(cs4223);
        emb6.isDepth = true;

        ModuleCategory emb7 = new ModuleCategory(ee4218);
        emb7.isDepth = true;

        ModuleCategory emb8 = new ModuleCategory(ee4415);
        emb8.isDepth = true;

        ModuleCategory emb9 = new ModuleCategory(cs5272);
        emb9.isDepth = true;

        ModuleCategory emb10 = new ModuleCategory(ee5903);
        emb10.isDepth = true;

        embComputingBD.add(emb1);
        embComputingBD.add(emb2);
        embComputingBD.add(emb3);
        embComputingBD.add(emb4);
        embComputingBD.add(emb5);
        embComputingBD.add(emb6);
        embComputingBD.add(emb7);
        embComputingBD.add(emb8);
        embComputingBD.add(emb9);
        embComputingBD.add(emb10);

        specMap.put(specList.get(embeddedComputingIndex - 1), embComputingBD);

        ModuleCategory is1 = new ModuleCategory(cs3243);
        is1.isBreadth = true;

        ModuleCategory is2 = new ModuleCategory(cs3244);
        is2.isBreadth = true;

        ModuleCategory is3 = new ModuleCategory(ee3331C);
        is3.isBreadth = true;

        ModuleCategory is4 = new ModuleCategory(cs4244);
        is4.isDepth = true;

        ModuleCategory is5 = new ModuleCategory(cs4246);
        is5.isDepth = true;

        ModuleCategory is6 = new ModuleCategory(cs4248);
        is6.isDepth = true;

        ModuleCategory is7 = new ModuleCategory(ee4305);
        is7.isDepth = true;

        ModuleCategory is8 = new ModuleCategory(ee4308);
        is8.isDepth = true;

        ModuleCategory is9 = new ModuleCategory(cs5242);
        is9.isDepth = true;

        ModuleCategory is10 = new ModuleCategory(cs5339);
        is10.isDepth = true;

        ModuleCategory is11 = new ModuleCategory(ee5904);
        is11.isDepth = true;

        ModuleCategory is12 = new ModuleCategory(ee5907);
        is12.isDepth = true;

        intSystemsBD.add(is1);
        intSystemsBD.add(is2);
        intSystemsBD.add(is3);
        intSystemsBD.add(is4);
        intSystemsBD.add(is5);
        intSystemsBD.add(is6);
        intSystemsBD.add(is7);
        intSystemsBD.add(is8);
        intSystemsBD.add(is9);
        intSystemsBD.add(is10);
        intSystemsBD.add(is11);
        intSystemsBD.add(is12);

        specMap.put(specList.get(intelligentSystemsIndex - 1), intSystemsBD);

        ModuleCategory idm1 = new ModuleCategory(cs2108);
        idm1.isBreadth = true;

        ModuleCategory idm2 = new ModuleCategory(cs3240);
        idm2.isBreadth = true;

        ModuleCategory idm3 = new ModuleCategory(cs3241);
        idm3.isBreadth = true;

        ModuleCategory idm4 = new ModuleCategory(cs3242);
        idm4.isBreadth = true;

        ModuleCategory idm5 = new ModuleCategory(cs3247);
        idm5.isBreadth = true;

        ModuleCategory idm6 = new ModuleCategory(ee3731C);
        idm6.isBreadth = true;

        ModuleCategory idm7 = new ModuleCategory(cs4240);
        idm7.isDepth = true;

        ModuleCategory idm8 = new ModuleCategory(cs4243);
        idm8.isDepth = true;

        ModuleCategory idm9 = new ModuleCategory(cs4247);
        idm9.isDepth = true;

        ModuleCategory idm10 = new ModuleCategory(cs4249);
        idm10.isDepth = true;

        ModuleCategory idm11 = new ModuleCategory(cs4347);
        idm11.isDepth = true;

        ModuleCategory idm12 = new ModuleCategory(cs4351);
        idm12.isDepth = true;

        ModuleCategory idm13 = new ModuleCategory(ee4212);
        idm13.isDepth = true;

        ModuleCategory idm14 = new ModuleCategory(ee4604);
        idm14.isDepth = true;

        ModuleCategory idm15 = new ModuleCategory(ee4704);
        idm15.isDepth = true;

        intDigMediaBD.add(idm1);
        intDigMediaBD.add(idm2);
        intDigMediaBD.add(idm3);
        intDigMediaBD.add(idm4);
        intDigMediaBD.add(idm5);
        intDigMediaBD.add(idm6);
        intDigMediaBD.add(idm7);
        intDigMediaBD.add(idm8);
        intDigMediaBD.add(idm9);
        intDigMediaBD.add(idm10);
        intDigMediaBD.add(idm11);
        intDigMediaBD.add(idm12);
        intDigMediaBD.add(idm13);
        intDigMediaBD.add(idm14);
        intDigMediaBD.add(idm15);

        specMap.put(specList.get(interactiveDigitalMediaIndex - 1),
                intDigMediaBD);

        ModuleCategory ls1 = new ModuleCategory(cs2101);
        ls1.isBreadth = true;

        ModuleCategory ls2 = new ModuleCategory(cs3210);
        ls2.isBreadth = true;

        ModuleCategory ls3 = new ModuleCategory(cs3211);
        ls3.isBreadth = true;

        ModuleCategory ls4 = new ModuleCategory(cs3230);
        ls4.isBreadth = true;

        ModuleCategory ls5 = new ModuleCategory(cs3235);
        ls5.isBreadth = true;

        ModuleCategory ls6 = new ModuleCategory(cs3223);
        ls6.isDepth = true;

        ModuleCategory ls7 = new ModuleCategory(cs4211);
        ls7.isDepth = true;

        ModuleCategory ls8 = new ModuleCategory(cs4221);
        ls8.isDepth = true;

        ModuleCategory ls9 = new ModuleCategory(cs4223);
        ls9.isDepth = true;

        ModuleCategory ls10 = new ModuleCategory(cs4224);
        ls10.isDepth = true;

        ModuleCategory ls11 = new ModuleCategory(cs4231);
        ls11.isDepth = true;

        ModuleCategory ls12 = new ModuleCategory(ee4210);
        ls12.isDepth = true;

        ModuleCategory ls13 = new ModuleCategory(ee4218);
        ls13.isDepth = true;

        largeScaleComputingBD.add(ls1);
        largeScaleComputingBD.add(ls2);
        largeScaleComputingBD.add(ls3);
        largeScaleComputingBD.add(ls4);
        largeScaleComputingBD.add(ls5);
        largeScaleComputingBD.add(ls6);
        largeScaleComputingBD.add(ls7);
        largeScaleComputingBD.add(ls8);
        largeScaleComputingBD.add(ls9);
        largeScaleComputingBD.add(ls10);
        largeScaleComputingBD.add(ls11);
        largeScaleComputingBD.add(ls12);
        largeScaleComputingBD.add(ls13);

        specMap.put(specList.get(largeScaleComputingIndex - 1),
                largeScaleComputingBD);

        ModuleCategory s1 = new ModuleCategory(cg3207);
        s1.isBreadth = true;

        ModuleCategory s2 = new ModuleCategory(ee3104C);
        s2.isBreadth = true;

        ModuleCategory s3 = new ModuleCategory(ee3408C);
        s3.isBreadth = true;

        ModuleCategory s4 = new ModuleCategory(cs4223);
        s4.isDepth = true;

        ModuleCategory s5 = new ModuleCategory(ee4104);
        s5.isDepth = true;

        ModuleCategory s6 = new ModuleCategory(ee4112);
        s6.isDepth = true;

        ModuleCategory s7 = new ModuleCategory(ee4218);
        s7.isDepth = true;

        ModuleCategory s8 = new ModuleCategory(ee4415);
        s8.isDepth = true;

        ModuleCategory s9 = new ModuleCategory(ee4505);
        s9.isDepth = true;

        ModuleCategory s10 = new ModuleCategory(ee5518);
        s10.isDepth = true;

        sysDesignBD.add(s1);
        sysDesignBD.add(s2);
        sysDesignBD.add(s3);
        sysDesignBD.add(s4);
        sysDesignBD.add(s5);
        sysDesignBD.add(s6);
        sysDesignBD.add(s7);
        sysDesignBD.add(s8);
        sysDesignBD.add(s9);
        sysDesignBD.add(s10);

        specMap.put(specList.get(sysDesignIndex - 1), sysDesignBD);

    }
}
