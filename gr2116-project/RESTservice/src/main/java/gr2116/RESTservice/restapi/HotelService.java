package gr2116.RESTservice.restapi;


import gr2116.core.Hotel;
import gr2116.core.Person;
import gr2116.core.HotelRoom;
import gr2116.persistence.HotelPersistence;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The top-level rest service for Hotel.
 */
@Path(HotelService.HOTEL_MODEL_SERVICE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class HotelService {
  public static final String HOTEL_MODEL_SERVICE_PATH = "hotel";
  private static final Logger LOG = LoggerFactory.getLogger(HotelService.class);

  @Context
  private Hotel hotel;

  @Context
  private HotelPersistence hotelPersistence;

  /**
   * The root resource, i.e. /hotel
   *
   * @return the Hotel
   */
  @GET
  public Hotel getHotel() {
    LOG.debug("Hotel: " + hotel);
    return hotel;
  }
  
  @Path("/person/{username}")
  public PersonResource getPersonResource(@PathParam("username") String username) {
    Collection<Person> matches = hotel.getPersons(p -> p.getUsername() == username);
    if (matches.size() != 1) {
      throw new IllegalStateException("Multiple or 0 matches for username" + username);
    }
    // TODO: Add Person object to person resource here
    Person person = matches.iterator().next();
    PersonResource personResource = new PersonResource(username, person, hotel);
    return personResource;
  }

  @Path("/rooms/{roomNumber}")
  public RoomResource getRoomResource(@PathParam("roomNumber") String roomNumber) {
    HotelRoom room;
    //LOG.debug("Sub-resource room for " + username);
    Collection<HotelRoom> matches = hotel.getRooms(p -> p.getNumber() == Integer.parseInt(roomNumber));
    
    if (matches.size() > 1) {
      throw new IllegalStateException("Multiple matches for room number" + roomNumber);
    } else if (matches.size() == 0) {
      room = null;
    } else {
      room = matches.iterator().next();
    }

    System.out.println("bruuuuh");
    RoomResource roomResource = new RoomResource(room, hotel);
    roomResource.setHotelPersistence(hotelPersistence);
    return roomResource;
  }
}
