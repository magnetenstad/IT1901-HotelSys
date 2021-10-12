package gr2116.core;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Hotel implements Iterable<HotelRoom> {
  private final Collection<HotelRoom> rooms = new ArrayList<>();

  public Hotel() {}

  public Hotel(Collection<HotelRoom> rooms) {
    rooms.forEach((room) -> this.rooms.add(room));
  }

  public void addRoom(HotelRoom room) {
    if (room == null) {
      throw new NullPointerException();
    }
    if (rooms.contains(room)) {
      System.out.println("[Warning]: Tried to add a room that was already added.");
    } else {
      rooms.add(room);
    }
  }

  public void removeRoom(HotelRoom room) {
    if (!rooms.contains(room)) {
      throw new IllegalArgumentException();
    }
    rooms.remove(room);
  }

  public Collection<HotelRoom> getRooms(Predicate<HotelRoom> predicate) {
    return rooms.stream().filter(predicate).collect(Collectors.toList());
  }

  @Override
  public Iterator<HotelRoom> iterator() {
    return rooms.iterator();
  }
}