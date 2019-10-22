package rims.core;

import java.util.ArrayList;

import rims.resource.Reservation;
import rims.resource.ReservationList;
import rims.resource.Resource;

public class ResourceList {
    protected ArrayList<Resource> resources;
    private Ui ui;

    public ResourceList(ArrayList<Resource> resources, Ui ui) {
        this.resources = resources;
        this.ui = ui;
    }

    // Create
    public void addResource(Resource resource) {
        resources.add(resource);
    }

    // Read
    public ArrayList<Resource> getResourceList() {
        return resources;
    }
    
    /**
     * 
     * @return
     */
    public ArrayList<String> getResourceArrayWithReservations() {
        ArrayList<String> list = new ArrayList<String>();
        return list;
    }
    
    public int size() {
        return resources.size();
    }

    /**
     * This method returns all reservations under a specified user id 
     * (Some work needs to be done to tidy up temp variables and constructors)
     * @param user_id
     * @return List of reservation under the given user id (as reservationList)
     */
    public ReservationList getReservationsByUserId (int user_id ){
        ReservationList list = new ReservationList();
        for(int i=0; i < resources.size(); i++ ){
            Resource thisResource = resources.get(i);
            ReservationList thisList = thisResource.getReservations();
            for(int j=0; j < thisList.size(); j ++){
                Reservation thisReservation = new Reservation();
                thisReservation = thisList.getReservationByIndex(j);

                if (thisReservation.getUid()==user_id){
                    list.addNewReservation(thisReservation);
                }
            }
        }
        return list;
    }

    public Resource getResourceByIndex(int id) {
        Resource resource = null;
        for (int i = 0; i < resources.size(); i++) {
            if (resources.get(i).getResourceId() == id) {
                resource = resources.get(i);
                break;
            }
        }
        return resource;
    }

    public Resource getResourceByName(String resourceName) {
        Resource resource = null;
        for (int i = 0; i < resources.size(); i++) {
            if (resources.get(i).getResourceName().equals(resourceName)) {
                resource = resources.get(i);
                break;
            }
        }
        return resource;
    }

    /**
     * This method return a resource that matches the given resource Id
     */
    public Resource getResourceByResourceId(int resource_id) {
        Resource resource = new Resource(){};

        for (int i = 0; i < resources.size(); i++) {
            if (resources.get(i).getResourceId() == resource_id) {
                resource = resources.get(i);
                break;
            }
        }
        return resource;
    }

    // update
    // delete
    public void deleteResourceByResourceId(int resource_id) {
        int index_to_remove = -1;

        for (int i = 0; i < resources.size(); i++) {
            if (resources.get(i).getResourceId() == resource_id) {
                index_to_remove = i;
                break;
            }
        }

        if (index_to_remove != -1) {
            ui.formattedPrint("The following resource and its reservations are removed:\n" + "\t"+ resources.get(index_to_remove).toString());
            resources.remove(index_to_remove);
        }
    }

    /**
     * Delete a single reservation, a single reservation can only be uniquely identified through a pair of resource id and reservation id
     * First query for the resource, then remove the reservation via id
     * @param resource_id
     * @param reservation_id
     */
    public void deleteSingleReservation(int resource_id, int reservation_id){
        Resource thisResource = getResourceByResourceId(resource_id);
        thisResource.removeReservationByReservationId(reservation_id);
    }


    // protected HashMap<String, ArrayList<Resource>> resources;

    // public ResourceList(HashMap<String, ArrayList<Resource>> resources) {
    // this.resources = resources;
    // }

    // public HashMap<String, ArrayList<Resource>> getResources() {
    // return resources;
    // }

    // public int getTotalQuantity(String resourceName) {
    // return resources.get(resourceName).size();
    // }

    // public int getAvailableQuantity(String resourceName) {
    // ArrayList<Resource> thisResourceArray = resources.get(resourceName);
    // int availableQty = 0;
    // for (int i = 0; i < thisResourceArray.size(); i++) {
    // if (!(thisResourceArray.get(i).isBookedNow())) {
    // availableQty++;
    // }
    // }
    // return availableQty;
    // }

    // public int getBookedQuantity(String resourceName) {
    // ArrayList<Resource> thisResourceArray = resources.get(resourceName);
    // int bookedQty = 0;
    // for (int i = 0; i < thisResourceArray.size(); i++) {
    // if (thisResourceArray.get(i).isBookedNow()) {
    // bookedQty++;
    // }
    // }
    // return bookedQty;
    // }

