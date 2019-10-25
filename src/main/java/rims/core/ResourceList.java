package rims.core;

import java.util.ArrayList;

import javax.naming.spi.ResolveResult;

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

    public int generateResourceId(){
        int resource_id=0;
        if(resources.size()>0){
            resource_id=( resources.get( resources.size()-1 ).getResourceId() ) + 1 ;
        }
        return resource_id;
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
                Reservation thisReservation = thisList.getReservationByIndex(j);
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
    /**
     * Find a resource using the resource id, then remove it from the resources array.
     * @exception invalidResourceId 
     * 
     * Then, it will use built-in remove method to take out this resource and update 
     * 
     */
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
}