package model;

import model.task.Task;
import utils.DukeException;

import java.util.ArrayList;

public class MemberManager {
    ArrayList<Member>  memberList;

    public MemberManager() {
        memberList = new ArrayList<>();
    }

    /**
     * Add a new member with a name.
     * @param name The name of the new member, case sensitive.
     * @throws DukeException If duplicated name is found.
     */
    public void addMember(String name) throws DukeException {
        if (getMemberByName(name) == null) {
            memberList.add(new Member(name));
        } else {
            throw new DukeException("duplicated name");
        }
    }

    /**
     * Get the Member object by its name.
     * @param name The name
     * @return Return the Member object if found, return null if not found.
     */
    public Member getMemberByName(String name) {
        for (int i = 0; i < memberList.size(); i++) {
            if (memberList.get(i).getName().equals(name)) {
                return memberList.get(i);
            }
        }
        return null;
    }

    /**
     * Get the Member object by its id.
     * @param id The id, or the index of the Member ArrayList, which is non-persistent.
     *           An id starts with 0.
     * @return Return the Member object if found, return null if index is wrong.
     */
    public Member getMemberById(int id) {
        if (id >= 0 && id < memberList.size()) {
            return memberList.get(id);
        }
        return null;
    }

    /**
     * Get an array of Member objects by their names.
     * @param names The array of names of these members.
     * @return An array of Member objects, the elements in the array could be null if not found.
     */
    public Member[] getMembersByNames(String[] names) {
        Member[] members = new Member[names.length];
        for (int i = 0; i < names.length; i++) {
            members[i] = getMemberByName(names[i]);
        }
        return members;
    }

    /**
     * Get an array of Member objects by their ids.
     * @param ids The array of ids, the ids are the indexes of the Member ArrayList,
     *            which are non-persistent.
     *            An id starts with 0.
     * @return An array of Member objects, the elements in the array could be null if the indexes are in wrong range.
     */
    public Member[] getMembersByIds(int[] ids) {
        Member[] members = new Member[ids.length];
        for (int i = 0; i < ids.length; i++) {
            members[i] = getMemberById(ids[i]);
        }
        return members;
    }

    /**
     * Remove a member from the member list.
     * @param toRemove The Member object to be removed.
     */
    public void removeMember(Member toRemove) {

        memberList.remove(toRemove);
    }

    /**
     * Remove an array of Member objects from the member list.
     * @param toRemove The Member objects array to be removed.
     */
    public void removeMembers(Member[] toRemove) {
        for (int i = 0; i < toRemove.length; i++) {
            memberList.remove(toRemove[i]);
        }
    }

    /**
     * Add link(s) from member(s) to task(s). Duplicated link will be cancelled.
     * @param members Array of Member objects to link.
     * @param toAdd Array of Task objects to link.
     */
    public void addTask(Member[] members, Task[] toAdd) {
        for (int i = 0; i < members.length; i++) {
            for (int j = 0; j < toAdd.length; j++) {
                members[i].addTask(toAdd[j]);
            }
        }
    }

    /**
     * Remove link(s) from member(s) to task(s). Non-existing link won't be removed.
     * This is the reverse method of <code>addTask(Member[] members, Task[] toAdd)</code> method.
     * @param members Array of Member objects to remove link.
     * @param toRemove Array of Task objects to remove link.
     */
    public void removeTask(Member[] members, Task[] toRemove) {
        for (int i = 0; i < members.length; i++) {
            for (int j = 0; j < toRemove.length; j++) {
                members[i].removeTask(toRemove[j]);
            }
        }
    }
}
