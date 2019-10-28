package gazeeebo.commands.specialization;

import com.sun.javafx.application.ModuleAccess;
import gazeeebo.UI.Ui;
import gazeeebo.notes.Module;
import gazeeebo.storage.Storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class ListOfSpecializationAndModules {
    final static int COMMS_NETWORKING_INDEX = 1;
    final static int EMBEDDED_COMPUTING_INDEX = 2;
    final static int INTELLIGENT_SYSTEMS_INDEX = 3;
    final static int INTERACTIVE_DIGITAL_MEDIA_INDEX = 4;
    final static int LARGE_SCALE_COMPUTING_INDEX = 5;
    final static int SYS_DESIGN_INDEX = 6;

    public ListOfSpecializationAndModules(Ui ui, Storage storage, Map<String, ArrayList<ModuleCategory>> specMap) {
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

        String CS2107 = "CS2107 Introduction to Information System";
        String CS3103 = "CS3103 Computer Networks Practice";
        String EE3131C = "EE3131C Communication Systems";
        String CS4222 = "CS4222 Wireless Networking";
        String CS4226 = "CS4226 Internet Architecture";
        String EE4210 = "EE4210 Network Protocols and Applications";
        String CS5223 = "CS5223 Distributed Systems";
        String CS5321 = "CS5321 Network Security";
        String EE5135 = "EE5135 Digital Communications";

        String CG3207 = "CG3207 Computing Architecture";
        String CS3211 = "CS3211 Parallel and Concurrent Programming";
        String EE3731C = "EE3731C Signal Processing Methods";
        String CS4223 = "CS4223 Multi-core Architectures";
        String EE4218 = "EE4218 Embedded Hardware System Design";
        String EE4415 = "EE4218 Embedded Hardware System Design";
        String CS5272 = "CS5272 Embedded Software Design";
        String EE5903 = "EE5903 Real-time Systems";

        String CS3243 = "CS3243 Introduction to Artificial Intelligence";
        String CS3244 = "CS3244 Machine Learning";
        String EE3331C = "EE3331C Feedback Control Systems";
        String CS4244 = "CS4244 Knowledge-based Systems";
        String CS4246 = "CS4246 AI Planning and Decision Making";
        String CS4248 = "CS4248 Natural Language Processing";
        String EE4305 = "EE4305 Fuzzy/Neural Systems for Intelligent Robotics";
        String EE4308 = "EE4308 Autonomous Robot Systems";
        String CS5242 = "CS5242 Neural Networks & Deep Learning";
        String CS5339 = "CS5339 Theory and Algorithms for Machine Learning";
        String EE5904 = "EE5904 Neural Networks";
        String EE5907 = "EE5907 Pattern Recognition";

        String CS2108 = "CS2108 Introduction to Media Computing";
        String CS3240 = "CS3240 Interaction Design";
        String CS3241 = "CS3241 Computer Graphics";
        String CS3242 = "CS3242 3D Modeling and Animation";
        String CS3247 = "CS3247 Game Development";
        String CS4240 = "CS4240 Interaction Design for Virtual and Augmented Reality";
        String CS4243 = "CS4243 Computer Vision and Pattern Recognition";
        String CS4247 = "CS4247 Graphics Rendering Techniques";
        String CS4249 = "CS4249 Phenomena and Theories of Human-Computer Interaction";
        String CS4347 = "CS4347 Sound and Music Computing";
        String CS4351 = "CS4351 Real-Time Graphics";
        String EE4212 = "EE4212 Computer Vision";
        String EE4604 = "EE4604 Biological Perception in Digital Media";
        String EE4704 = "EE4704 Introduction to Computer Vision and Image Processing";

        String CS2101 = "CS2101 Database Systems";
        String CS3210 = "CS3210 Parallel Computing";
        String CS3230 = "CS3230 Design & Analysis of Algorithms";
        String CS3235 = "CS3235 Computer Security";
        String CS3223 = "CS3223 Database Systems Implementation";
        String CS4211 = "CS4211 Formal Methods for Software Engineering";
        String CS4221 = "CS4221 Database Applications Design and Tuning";
        String CS4224 = "CS4224 Distributed Databases";
        String CS4231 = "CS4231 Parallel & Distributed Algorithms";

        String EE3104C = "EE3104C Introduction to RF and Microwave Systems & Circuit";
        String EE3408C = "EE3408C Integrated Analog Design";
        String EE4104 = "EE4101 Microwave Circuits & Devices";
        String EE4112 = "EE4112 Radio Frequency Design and Systems";
        String EE4505 = "EE4505 Power Semiconductor Devices & ICs";
        String EE5518 = "EE5518 VLSI Digital Circuit Design";

        ModuleCategory cn1 = new ModuleCategory(CS2107);
        cn1.breadth = true;

        ModuleCategory cn2 = new ModuleCategory(CS3103);
        cn2.breadth = true;

        ModuleCategory cn3= new ModuleCategory(EE3131C);
        cn3.breadth = true;

        ModuleCategory cn4 = new ModuleCategory(CS4222);
        cn4.depth = true;

        ModuleCategory cn5 = new ModuleCategory(CS4226);
        cn5.depth = true;

        ModuleCategory cn6 = new ModuleCategory(EE4210);
        cn6.depth = true;

        ModuleCategory cn7 = new ModuleCategory(CS5223);
        cn7.depth = true;

        ModuleCategory cn8 = new ModuleCategory(CS5321);
        cn8.depth = true;

        ModuleCategory cn9 = new ModuleCategory(EE5135);
        cn9.depth = true;

        commsAndNetworkingBD.add(cn1);
        commsAndNetworkingBD.add(cn2);
        commsAndNetworkingBD.add(cn3);
        commsAndNetworkingBD.add(cn4);
        commsAndNetworkingBD.add(cn5);
        commsAndNetworkingBD.add(cn6);
        commsAndNetworkingBD.add(cn7);
        commsAndNetworkingBD.add(cn8);
        commsAndNetworkingBD.add(cn9);

        specMap.put(specList.get(COMMS_NETWORKING_INDEX-1), commsAndNetworkingBD);

        ModuleCategory emb1 = new ModuleCategory(CG3207);
        emb1.breadth = true;

        ModuleCategory emb2 = new ModuleCategory(CS2107);
        emb2.breadth = true;

        ModuleCategory emb3 = new ModuleCategory(CS3211);
        emb3.breadth = true;

        ModuleCategory emb4 = new ModuleCategory(EE3731C);
        emb4.breadth = true;

        ModuleCategory emb5 = new ModuleCategory(CS4222);
        emb5.depth = true;

        ModuleCategory emb6 = new ModuleCategory(CS4223);
        emb6.depth = true;

        ModuleCategory emb7 = new ModuleCategory(EE4218);
        emb7.depth = true;

        ModuleCategory emb8 = new ModuleCategory(EE4415);
        emb8.depth = true;

        ModuleCategory emb9 = new ModuleCategory(CS5272);
        emb9.depth = true;

        ModuleCategory emb10 = new ModuleCategory(EE5903);
        emb10.depth = true;

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

        specMap.put(specList.get(EMBEDDED_COMPUTING_INDEX-1), embComputingBD);

        ModuleCategory is1 = new ModuleCategory(CS3243);
        is1.breadth = true;

        ModuleCategory is2 = new ModuleCategory(CS3244);
        is2.breadth = true;

        ModuleCategory is3 = new ModuleCategory(EE3331C);
        is3.breadth = true;

        ModuleCategory is4 = new ModuleCategory(CS4244);
        is4.depth = true;

        ModuleCategory is5 = new ModuleCategory(CS4246);
        is5.depth = true;

        ModuleCategory is6 = new ModuleCategory(CS4248);
        is6.depth = true;

        ModuleCategory is7 = new ModuleCategory(EE4305);
        is7.depth = true;

        ModuleCategory is8 = new ModuleCategory(EE4308);
        is8.depth = true;

        ModuleCategory is9 = new ModuleCategory(CS5242);
        is9.depth = true;

        ModuleCategory is10 = new ModuleCategory(CS5339);
        is10.depth = true;

        ModuleCategory is11 = new ModuleCategory(EE5904);
        is11.depth = true;

        ModuleCategory is12 = new ModuleCategory(EE5907);
        is12.depth = true;

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

        specMap.put(specList.get(INTELLIGENT_SYSTEMS_INDEX-1), intSystemsBD);

        ModuleCategory idm1 = new ModuleCategory(CS2108);
        idm1.breadth = true;

        ModuleCategory idm2 = new ModuleCategory(CS3240);
        idm2.breadth = true;

        ModuleCategory idm3 = new ModuleCategory(CS3241);
        idm3.breadth = true;

        ModuleCategory idm4 = new ModuleCategory(CS3242);
        idm4.breadth = true;

        ModuleCategory idm5 = new ModuleCategory(CS3247);
        idm5.breadth = true;

        ModuleCategory idm6 = new ModuleCategory(EE3731C);
        idm6.breadth = true;

        ModuleCategory idm7 = new ModuleCategory(CS4240);
        idm7.depth = true;

        ModuleCategory idm8 = new ModuleCategory(CS4243);
        idm8.depth = true;

        ModuleCategory idm9 = new ModuleCategory(CS4247);
        idm9.depth = true;

        ModuleCategory idm10 = new ModuleCategory(CS4249);
        idm10.depth = true;

        ModuleCategory idm11 = new ModuleCategory(CS4347);
        idm11.depth = true;

        ModuleCategory idm12 = new ModuleCategory(CS4351);
        idm12.depth = true;

        ModuleCategory idm13 = new ModuleCategory(EE4212);
        idm13.depth = true;

        ModuleCategory idm14 = new ModuleCategory(EE4604);
        idm14.depth = true;

        ModuleCategory idm15 = new ModuleCategory(EE4704);
        idm15.depth = true;

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

        specMap.put(specList.get(INTERACTIVE_DIGITAL_MEDIA_INDEX-1), intDigMediaBD);

        ModuleCategory ls1 = new ModuleCategory(CS2101);
        ls1.breadth = true;

        ModuleCategory ls2 = new ModuleCategory(CS3210);
        ls2.breadth = true;

        ModuleCategory ls3 = new ModuleCategory(CS3211);
        ls3.breadth = true;

        ModuleCategory ls4 = new ModuleCategory(CS3230);
        ls4.breadth = true;

        ModuleCategory ls5 = new ModuleCategory(CS3235);
        ls5.breadth = true;

        ModuleCategory ls6 = new ModuleCategory(CS3223);
        ls6.depth = true;

        ModuleCategory ls7 = new ModuleCategory(CS4211);
        ls7.depth = true;

        ModuleCategory ls8 = new ModuleCategory(CS4221);
        ls8.depth = true;

        ModuleCategory ls9 = new ModuleCategory(CS4223);
        ls9.depth = true;

        ModuleCategory ls10 = new ModuleCategory(CS4224);
        ls10.depth = true;

        ModuleCategory ls11 = new ModuleCategory(CS4231);
        ls11.depth = true;

        ModuleCategory ls12 = new ModuleCategory(EE4210);
        ls12.depth = true;

        ModuleCategory ls13 = new ModuleCategory(EE4218);
        ls13.depth = true;

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

        specMap.put(specList.get(LARGE_SCALE_COMPUTING_INDEX-1), largeScaleComputingBD);

        ModuleCategory s1 = new ModuleCategory(CG3207);
        s1.breadth = true;

        ModuleCategory s2 = new ModuleCategory(EE3104C);
        s2.breadth = true;

        ModuleCategory s3 = new ModuleCategory(EE3408C);
        s3.breadth = true;

        ModuleCategory s4 = new ModuleCategory(CS4223);
        s4.depth = true;

        ModuleCategory s5 = new ModuleCategory(EE4104);
        s5.depth = true;

        ModuleCategory s6 = new ModuleCategory(EE4112);
        s6.depth = true;

        ModuleCategory s7 = new ModuleCategory(EE4218);
        s7.depth = true;

        ModuleCategory s8 = new ModuleCategory(EE4415);
        s8.depth = true;

        ModuleCategory s9 = new ModuleCategory(EE4505);
        s9.depth = true;

        ModuleCategory s10 = new ModuleCategory(EE5518);
        s10.depth = true;

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

        specMap.put(specList.get(SYS_DESIGN_INDEX-1), sysDesignBD);

    }
}
