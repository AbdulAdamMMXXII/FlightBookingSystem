# Flight Booking System (Java)

## Overview
This project is a Java-based Flight Booking System developed during my first year of undergraduate Computer Science studies as part of the *Object-Oriented Programming in Java* module (November 2024 – January 2025). The system was developed collaboratively in a team of two and demonstrates core object-oriented design principles, data persistence, and robust exception handling.

The application allows users to manage flights, customers, and bookings via a command-line interface, with optional lightweight GUI components for demonstration.

---

## Key Features
- Add, view, and remove flights and customers
- Create and cancel flight bookings
- Prevent duplicate bookings for the same customer and flight
- Persistent storage using text files
- Command-based interaction model
- Validation and domain-specific exception handling

---

## System Architecture
The system follows a modular architecture with clear separation of concerns:

- **Model Layer**:  
  `Flight`, `Customer`, `Booking`  
- **Command Layer**:  
  Implements the Command Pattern for CLI operations (e.g. AddCustomer, AddFlight, BookFlight)
- **Data Management Layer**:  
  DataManager classes responsible for loading and storing system state
- **Main System**:  
  `FlightBookingSystem` coordinates entities and operations

---

## Technologies Used
- **Language**: Java  
- **Paradigms**: Object-Oriented Programming (encapsulation, abstraction, interfaces)  
- **Collections**: `List`, `Map`, `TreeMap`  
- **Date/Time**: `java.time.LocalDate`  
- **Persistence**: File I/O (`Scanner`, `PrintWriter`)  
- **Testing**: JUnit (unit testing for model behaviour)  
- **UI**: Command-Line Interface (CLI), basic Swing components  

---

## Design Patterns & Concepts
- Command Pattern for extensible command handling
- Separation of concerns between model, commands, and persistence
- Defensive programming and validation
- Checked exceptions for business-rule enforcement
- Immutable data exposure where appropriate (defensive copying)

---

## My Contributions
- Designed and implemented core domain models and booking logic
- Developed data persistence layer (Customer, Flight, Booking data managers)
- Implemented booking validation and exception handling
- Integrated command parsing and system initialisation
- Wrote and executed unit tests to verify domain behaviour

---

## Learning Outcomes
This project strengthened my understanding of:
- Object-oriented design and system decomposition
- Data modelling and invariants
- File-based persistence strategies
- Exception handling and defensive programming
- Collaborative software development

---

## How to Run
1. Compile the project using a Java-compatible IDE or command line
2. Ensure the `resources/data` directory is present
3. Run the main application class
4. Interact with the system via the command-line interface

---

## Academic Context
This project was formally assessed as part of an undergraduate module and reflects foundational software engineering skills relevant to advanced study in computer science, particularly in systems design and programming methodology.

---

## License
This project is intended for academic and educational purposes.
