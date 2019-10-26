package model;

import utils.DukeException;

import java.util.ArrayList;

//@@author chenyuheng
public class MemberManager {
    public static final String MESSAGE_DUPLICATED_MEMBER_NAME = "Duplicated member name.";
    ArrayList<Member> memberList;

    public MemberManager(ArrayList<Member> memberList) {
        if (memberList != null) {
            this.memberList = memberList;
        } else {
            this.memberList = new ArrayList<Member>();
        }
    }

    /**
     * Add a new member with a name.
     *
     * @param name The name of the new member, case sensitive.
     * @throws DukeException If duplicated member name is found.
     */
    public void addMember(String name) throws DukeException {
        name = name.trim();
        if (getMemberByName(name) == null) {
            memberList.add(new Member(name));
        } else {
            throw new DukeException(MESSAGE_DUPLICATED_MEMBER_NAME);
        }
    }

    /**
     * Get the Member object by its name.
     *
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
     *
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
     * Delete a member from the member list.
     *
     * @param toDelete The Member object to be deleted.
     */
    public void deleteMember(Member toDelete) {
        memberList.remove(toDelete);
    }

    /**
     * Delete an array of Member objects from the member list.
     *
     * @param toDelete The Member objects array to be deleted.
     */
    public void deleteMembers(Member[] toDelete) {
        for (int i = 0; i < toDelete.length; i++) {
            memberList.remove(toDelete[i]);
        }
    }

    /**
     * Add link(s) from member(s) to task(s). Duplicated link will be cancelled.
     *
     * @param members Array of Member objects to link.
     * @param toAdd   Array of Task objects to link.
     */
    public void addTask(Member[] members, Task[] toAdd) {
        for (int i = 0; i < members.length; i++) {
            for (int j = 0; j < toAdd.length; j++) {
                members[i].addTask(toAdd[j]);
            }
        }
    }

    /**
     * Delete link(s) from member(s) to task(s). Non-existing link won't be deleted.
     * This is the reverse method of <code>addTask(Member[] members, Task[] toAdd)</code> method.
     *
     * @param members  Array of Member objects to delete link.
     * @param toDelete Array of Task objects to delete link.
     */
    public void deleteTask(Member[] members, Task[] toDelete) {
        for (int i = 0; i < members.length; i++) {
            for (int j = 0; j < toDelete.length; j++) {
                members[i].deleteTask(toDelete[j]);
            }
        }
    }

    public ArrayList<Member> getMemberList() {
        return memberList;
    }
}