    // public int getBookedQuantityOfOrder(String resourceName, int loanId) {
    // ArrayList<Resource> thisResourceArray = resources.get(resourceName);
    // int bookedQty = 0;
    // for (int i = 0; i < thisResourceArray.size(); i++) {
    // Resource thisResource = thisResourceArray.get(i);
    // if (thisResource.isBookedNow() && thisResource.getLoanId() == loanId) {
    // bookedQty++;
    // }
    // }
    // return bookedQty;
    // }

    // public Resource getAvailableResource(String resourceName) throws Exception {
    // ArrayList<Resource> thisResourceArray = resources.get(resourceName);
    // for (int i = 0; i < thisResourceArray.size(); i++) {
    // Resource thisResource = thisResourceArray.get(i);
    // if (!(thisResource.isBookedNow())) {
    // return thisResource;
    // }
    // }
    // throw new Exception("No available items!");
    // // throw exception if nothing returned
    // // replace with custom exception
    // }

    // public Resource getBookedResource(String resourceName, int loanId) throws
    // Exception {
    // ArrayList<Resource> thisResourceArray = resources.get(resourceName);
    // for (int i = 0; i < thisResourceArray.size(); i++) {
    // Resource thisResource = thisResourceArray.get(i);
    // if (thisResource.isBookedNow() && thisResource.getLoanId() == loanId) {
    // return thisResource;
    // }
    // }
    // throw new Exception("No available items!");
    // // replace with custom exception
    // }

    // /**
    // * This overloaded method returns borrowed list by a given loadID
    // *
    // * @param loanId additional param to filter item list
    // *
    // * This method is used in reader.java to provide user a view of
    // * items they currently borrowed Later, this is can also be used
    // * to catch errors where users trying to return items when they
    // * have not borrowed anything.
    // */
    // public ArrayList<String> generateBookedList(int loanId) {
    // ArrayList<String> list = new ArrayList<String>();
    // String s = "CURRENTLY BOOK UNDER ID: " + Integer.toString(loanId);
    // list.add(s);

    // for (Map.Entry<String, ArrayList<Resource>> entry : resources.entrySet()) {
    // ArrayList<Resource> thisResourceArray = entry.getValue();
    // int qtyBooked = getBookedQuantity(entry.getKey());
    // if (thisResourceArray.size() > 0 && qtyBooked > 0) {
    // if (thisResourceArray.get(0).getType() == 'I' &&
    // thisResourceArray.get(0).getLoanId() == loanId) {
    // list.add(thisResourceArray.get(0).toString() + " (qty: " + qtyBooked + ")");
    // } else if (thisResourceArray.get(0).getType() == 'R' &&
    // thisResourceArray.get(0).getLoanId() == loanId
    // && thisResourceArray.get(0).getLoanId() == loanId) {
    // list.add(thisResourceArray.get(0).toString());
    // }
    // }
    // }
    // return list;
    // }

    // public ArrayList<String> generateBookedList() {
    // ArrayList<String> list = new ArrayList<String>();
    // list.add("CURRENTLY BOOKED");
    // for (Map.Entry<String, ArrayList<Resource>> entry : resources.entrySet()) {
    // ArrayList<Resource> thisResourceArray = entry.getValue();
    // int qtyBooked = getBookedQuantity(entry.getKey());
    // if (thisResourceArray.size() > 0 && qtyBooked > 0) {
    // if (thisResourceArray.get(0).getType() == 'I') {
    // list.add(thisResourceArray.get(0).toString() + " (qty: " + qtyBooked + ")" +
    // " (by user:"
    // + Integer.toString(thisResourceArray.get(0).getLoanId()) + ")");
    // } else if (thisResourceArray.get(0).getType() == 'R') {
    // list.add(thisResourceArray.get(0).toString() + " (by user:"
    // + Integer.toString(thisResourceArray.get(0).getLoanId()) + ")");
    // }
    // }
    // }
    // return list;
    // }

    // public ArrayList<String> generateFullList() {
    // ArrayList<String> list = new ArrayList<String>();
    // list.add("Full List of resources");
    // for (Map.Entry<String, ArrayList<Resource>> entry : resources.entrySet()) {
    // ArrayList<Resource> thisResourceArray = entry.getValue();
    // for (int i = 0; i < thisResourceArray.size(); i ++ ){
    // list.add("Name: " + thisResourceArray.get(i).toString() + " id: " +
    // thisResourceArray.get(i).getId() + " loan id: " +
    // thisResourceArray.get(i).getLoanId());
    // }
    // }
    // return list;
    // }

