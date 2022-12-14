package gr2116.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Person class. A person has a collection of listerners, a collection of
 * reservaitons, a name, an username and a balance.
 */
public class Person {
  /**
   * Collection of PersonListener's listening to this Person object.
   */
  private final Collection<PersonListener> listeners = new HashSet<>();
  /**
   * Collection of Reservation objects that this person has.
   */
  private final Collection<Reservation> reservations = new HashSet<>();
  /**
   * The Person objects name.
   */
  private String firstName;
  private String lastName;
  /**
   * The Person objects username.
   */
  private final String username;
  private String password;
  /**
   * The Person objects balance.
   */
  private double balance = 0;

  /**
   * Constructs a Person object with a given name.
   *
   * @param username - {@code String} that represents the persons username.
   *
   * @throws IllegalArgumentException if {@code name} is null.
   * @throws IllegalArgumentException if {@code name} is not valid.
   */
  public Person(final String username) {
    if (!isValidUsername(username)) {
      throw new IllegalArgumentException("The username is not valid");
    }
    this.username = username;
  }

  /**
   * Returns the full name.
   *
   * @return {@code name}
   */
  public final String getName() {
    return firstName + " " + lastName;
  }

  /**
   * Returns the first name.
   *
   * @return {@code firstName}
   */
  public final String getFirstName() {
    return firstName;
  }

  /**
   * Sets the first name.
   *
   * @param firstName the string to be set as the first name
   *
   * @throws IllegalArgumentException if the name is invalid
   */
  public void setFirstName(String firstName) {
    if (!isValidName(firstName)) {
      throw new IllegalArgumentException("The name is not valid");
    }
    this.firstName = firstName;
  }

  /**
   * Returns the last name.
   *
   * @return {@code lastName}
   */
  public final String getLastName() {
    return lastName;
  }

  /**
   * Sets the last name.
   *
   * @param lastName the string to be set as the last name
   *
   * @throws IllegalArgumentException if the name is invalid
   */
  public void setLastName(String lastName) {
    if (!isValidName(lastName)) {
      throw new IllegalArgumentException("The name is not valid");
    }
    this.lastName = lastName;
  }

  /**
   * Returns the username.
   *
   * @return {@code username} that is associated with this person.
   */
  public final String getUsername() {
    return username;
  }

  /**
   * Validation method that uses regex to check if the username provided is valid.
   *
   * @param username - the username that will be validated.
   *
   * @return {@code true} if the username is valid, {@code false} otherwise.
   */
  public static boolean isValidUsername(final String username) {
    if (username == null) {
      return false;
    }
    String regex = "^[a-zA-Z]+$";
    return username.matches(regex);
  }

  /**
   * Validation method that uses regex to check if the name provided is valid.
   *
   * @param name - the name that will be validated.
   *
   * @return {@code true} if the name is valid, {@code false} otherwise.
   */
  public static boolean isValidName(final String name) {
    return name.matches("([A-Za-z]+.* *)+");
  }

  /**
   * Checks if the given password is valid.
   * A valid password
   * - is not null
   * - has length >= 6
   * - returns a valid hash
   *
   * @param password the password to check
   * @return whether or not the password is valid
   */
  public static boolean isValidPassword(String password) {
    if (password == null) {
      return false;
    }
    if (password.length() < 6) {
      return false;
    }
    if (PasswordUtil.hashPassword(password) == null) {
      return false;
    }
    return true;
  }

  /**
   * Sets the persons password.
   *
   * @param password the password to set
   *
   * @throws IllegalArgumentException if the password is invalid - see isValidPassword method.
   */
  public void setPassword(String password) {
    if (!isValidPassword(password)) {
      throw new IllegalArgumentException("Invalid password!");
    }
    this.password = PasswordUtil.hashPassword(password);
  }

  /**
   * Sets the persons password hash directly.
   * Used when building persons from json.
   *
   * @param hashed the hashed password to be set
   *
   * @throws IllegalStateExceptino if the password has already been set
   */
  public void setHashedPassword(String hashed) {
    if (password != null) {
      throw new IllegalStateException("Cannot override a password with a hash.");
    }
    password = hashed;
  }

