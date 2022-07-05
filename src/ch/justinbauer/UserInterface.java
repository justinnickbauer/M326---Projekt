package ch.justinbauer;

import ch.justinbauer.exceptions.DateFormatException;
import ch.justinbauer.exceptions.ReservationException;

import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;
import java.util.Vector;

public class UserInterface {
    private static final boolean True = false;

    public void showInterface() {
        Date arrivalDate = null;
        String roomTypeString;
        String name;

        int nights = 0;
        int persons = 0;
        int price = 0;
        System.out.println("--------------------------------------");
        System.out.println("Hotel Reservation System");
        System.out.println("--------------------------------------");

        Vector<Room> rooms = new Vector<>(Arrays.asList(new Room[] {
                new SingleRoom("Single Room A", 10000, 1),
                new SingleRoom("Single Room B",  12000, 1),
                new DoubleRoom("Double Room A",  20000, 1),
                new FamilyRoom("Family Room A",  25000, 1)

        }));

        System.out.println("1. User: Reserve");
        System.out.println("2. User: Search");
        System.out.println("3. Admin: Add");
        System.out.println("4. Admin: Show");
        System.out.println("5. Admin: CheckIn");
        System.out.println("6. Admin: CheckOut");
        System.out.println("7. Cancel Room");
        System.out.println("8. Exit");
        System.out.println("-------------------------------------------------");

        System.out.print("Input Function.\n > ");


        try (Scanner scanner = new Scanner(System.in)) {
            while(true) {

                int select = Integer.parseInt(scanner.nextLine());
                if(select == 1){ //User Reservation
                    System.out.println("Welcome to User Reservation System");

                    System.out.print("Input Room Type (Single/Double/Family).\n > ");
                    roomTypeString = scanner.nextLine();

                    System.out.print("Input Checkin Date.(31.1.1995) \n > ");
                    arrivalDate = new Date(scanner.nextLine());

                    System.out.print("Input Nights.\n > ");
                    nights = Integer.parseInt(scanner.nextLine()) - 1;

                    System.out.print("Input Person Number.\n > ");
                    persons = Integer.parseInt(scanner.nextLine());

                    for (Room Room: rooms) {
                        if(roomTypeString.equals("Single") ) {
                            if(Room instanceof SingleRoom) {
                                if(Room.checkVacancy(arrivalDate, arrivalDate.getFollowingDate(nights))== true){
                                    showObjects(new Reservation(arrivalDate, nights, persons, Room));
                                    break;
                                }
                            }
                        }

                        if(roomTypeString.equals("Double") ) {
                            if(Room instanceof DoubleRoom) {
                                if(Room.checkVacancy(arrivalDate, arrivalDate.getFollowingDate(nights))== true){
                                    showObjects(new Reservation(arrivalDate, nights, persons, Room));
                                    break;
                                }
                            }
                        }

                        if(roomTypeString.equals("Family") ) {
                            if(Room instanceof FamilyRoom) {
                                if(Room.checkVacancy(arrivalDate, arrivalDate.getFollowingDate(nights))== true){
                                    showObjects(new Reservation(arrivalDate, nights, persons, Room));
                                    break;
                                }
                            }
                        }

                    }

                }

                else if(select== 2){//User Search System
                    System.out.println("Welcome to User Search System");

                    System.out.print("Input Room Type (Single/Double/Family).\n > ");
                    roomTypeString = scanner.nextLine();

                    System.out.print("Input Checkin Date.(31.1.1995) \n > ");
                    arrivalDate = new Date(scanner.nextLine());

                    System.out.print("Input Nights.\n > ");
                    nights = Integer.parseInt(scanner.nextLine()) - 1;

                    System.out.print("Input Person Number.\n > ");
                    persons = Integer.parseInt(scanner.nextLine()); //#3 fixed

                    for (Room Room: rooms) {
                        if(roomTypeString.equals("Single") ) {
                            if(Room instanceof SingleRoom) {
                                if(Room.checkVacancy(arrivalDate, arrivalDate.getFollowingDate(nights))== true){
                                    System.out.println("Empty!");
                                    break;
                                }
                            }
                        }

                        if(roomTypeString.equals("Double") ) {
                            if(Room instanceof DoubleRoom) {
                                if(Room.checkVacancy(arrivalDate, arrivalDate.getFollowingDate(nights))== true){
                                    System.out.println("Empty!");
                                    break;
                                }
                            }
                        }

                        if(roomTypeString.equals("Family") ) {
                            if(Room instanceof FamilyRoom) {
                                if(Room.checkVacancy(arrivalDate, arrivalDate.getFollowingDate(nights))== true){
                                    System.out.println("Empty!");
                                    break;
                                }
                            }
                        }

                    }

                }
                else if(select== 3){//Admin Add System
                    System.out.println("Welcome to Admin Add-Room System");

                    System.out.print("Input Room Name.\n > ");
                    name = scanner.nextLine();

                    System.out.print("Input Room Type (Single/Double/Family).\n > ");
                    roomTypeString = scanner.nextLine();

                    System.out.print("Input Base Price.\n > ");
                    price = Integer.parseInt(scanner.nextLine());

                    System.out.print("Input Bed Number.\n > ");
                    persons = Integer.parseInt(scanner.nextLine()) - 1;

                    if(roomTypeString.equals("Single") ) {
                        rooms.add(new SingleRoom(name, price, persons));
                        System.out.println("Added");
                    }

                    if(roomTypeString.equals("Double") ) {
                        rooms.add(new DoubleRoom(name, price, persons));
                        System.out.println("Added");
                    }

                    if(roomTypeString.equals("Family") ) {
                        rooms.add(new FamilyRoom(name, price, persons));
                        System.out.println("Added");
                    }


                }
                else if(select== 4){//Admin Show System
                    for (Room Room:rooms){
                        showObjects(Room);
                    }


                }

                else if(select== 5){//Admin CheckIn System
                    System.out.println("Input UUID >");
                    UUID code = UUID.fromString(scanner.nextLine());
                    for (Room Room:rooms){
                        if(Room.checkUUID(code))
                            showObjects(Room);
                    }


                }

                else if(select== 6){//Admin CheckIn System
                    System.out.println("Input Room Name >");
                    name=scanner.nextLine();
                    for (Room Room:rooms){
                        if(name.equals(Room.getName()))
                            Room.printReservationInformation();
                    }


                }

                else if(select== 7){//Admin CheckIn System
                    System.out.println("Input UUID >");
                    UUID code = UUID.fromString(scanner.nextLine());
                    for (Room Room:rooms){
                        if(Room.checkUUID(code))
                            Room.cancelReservation(Room.getReservation(code));;
                    }


                }

                else if(select == 8)
                    System.exit(0);

                System.out.println("-------------------------------------------------");

                System.out.println("input 1 to continue >");
                if(Integer.parseInt(scanner.nextLine())==1) {
                    System.out.println("1. User: Reserve");
                    System.out.println("2. User: Search");
                    System.out.println("3. Admin: Add");
                    System.out.println("4. Admin: Show");
                    System.out.println("5. Admin: CheckIn");
                    System.out.println("6. Admin: CheckOut");
                    System.out.println("7. Cancel Room");
                    System.out.println("8. Exit");
                    System.out.println("-------------------------------------------------");

                    System.out.print("Input Function.\n > ");
                }
                else
                    System.exit(1);

            }
        }

        catch (DateFormatException | ReservationException | NumberFormatException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

    }

    /**
     * Calls {@link Showable#show() show()} method on given {@link Showable} object.
     *
     * @param		showable	Any object implementing {@link Showable} interface.
     */
    public static void showObjects(Showable showable) {
        System.out.println("-------------------------------------------------");
        showable.show();
    }
}