    // public ArrayList<String> generateAvailableList() {
    // ArrayList<String> list = new ArrayList<String>();
    // list.add("AVAILABLE FOR LOAN");
    // for (Map.Entry<String, ArrayList<Resource>> entry : resources.entrySet()) {
    // ArrayList<Resource> thisResourceArray = entry.getValue();
    // int qtyAvailable = getAvailableQuantity(entry.getKey());
    // if (thisResourceArray.size() > 0 && qtyAvailable > 0) {
    // if (thisResourceArray.get(0).getType() == 'I') {
    // list.add(thisResourceArray.get(0).toString() + " (qty: " + qtyAvailable +
    // ")");
    // } else if (thisResourceArray.get(0).getType() == 'R') {
    // list.add(thisResourceArray.get(0).toString());
    // }
    // }
    // }
    // return list;
    // }

    // /**
    // * Gets total quantity of resources in ResourceList regardless of status.
    // *
    // * @return total quantity of resources.
    // */
    // public int getAllResourcesQuantity() {
    // int qty = 0;
    // for (ArrayList<Resource> identicalResources : resources.values()) {
    // qty += identicalResources.size();
    // }
    // return qty;
    // }

    // /**
    // * Adds new resource to ResourceList. (OUTDATED + conceptually incorrect
    // because
    // * resource id is allocated outside of ResourceList)
    // *
    // * @param newResource new resource to add, can be item or room.
    // */
    // public void addResource(Resource newResource) {
    // String resourceName = newResource.getName();
    // if (resources.containsKey(resourceName)) {
    // resources.get(resourceName).add(newResource);
    // } else {
    // resources.put(resourceName, new ArrayList<Resource>());
    // resources.get(resourceName).add(newResource);
    // }
    // }

    // /**
    // * Adds a new resource to ResourceList given resource name and type.
    // *
    // * @param resourceName Name of resource to add.
    // * @param resourceType Type of resource: item or room.
    // * @throws RimException when type of resource is not item nor room
    // */
    // public void addResource(String resourceName, char resourceType) throws
    // RimException {
    // Resource newResource;
    // if (resourceType == 'I') {
    // newResource = new Item(resourceName, getAllResourcesQuantity());
    // } else if (resourceType == 'R') {
    // newResource = new Room(resourceName, getAllResourcesQuantity());
    // } else {
    // throw new RimException("Invalid type of resource!");
    // }

    // if (resources.containsKey(resourceName)) {
    // resources.get(resourceName).add(newResource);
    // } else {
    // resources.put(resourceName, new ArrayList<>());
    // resources.get(resourceName).add(newResource);
    // }
    // }

    // /**
    // * Deletes a resource from ResourceList given the resourceName.
    // *
    // * @param resourceName Name of resource to delete.
    // * @return the resource that was deleted.
    // * @throws RimException
    // */
    // public void deleteResource(String resourceName) throws Exception {

    // if (!resources.containsKey(resourceName)) {
    // throw new RimException("Resource not in list"); // resource stated not in
    // list
    // }

    // Resource deletedResource = getAvailableResource(resourceName);
    // resources.get(resourceName).remove(deletedResource);

    // // Remove empty ArrayList entry in inventory if that resource is depleted
    // if (resources.get(resourceName).isEmpty()) {
    // resources.remove(resourceName);
    // }
    // }

    // public ArrayList<String> generateAvailableListByDate(String stringDate)
    // throws ParseException {
    // ArrayList<String> list = new ArrayList<String>();
    // list.add("AVAILABLE FOR LOAN ON: " +
    // getDateToPrint(stringToDate(stringDate)));
    // for (Map.Entry<String, ArrayList<Resource>> entry : resources.entrySet()) {
    // ArrayList<Resource> thisResourceArray = entry.getValue();
    // int qtyAvailable = 0;
    // for (int i = 0; i < thisResourceArray.size(); i++) {
    // Resource thisResource = thisResourceArray.get(i);
    // if (!(thisResource.isBookedOn(stringToDate(stringDate)))) {
    // qtyAvailable += 1;
    // }
    // if (i == thisResourceArray.size() - 1 && qtyAvailable > 0) {
    // if (thisResourceArray.get(0).getType() == 'I') {
    // list.add(thisResourceArray.get(0).toString() + " (qty: " + qtyAvailable +
    // ")");
    // } else if (thisResourceArray.get(0).getType() == 'R') {
    // list.add(thisResourceArray.get(0).toString());
    // }
    // }
    // }
    // }
    // return list;
    // }

