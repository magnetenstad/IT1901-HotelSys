package gr2116.persistence;

import gr2116.core.Amenity;
import gr2116.core.Hotel;
import gr2116.core.HotelRoomType;
import gr2116.core.HotelRoom;
import gr2116.core.Person;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


public class HotelPersistenceTest {
  private HotelPersistence hotelPersistence;

  @BeforeEach
  public void setup() {
    hotelPersistence = new HotelPersistence();
  }

  @Test
  public void testSerializingDeserializing() {
    // set up data
    Person rick = new Person("Richard");
    Person kyle = new Person("Kyllard");
    Person tom = new Person("Tom");
    rick.setEmail("richard@people.com");
    kyle.setEmail("kyle@people.com");
    tom.setEmail("tom@richpeople.org");

    rick.addBalance(1000);
    kyle.addBalance(144);
    tom.addBalance(1000000000);

    HotelRoom room1 = new HotelRoom(HotelRoomType.Single, 101);
    HotelRoom room2 = new HotelRoom(HotelRoomType.Single, 102);
    HotelRoom room3 = new HotelRoom(HotelRoomType.Quad, 714);

    room1.addAmenity(Amenity.Bathtub);
    room1.addAmenity(Amenity.Television);
    room2.addAmenity(Amenity.Fridge);
    room2.addAmenity(Amenity.Internet);
    room2.addAmenity(Amenity.Shower);
    room3.addAmenity(Amenity.KitchenFacilities);
    room3.addAmenity(Amenity.Television);
    room3.addAmenity(Amenity.WashingMachine);
    room3.addAmenity(Amenity.Shower);

    rick.makeReservation(room1, LocalDate.of(2121, 6, 4), LocalDate.of(2121, 6, 7));
    kyle.makeReservation(room1, LocalDate.of(2121, 6, 11), LocalDate.of(2121, 6, 13));
    rick.makeReservation(room2, LocalDate.of(2121, 7, 13), LocalDate.of(2121, 7, 22));
    tom.makeReservation(room3, LocalDate.of(2121, 10, 12), LocalDate.of(2121, 10, 13));
    tom.makeReservation(room3, LocalDate.of(2121, 11, 12), LocalDate.of(2121, 11, 13));
    tom.makeReservation(room3, LocalDate.of(2122, 1, 12), LocalDate.of(2122, 1, 13));
    tom.makeReservation(room3, LocalDate.of(2122, 2, 12), LocalDate.of(2122, 2, 13));

    Collection<Person> persons = new ArrayList<Person>();
    persons.add(rick);
    persons.add(kyle);
    persons.add(tom);

    Collection<HotelRoom> rooms = new ArrayList<HotelRoom>();
    rooms.add(room1);
    rooms.add(room2);
    rooms.add(room3);

    Hotel hotel = new Hotel(rooms, persons);

    try {
      hotelPersistence.saveHotel(hotel, "test");
      Hotel hotel2 = hotelPersistence.loadHotel("test");
      assertEquals(hotel.getRooms().size(), hotel2.getRooms().size());
      assertEquals(hotel.getPersons().size(), hotel2.getPersons().size());

      assertTrue(hotel2.getRooms().contains(room1));
      assertTrue(hotel2.getRooms().contains(room2));
      assertTrue(hotel2.getRooms().contains(room3));

      assertTrue(hotel2.getPersons().contains(rick));
      assertTrue(hotel2.getPersons().contains(kyle));
      assertTrue(hotel2.getPersons().contains(tom));

      assertTrue(hotel2.getRooms().equals(hotel.getRooms()));
      assertTrue(hotel2.getPersons().equals(hotel.getPersons()));

    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testPrefixIsNull() {
    Hotel hotel = new Hotel();
    assertThrows(NullPointerException.class, () -> hotelPersistence.saveHotel(hotel, null));
    assertThrows(NullPointerException.class, () -> hotelPersistence.loadHotel(null));
    assertThrows(NullPointerException.class, () -> hotelPersistence.saveHotel(null, "test"));
  }

  @AfterAll
  public static void cleanUp() {
    File testFile = new File(HotelPersistence.DATA_FOLDER, "testHotel.json");
    testFile.delete();
  }
}