 package rims.core;

import rims.command.*;
import rims.exception.*;
import rims.resource.*;

import java.util.*;
import java.io.*;
import java.text.*;

public class ResourceList {
    protected HashMap<String, ArrayList<Resource>> resources;

    public ResourceList(HashMap<String, ArrayList<Resource>> resources) {
        this.resources = resources;
    }

    public HashMap<String, ArrayList<Resource>> getResources() {
        return resources;
    }

    public int getAvailableQuantity(String resourceName) {
        ArrayList<Resource> thisResourceArray = resources.get(resourceName);
        int availableQty = 0;
        for (int i = 0; i < thisResourceArray.size(); i++) {
            if (!(thisResourceArray.get(i).isBooked())) {
                availableQty++;
            }
        }
        return availableQty;
    }

    public int getBookedQuantity(String resourceName) {
        ArrayList<Resource> thisResourceArray = resources.get(resourceName);
        int bookedQty = 0;
        for (int i = 0; i < thisResourceArray.size(); i++) {
            if (thisResourceArray.get(i).isBooked()) {
                bookedQty++;
            }
        }
        return bookedQty;
    }

    public int getBookedQuantityOfOrder(String resourceName, int loanId) {
        ArrayList<Resource> thisResourceArray = resources.get(resourceName);
        int bookedQty = 0;
        for (int i = 0; i < thisResourceArray.size(); i++) {
            Resource thisResource = thisResourceArray.get(i);
            if (thisResource.isBooked() && thisResource.getLoanId() == loanId) {
                bookedQty++;
            }
        }
        return bookedQty;
    }

    public int getTotalQuantity(String resourceName) {
        return resources.get(resourceName).size();
    }

    public Resource getAvailableResource(String resourceName) throws Exception {
        ArrayList<Resource> thisResourceArray = resources.get(resourceName);
        for (int i = 0; i < thisResourceArray.size(); i++) {
            Resource thisResource = thisResourceArray.get(i);
            if (!(thisResource.isBooked())) {
                return thisResource;
            }
        }
        throw new Exception("No available items!");
        // throw exception if nothing returned
        // replace with custom exception
    }

    public Resource getBookedResource(String resourceName, int loanId) throws Exception {
        ArrayList<Resource> thisResourceArray = resources.get(resourceName);
        for (int i = 0; i < thisResourceArray.size(); i++) {
            Resource thisResource = thisResourceArray.get(i);
            if (thisResource.isBooked() && thisResource.getLoanId() == loanId) {
                return thisResource;
            }
        }
        throw new Exception("No available items!");
        // replace with custom exception
    }



    public ArrayList<String> generateBookedList() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("CURRENTLY BOOKED");
        for (Map.Entry<String, ArrayList<Resource>> entry : resources.entrySet()) {
            ArrayList<Resource> thisResourceArray = entry.getValue();
            int qtyBooked = getBookedQuantity(entry.getKey());
            if (thisResourceArray.size() > 0 && qtyBooked > 0) {
                if (thisResourceArray.get(0).getType() == 'I') {
                    list.add(thisResourceArray.get(0).toString() + " (qty: " + qtyBooked + ")");
                }
                else if (thisResourceArray.get(0).getType() == 'R') {
                    list.add(thisResourceArray.get(0).toString());
                }
            }
        }
        return list;
    }

    public ArrayList<String> generateAvailableList() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("AVAILABLE FOR LOAN");
        for (Map.Entry<String, ArrayList<Resource>> entry : resources.entrySet()) {
            ArrayList<Resource> thisResourceArray = entry.getValue();
            int qtyAvailable = getAvailableQuantity(entry.getKey());
            if (thisResourceArray.size() > 0 && qtyAvailable > 0) {
                if (thisResourceArray.get(0).getType() == 'I') {
                    list.add(thisResourceArray.get(0).toString() + " (qty: " + qtyAvailable + ")");
                }
                else if (thisResourceArray.get(0).getType() == 'R') {
                    list.add(thisResourceArray.get(0).toString());
                }
            }
        }
        return list;
    }


    public ArrayList<String> generateAvailableListByDate(String day) throws ParseException {
        ArrayList<String> list = new ArrayList<String>();
        list.add("AVAILABLE FOR LOAN ON THIS DAY");
        for (Map.Entry<String, ArrayList<Resource>> entry : resources.entrySet()) {
            ArrayList<Resource> thisResourceArray = entry.getValue();
            int qtyAvailable = getAvailableQuantity(entry.getKey());
            for(int i = 0; i < thisResourceArray.size(); i++){
                Resource thisResource = thisResourceArray.get(i);
                if (!(thisResource.isBooked()) && ((thisResource.getDateBookedFrom().after(stringToDate(day))) && (thisResource.getDateBookedTill().before(stringToDate(day))))) {
                    if (thisResourceArray.get(0).getType() == 'I') {
                        list.add(thisResourceArray.get(0).toString() + " (qty: " + qtyAvailable + ")");
                    }
                    else if (thisResourceArray.get(0).getType() == 'R') {
                        list.add(thisResourceArray.get(0).toString());
                    }
                }
            }
        }
        return list;
    }

    public ArrayList<String> generateBookedListByDate(String day) throws ParseException {
        ArrayList<String> list = new ArrayList<String>();
        list.add("CURRENTLY BOOKED ON THIS DAY");
        for (Map.Entry<String, ArrayList<Resource>> entry : resources.entrySet()) {
            ArrayList<Resource> thisResourceArray = entry.getValue();
            int qtyBooked = getBookedQuantity(entry.getKey());
            for(int i = 0; i < thisResourceArray.size(); i++){
                Resource thisResource = thisResourceArray.get(i);
                if (thisResource.isBooked() && ((thisResource.getDateBookedFrom().before(stringToDate(day))) && (thisResource.getDateBookedTill().after(stringToDate(day))))) {
                    if (thisResourceArray.get(0).getType() == 'I') {
                        list.add(thisResourceArray.get(0).toString() + " (qty: " + qtyBooked + ")");
                    }
                    else if (thisResourceArray.get(0).getType() == 'R') {
                        list.add(thisResourceArray.get(0).toString());
                    }
                }
            }
        }
        return list;
    }

    public Date stringToDate(String stringDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date dateValue = formatter.parse(stringDate);
        return dateValue;
    }

}