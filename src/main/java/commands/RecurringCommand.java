package commands;

import Storage.Storage;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RecurringCommand {
    public String description;

    public RecurringCommand(String description) {
        this.description = description;
    }

    public void AddRecurring(ArrayList<Task> list, String list_description, Storage storage) throws ParseException, IOException {
        String[] splitstring;
        String Recurringtask;
        int date;
        if (list_description.contains("weekly")) {
            if (list_description.charAt(1) == 'E') {
                splitstring = list_description.split("\\(at:");
                char a = splitstring[1].charAt(0);
                char b = splitstring[1].charAt(1);
                String conc = "" + a + b; //get the date in string form
                date = Integer.parseInt(conc) + 7;
                Date getdate = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(splitstring[1].substring(3, 6));
                Calendar cal = Calendar.getInstance();
                cal.setTime(getdate);
                int month = cal.get(Calendar.MONTH) + 1;
                int year = Integer.parseInt(splitstring[1].substring(7,11));
                String[] timing = splitstring[1].split(" ");
                String hour_min_sec = timing[3].substring(0,17);
                String conc_time =  year + "-" + month + "-" + date + " " + hour_min_sec;
                String description = splitstring[0].substring(6, splitstring[0].length());

                Event new_weeklyEvent = new Event(description, (conc_time));
                list.add(new_weeklyEvent);
                System.out.println("");
                System.out.println("I've automatically added this weekly task again:");
                System.out.println(new_weeklyEvent.listFormat());
                System.out.println("Now you have " + list.size() + " tasks in the list.");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getClass().getName().equals("Tasks.Deadline")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.Event")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.FixedDuration")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.DoAfter")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.Timebound")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else {
                        sb.append(list.get(i).toString() + "\n");
                    }
                }
                storage.Storages(sb.toString());
            } else if (list_description.charAt(1) == 'D') {
                splitstring = list_description.split("by");
                char a = splitstring[1].charAt(0);
                char b = splitstring[1].charAt(1);
                String conc = "" + a + b; //get the date in string form
                date = Integer.parseInt(conc) + 7;
                Date getdate = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(splitstring[1].substring(3, 6));
                Calendar cal = Calendar.getInstance();
                cal.setTime(getdate);
                int month = cal.get(Calendar.MONTH) + 1;
                int year = Integer.parseInt(splitstring[1].substring(7,11));
                String[] timing = splitstring[1].split(" ");
                String hour_min_sec = timing[3].substring(0,17);
                String conc_time =  year + "-" + month + "-" + date + " " + hour_min_sec;
                String description = splitstring[0].substring(6, splitstring[0].length());

                Event new_weeklyEvent = new Event(description, (conc_time));
                list.add(new_weeklyEvent);
                System.out.println("");
                System.out.println("I've automatically added this weekly task again:");
                System.out.println(new_weeklyEvent.listFormat());
                System.out.println("Now you have " + list.size() + " tasks in the list.");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getClass().getName().equals("Tasks.Deadline")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.Event")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.FixedDuration")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.DoAfter")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.Timebound")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else {
                        sb.append(list.get(i).toString() + "\n");
                    }
                }
                storage.Storages(sb.toString());
            }
        } else if (list_description.contains("monthly")) {
            if (list_description.charAt(1) == 'E') {
                splitstring = list_description.split("\\(at:");
                char a = splitstring[1].charAt(0);
                char b = splitstring[1].charAt(1);
                String conc = "" + a + b; //get the date in string form
                date = Integer.parseInt(conc);
                Date getdate = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(splitstring[1].substring(3, 6));
                Calendar cal = Calendar.getInstance();
                cal.setTime(getdate);
                int month = cal.get(Calendar.MONTH) + 2;
                int year = Integer.parseInt(splitstring[1].substring(7,11));
                String[] timing = splitstring[1].split(" ");
                String hour_min_sec = timing[3].substring(0,17);
                String conc_time =  year + "-" + month + "-" + date + " " + hour_min_sec;
                String description = splitstring[0].substring(6, splitstring[0].length());

                Event new_weeklyEvent = new Event(description, (conc_time));
                list.add(new_weeklyEvent);
                System.out.println("");
                System.out.println("I've automatically added this monthly task again:");
                System.out.println(new_weeklyEvent.listFormat());
                System.out.println("Now you have " + list.size() + " tasks in the list.");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getClass().getName().equals("Tasks.Deadline")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.Event")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.FixedDuration")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.DoAfter")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.Timebound")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else {
                        sb.append(list.get(i).toString() + "\n");
                    }
                }
                storage.Storages(sb.toString());
            } else if (list_description.charAt(1) == 'D') {
                splitstring = list_description.split("by");
                char a = splitstring[1].charAt(0);
                char b = splitstring[1].charAt(1);
                String conc = "" + a + b; //get the date in string form
                date = Integer.parseInt(conc);
                Date getdate = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(splitstring[1].substring(3, 6));
                Calendar cal = Calendar.getInstance();
                cal.setTime(getdate);
                int month = cal.get(Calendar.MONTH) + 2;
                int year = Integer.parseInt(splitstring[1].substring(7,11));
                String[] timing = splitstring[1].split(" ");
                String hour_min_sec = timing[3].substring(0,17);
                String conc_time =  year + "-" + month + "-" + date + " " + hour_min_sec;
                String description = splitstring[0].substring(6, splitstring[0].length());

                Event new_weeklyEvent = new Event(description, (conc_time));
                list.add(new_weeklyEvent);
                System.out.println("");
                System.out.println("I've automatically added this weekly task again:");
                System.out.println(new_weeklyEvent.listFormat());
                System.out.println("Now you have " + list.size() + " tasks in the list.");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getClass().getName().equals("Tasks.Deadline")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.Event")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.FixedDuration")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.DoAfter")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.Timebound")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else {
                        sb.append(list.get(i).toString() + "\n");
                    }
                }
                storage.Storages(sb.toString());
            }
        } else if (list_description.contains("yearly")) {
            if (list_description.charAt(1) == 'E') {
                splitstring = list_description.split("\\(at:");
                char a = splitstring[1].charAt(0);
                char b = splitstring[1].charAt(1);
                String conc = "" + a + b; //get the date in string form
                date = Integer.parseInt(conc);
                Date getdate = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(splitstring[1].substring(3, 6));
                Calendar cal = Calendar.getInstance();
                cal.setTime(getdate);
                int month = cal.get(Calendar.MONTH) + 1;
                int year = Integer.parseInt(splitstring[1].substring(7,11)) + 1;
                String[] timing = splitstring[1].split(" ");
                String hour_min_sec = timing[3].substring(0,17);
                String conc_time =  year + "-" + month + "-" + date + " " + hour_min_sec;
                String description = splitstring[0].substring(6, splitstring[0].length());

                Event new_weeklyEvent = new Event(description, (conc_time));
                list.add(new_weeklyEvent);
                System.out.println("");
                System.out.println("I've automatically added this weekly task again:");
                System.out.println(new_weeklyEvent.listFormat());
                System.out.println("Now you have " + list.size() + " tasks in the list.");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getClass().getName().equals("Tasks.Deadline")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.Event")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.FixedDuration")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.DoAfter")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.Timebound")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else {
                        sb.append(list.get(i).toString() + "\n");
                    }
                }
                storage.Storages(sb.toString());
            } else if (list_description.charAt(1) == 'D') {
                splitstring = list_description.split("by");
                char a = splitstring[1].charAt(0);
                char b = splitstring[1].charAt(1);
                String conc = "" + a + b; //get the date in string form
                date = Integer.parseInt(conc);
                Date getdate = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(splitstring[1].substring(3, 6));
                Calendar cal = Calendar.getInstance();
                cal.setTime(getdate);
                int month = cal.get(Calendar.MONTH) + 1;
                int year = Integer.parseInt(splitstring[1].substring(7,11)) + 1;
                String[] timing = splitstring[1].split(" ");
                String hour_min_sec = timing[3].substring(0,17);
                String conc_time =  year + "-" + month + "-" + date + " " + hour_min_sec;
                String description = splitstring[0].substring(6, splitstring[0].length());
                Event new_weeklyEvent = new Event(description, (conc_time));
                list.add(new_weeklyEvent);
                System.out.println("");
                System.out.println("I've automatically added this weekly task again:");
                System.out.println(new_weeklyEvent.listFormat());
                System.out.println("Now you have " + list.size() + " tasks in the list.");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getClass().getName().equals("Tasks.Deadline")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.Event")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.FixedDuration")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.DoAfter")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.Timebound")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else {
                        sb.append(list.get(i).toString() + "\n");
                    }
                }
                storage.Storages(sb.toString());
            }
        } else if(list_description.contains("monthly")) {
            if (list_description.charAt(1) == 'E') {
                splitstring = list_description.split("at");
                String conc = "";
                char a = splitstring[1].charAt(9);
                char b = splitstring[1].charAt(10);
                conc = "" + a + b;
                date = Integer.parseInt(conc);
                String[] timing = splitstring[1].split(" SGT ");
                timing[1] = timing[1].substring(0, 4);
                int year = Integer.parseInt(timing[1]);
                Date getdate = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(timing[0].substring(5, 8));
                Calendar cal = Calendar.getInstance();
                cal.setTime(getdate);
                int month = cal.get(Calendar.MONTH) + 2;
                String hour_min_sec = timing[0].substring(timing[0].length() - 8, timing[0].length());
                String conc_time = year + "-" + month + "-" + date + " " + hour_min_sec;
                String description = splitstring[0].substring(6, splitstring[0].length() - 1);
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Event new_monthlyEvent = new Event(description,conc_time);
                list.add(new_monthlyEvent);
                System.out.println("");
                System.out.println("I've automatically added this monthly task again:");
                System.out.println(new_monthlyEvent.listFormat());
                System.out.println("Now you have " + list.size() + " tasks in the list.");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getClass().getName().equals("Tasks.Deadline")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.Event")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.FixedDuration")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.DoAfter")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.Timebound")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else {
                        sb.append(list.get(i).toString() + "\n");
                    }
                }
                storage.Storages(sb.toString());
            } else if (list_description.charAt(1) == 'D') {
                splitstring = list_description.split("by");
                String conc = "";
                char a = splitstring[1].charAt(9);
                char b = splitstring[1].charAt(10);
                conc = "" + a + b;
                date = Integer.parseInt(conc);
                String[] timing = splitstring[1].split(" SGT ");
                timing[1] = timing[1].substring(0, 4);
                int year = Integer.parseInt(timing[1]);
                Date getdate = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(timing[0].substring(5, 8));
                Calendar cal = Calendar.getInstance();
                cal.setTime(getdate);
                int month = cal.get(Calendar.MONTH) + 2;
                String hour_min_sec = timing[0].substring(timing[0].length() - 8, timing[0].length());
                String conc_time = year + "-" + month + "-" + date + " " + hour_min_sec;
                String description = splitstring[0].substring(6, splitstring[0].length() - 1);
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Deadline new_monthlyDeadline = new Deadline(description, fmt.parse(conc_time));
                list.add(new_monthlyDeadline);
                System.out.println("");
                System.out.println("I've automatically added this monthly task again:");
                System.out.println(new_monthlyDeadline.listFormat());
                System.out.println("Now you have " + list.size() + " tasks in the list.");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getClass().getName().equals("Tasks.Deadline")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.Event")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.FixedDuration")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.DoAfter")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.Timebound")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else {
                        sb.append(list.get(i).toString() + "\n");
                    }
                }
                storage.Storages(sb.toString());
            }
        }
    }
}