    // public ArrayList<String> generateBookedListByDate(String stringDate) throws
    // ParseException {
    // ArrayList<String> list = new ArrayList<String>();
    // list.add("CURRENTLY BOOKED ON: " + getDateToPrint(stringToDate(stringDate)));
    // for (Map.Entry<String, ArrayList<Resource>> entry : resources.entrySet()) {
    // ArrayList<Resource> thisResourceArray = entry.getValue();
    // int qtyBooked = 0;
    // for (int i = 0; i < thisResourceArray.size(); i++) {
    // Resource thisResource = thisResourceArray.get(i);
    // if (thisResource.isBookedOn(stringToDate(stringDate))) {
    // qtyBooked += 1;
    // }
    // if ((i == thisResourceArray.size() - 1) && qtyBooked > 0) {
    // if (thisResourceArray.get(0).getType() == 'I') {
    // list.add(thisResourceArray.get(0).toString() + " (qty: " + qtyBooked + ")");
    // } else if (thisResourceArray.get(0).getType() == 'R') {
    // list.add(thisResourceArray.get(0).toString());
    // }
    // }
    // }
    // }
    // return list;
    // }

    // public Date stringToDate(String stringDate) throws ParseException {
    // SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
    // Date dateValue = formatter.parse(stringDate);
    // return dateValue;
    // }

    // public String getDateToPrint(Date date) {
    // DateFormat dayFormat = new SimpleDateFormat("d");
    // int day = Integer.parseInt(dayFormat.format(date)) % 10;
    // String suffix = day == 1 ? "st" : (day == 2 ? "nd" : (day == 3 ? "rd" :
    // "th"));
    // String stringDate = (new SimpleDateFormat("EEEEE, ")).format(date) +
    // (dayFormat.format(date)) + suffix + " of "
    // + (new SimpleDateFormat("MMMMM yyyy, hh:mm aaa")).format(date);
    // return stringDate;
    // }

    // public ArrayList<String> generateListByItem(String itemName) throws
    // ParseException {
    // ArrayList<String> list = new ArrayList<String>();
    // for (Map.Entry<String, ArrayList<Resource>> entry : resources.entrySet()) {
    // ArrayList<Resource> thisResourceArray = entry.getValue();
    // int qtyAvailable = getAvailableQuantity(itemName);
    // if (thisResourceArray.size() > 0 &&
    // thisResourceArray.get(0).getName().equals(itemName)) {
    // if (qtyAvailable > 0) {
    // list.add(thisResourceArray.get(0).toString() + " (qty: " + qtyAvailable
    // + ") - available for booking");
    // }
    // for (int i = 0; i < thisResourceArray.size(); i++) {
    // Resource thisResource = thisResourceArray.get(i);
    // if (thisResource.isBookedNow()) {
    // list.add(thisResource.toString() + " - booked from "
    // + getDateToPrint(thisResource.getDateBookedFrom()) + " till "
    // + getDateToPrint(thisResource.getDateBookedTill()));
    // }

    // }
    // }
    // }
    // return list;
    // }

    // public ArrayList<String> generateListByRoom(String roomName) throws
    // ParseException {
    // ArrayList<String> list = new ArrayList<String>();
    // for (Map.Entry<String, ArrayList<Resource>> entry : resources.entrySet()) {
    // ArrayList<Resource> thisResourceArray = entry.getValue();
    // if (thisResourceArray.size() > 0 &&
    // thisResourceArray.get(0).getName().equals(roomName)) {
    // Resource thisResource = thisResourceArray.get(0);
    // if (thisResource.isBookedNow()) {
    // list.add(thisResourceArray.get(0).toString() + " - booked from "
    // + getDateToPrint(thisResource.getDateBookedFrom()) + " till "
    // + getDateToPrint(thisResource.getDateBookedTill()));
    // } else if (!(thisResource.isBookedNow())) {
    // list.add(thisResourceArray.get(0).toString() + " - is available");
    // }
    // }
    // }
    // return list;
    // }

    // public boolean isEmpty() {
    // return resources.isEmpty();
    // }

    // public int size() {
    // return resources.size();
    // }
}