  /**
   * Returns the persons hashed password.
   * The password cannot be accessed in clear text.
   *
   * @return the hashed password
   */
  public String getHashedPassword() {
    return password;
  }

  /**
   * Returns the balance of this Person object.
   *
   * @return {@code balance} of this person.
   */
  public final double getBalance() {
    return balance;
  }

  /**
   * Adds the specified {@code balance} to this Peron objects balance field.
   *
   * @param balance - balance (the amount) to be added.
   *
   * @throws IllegalArgumentException if {@code balance} is below 0.
   */
  public final void addBalance(final double balance) {
    if (balance < 0) {
      throw new IllegalArgumentException("Balance must be positive.");
    }
    this.balance += balance;
    notifyListeners();
  }

  /**
   * Subtracts the specified {@code balance} from this Peron objects balance field
   * and notifies listeners.
   *
   * @param balance - balance (the amount) to be subtracted.
   *
   * @throws IllegalArgumentException if {@code balance} is below 0.
   */
  public final void subtractBalance(final double balance) {
    if (balance < 0) {
      throw new IllegalArgumentException("Balance must be positive.");
    }
    this.balance -= balance;
    notifyListeners();
  }

  /**
   * Adds the given reservation to the Person objects collection of reservations and 
   * notifies listeners.
   *
   * @param reservation - the {@code Reservation} to be added.
   *
   * @throws IllegalArgumentException if reservation is null.
   */
  public final void addReservation(final Reservation reservation) {
    if (reservation == null) {
      throw new IllegalArgumentException();
    }
    reservations.add(reservation);
    notifyListeners();
  }

  /**
   * Returns a Collection of the reservation IDs for this Person objects. E.g all
   * reservation IDs that belong to this Person
   *
   * @return {@code Collection<Long>} of reservation IDs.
   */
  public final Collection<String> getReservationIds() {
    List<String> ids = reservations.stream()
        .map((r) -> r.getId()).toList();
    return ids;
  }

  /**
   * Returns a boolean value depending on if the Person has made the given reservation.
   *
   * @param reservation - the {@code Reservation} to be checked.
   *
   * @return {@code true} if this Person has made the reservation, {@code false} otherwise.
   */
  public final boolean hasReservation(final Reservation reservation) {
    return reservations.contains(reservation);
  }

  /**
   * Returns a Collection of the reservations this Person has made.
   *
   * @return {@code Collection<Reservation>} of the reservations.
   */
  public final Collection<Reservation> getReservations() {
    return new HashSet<>(reservations);
  }

  /**
   * Adds a {@code PersonListener} to Person listeners collection.
   *
   * @param listener - the listener that needs to listen to Person.
   */
  public final void addListener(final PersonListener listener) {
    listeners.add(listener);
  }

  /**
   * Removes the listener stated from the Person objects listeners Collection.
   *
   * @param listener - the listener to be removed.
   */
  public final void removeListener(final PersonListener listener) {
    listeners.remove(listener);
  }

  /**
   * Notifies all listeners that is listening to this Person object.
   */
  public final void notifyListeners() {
    for (PersonListener listener : listeners) {
      listener.onPersonChanged(this);
    }
  }

  /**
   * Custom implementation of .equals method.
   *
   * @param o object to test against
   *
   * @return true if o and this are the same, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }
    Person p = (Person) o;
    return this.getName().equals(p.getName())
        && this.getUsername().equals(p.getUsername())
        && this.getReservations().equals(p.getReservations());
  }

  /**
   * Custom implementation of hashCode method. See
   * https://www.technofundo.com/tech/java/equalhash.html on why we did this.
   *
   * @return the hashCode for this Person
   */
  @Override
  public int hashCode() {
    int hash = 5;
    hash = hash * 17 + getName().hashCode();
    hash = hash * 31 + (getUsername() == null ? 0 : getUsername().hashCode());
    hash = hash * 5 + getReservations().hashCode();
    return hash;
  }
}
