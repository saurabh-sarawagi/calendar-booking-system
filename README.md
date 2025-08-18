# Calendar Booking System
A calendar booking system that allows a Calendar Owner to set up their availability and enables Invitees to book appointments based on that availability through a set of APIs.

# Terminology
1. **Owner**: The individual who has an account with the calendar booking system and whose calendar is available for booking via APIs.

2. **Invitee**: A person who uses the API to book an appointment with the Calendar Owner.

3. **Appointment**: A booking made by an Invitee to create a meeting with the Calendar Owner.

# Features
1. **Owner Setup**
   * Owner can get enrolled in the system
   * Multiple Owners are supported
   * Owner can update their details like email/phone number etc.
   
2. **Calendar Setup by Calendar Owner**
   * Owner can define their availability by means of two functions : Weekly Availability Rules & Exception Rules
   * For a given owner, Weekly Availability Rules are generic and can be defined by owner on the basis of DayOfWeek i.e. MONDAY : 10 AM to 6 PM, THURSDAY : 12 PM to 4 PM, SUNDAY : NOT AVAILABLE
   * Exception Rules are specific to a particular date. For Eg. - August 27, 2025 : NOT AVAILABLE (HOLIDAY), September 1, 2025 : 10 AM to 1 PM (HALF DAY)
   * Exception Rules take precedence over weekly rules. For Eg. - September 1, 2025 being a Monday, if the owner has set the availability in Exception as 10 AM to 1 PM then it'll override the availability set by the Owner for Monday in Weekly Availability
   * Owner can set and update the Weekly Availability Rules
   * Owner can add/update/remove Exception rules

3. **Appointment Booking by Invitee**
   * Invitee can fetch available time slots for a given owner for a given date
   * Invitee can book an appointment (60 minutes in duration) based on owner's availability
   * Invitee can update/cancel an appointment
   * Owner's availability is defined based on their set rules (Weekly & Exception) and their existing appointments
   * Once a slot is booked by an Invitee, it becomes unavailable for others

4. **Appointment Review by Calendar Owner**
   * Owner can retrieve a list of all their upcoming appointments
   * Appointment details shown to owner would contain date & time of each appointment along with invitee details (For Eg. - Name, Email, Phone Number etc.)

# Assumptions
### Data & User Handling
* No Authentication/Authorization for Owner & Invitee
* Invitee is not registered as a user — only name, email, and mobile are stored directly
* Initially loaded data is assumed to be valid and clean
* All API calls are assumed to originate from the UI, which will send both updated and unchanged fields in the payload (wherever required)

### Time & Scheduling Rules
* No multiple time-zone handling — all times are in a single fixed zone
* No time gaps — availability is a continuous range (e.g., 10:00–16:00), not split intervals like 10:00–12:00 and 14:00–16:00
* Time format is 24-hour (HH:mm), not AM/PM
* No cross-day slots — start and end time must fall on the same day
* 24-hour full availability (00:00 to 00:00) is not supported — boundary is restricted to [00:00, 23:00]
* Only 60 minute time intervals are considered as valid slots that too in HH:00 format i.e. 10:00 - 11:00 is valid slot but 10:15 - 11.15 or 10:00 - 10:30 are not valid slots

### System Behavior
* Booked appointments remain valid even if the owner’s availability rules change later
* Owner doesn't have a facility to Accept/Reject an appointment request from the invitee
* No audit logging is maintained
* No explicit performance optimizations (e.g., caching) are implemented
* Persistence beyond runtime is not supported

# Tech Stack
* **Java 24**
* **Spring Boot 3.5.4**
* **Spring Web** – REST API
* **Spring Data JPA** – database layer
* **H2 (in-memory DB)** – default for development/testing
* **Lombok** – boilerplate code reduction
* **JUnit 5 + Mockito** – testing

# Setup & Run
### Prerequisites
* Java 24
* Maven 3.8+

### Clone & Build
* Clone the repo with following command -> git clone https://github.com/saurabh-sarawagi/calendar-booking-system.git
* Options on how to run the application after cloning
  * Use a bundled Maven with IDEs like Intellij and Run as a regular Spring Boot Application with main class [com.alltrickz.calibre.CalibreApplication]
  * Install Maven and run the below commands in the checked out repo directory to get the application up and running
    * mvn clean install
    * mvn spring-boot:run

# Important Links
* Application Base URL - http://localhost:8080
* H2 Databsae UI - http://localhost:8080/h2-console/login.jsp (Username : sa , Password is Blank)
* Swagger UI - http://localhost:8080/swagger-ui/index.html

# Available APIs
### Owner APIs
* **New Owner**  
    POST /api/owner/new
   * Request
   ```
   {
    "phoneNumber" : "9999999991",
    "fullName" : "Saurabh S",
    "email" : "saurabh@gmail.com"
  }
   ```
  * Response
  ```
  {
  "id" : 6,
  "fullName" : "Saurabh S",
  "email" : "saurabh@gmail.com",
  "phoneNumber" : "9999999991" 
  }
  ```
* **Update Owner Details**

  PUT /api/owner/update/{id}
  * Request
  ```
  {
  "phoneNumber" : "1234567890",
  "fullName" : "ssa ddd",
  "email" : "ssa.ddd@gmail.com" 
  }
  ```
  
  * Response
  ```
  {
  "id" : 5,
  "fullName" : "ssa ddd",
  "email" : "ssa.ddd@gmail.com",
  "phoneNumber" : "1234567890"
  }
  ```
  
* **Get All Owners**
    
    GET /api/owner/get
  * Response
  ```
  [{
    "id": 1,
    "fullName": "Saurabh Jindal",
    "email": "saurabhs@gmail.com",
    "phoneNumber": "9988772233"
  },
  {
    "id": 2,
    "fullName": "Arpit Sahoo",
    "email": "arpit@gmail.com",
    "phoneNumber": "2429809232"
  },
  {
    "id": 3,
    "fullName": "Ranbir Agarwal",
    "email": "ranbir@gmail.com",
    "phoneNumber": "4093494422"
  },
  {
    "id": 4,
    "fullName": "Suhail Khan",
    "email": "suhail@gmail.com",
    "phoneNumber": "9045904095"
  }]
  ```

* **Get Owner by Id**

  GET /api/owner/get/{id}
    * Response
  ```
  {
  "id": 4,
  "fullName": "Suhail Khan",
  "email": "suhail@gmail.com",
  "phoneNumber": "9045904095"
  }
  ```
  
### Availability Setup APIs
* **Set Weekly Availability for an Owner**  
  POST /api/availability/weekly/set/{ownerId}
    * Request
   ```
   [{
    "isAvailable": true,
    "dayOfWeek": "WEDNESDAY",
    "startTime": "10:00",
    "endTime": "18:00"
  },
  {
    "isAvailable": false,
    "dayOfWeek": "SATURDAY",
    "startTime": null,
    "endTime": null
  }]
   ```
    * Response
  ```
  [{
    "id": 14,
    "ownerId": 4,
    "dayOfWeek": "WEDNESDAY",
    "startTime": "10:00",
    "endTime": "18:00",
    "isAvailable": true
  },
  {
    "id": 15,
    "ownerId": 4,
    "dayOfWeek": "SATURDAY",
    "startTime": null,
    "endTime": null,
    "isAvailable": false
  }]
  ```

* **Get Weekly Availability for an owner**  
  GET /api/availability/weekly/{ownerId}
    * Response
  ```
  [{
    "id": 1,
    "ownerId": 2,
    "dayOfWeek": "MONDAY",
    "startTime": "10:00",
    "endTime": "18:00",
    "isAvailable": true
  },
  {
    "id": 2,
    "ownerId": 2,
    "dayOfWeek": "TUESDAY",
    "startTime": "10:00",
    "endTime": "18:00",
    "isAvailable": true
  },
  {
    "id": 3,
    "ownerId": 2,
    "dayOfWeek": "WEDNESDAY",
    "startTime": "10:00",
    "endTime": "18:00",
    "isAvailable": true
  },
  {
    "id": 4,
    "ownerId": 2,
    "dayOfWeek": "THURSDAY",
    "startTime": "12:00",
    "endTime": "18:00",
    "isAvailable": true
  },
  {
    "id": 5,
    "ownerId": 2,
    "dayOfWeek": "FRIDAY",
    "startTime": "01:00",
    "endTime": "11:00",
    "isAvailable": true
  },
  {
    "id": 6,
    "ownerId": 2,
    "dayOfWeek": "SATURDAY",
    "startTime": "10:00",
    "endTime": "18:00",
    "isAvailable": false
  },
  {
    "id": 7,
    "ownerId": 2,
    "dayOfWeek": "SUNDAY",
    "startTime": null,
    "endTime": null,
    "isAvailable": false
  }]
  ```

* **Add Availability Exceptions for an Owner**  
  POST /api/availability/exception/add/{ownerId}
    * Request
   ```
   [{
    "date": "2025-08-25",
    "isAvailable": false,
    "description": "VACATION",
    "startTime": null,
    "endTime": null
  },
  {
    "date": "2025-08-23",
    "isAvailable": true,
    "description": "HALF DAY",
    "startTime": "10:00",
    "endTime": "13:00"
  }]
   ```
    * Response
  ```
  [{
    "id": 4,
    "ownerId": 2,
    "date": "2025-08-25",
    "startTime": null,
    "endTime": null,
    "isAvailable": false,
    "description": "VACATION"
  },
  {
    "id": 9,
    "ownerId": 2,
    "date": "2025-08-23",
    "startTime": "10:00",
    "endTime": "13:00",
    "isAvailable": true,
    "description": "HALF DAY"
  }]
  ```

* **Get Availability Exceptions for an owner**  
  GET /api/availability/exception/{ownerId}
    * Response
  ```
  [{
    "id": 5,
    "ownerId": 3,
    "date": "2025-08-19",
    "startTime": "12:00",
    "endTime": "15:00",
    "isAvailable": false,
    "description": "VACATION"
  },
  {
    "id": 6,
    "ownerId": 3,
    "date": "2025-08-20",
    "startTime": "12:00",
    "endTime": "15:00",
    "isAvailable": false,
    "description": "VACATION"
  },
  {
    "id": 8,
    "ownerId": 3,
    "date": "2025-08-23",
    "startTime": "11:00",
    "endTime": "13:00",
    "isAvailable": true,
    "description": "AVAILABLE"
  }]
  ```

* **Delete Availability Exceptions by Id**  
  DELETE /api/availability/exception/delete/{id}

### Booking Appointment APIs
* **Get Available Timeslots for an owner on a Given Day**  
  GET /api/timeslots/get?ownerId=3&date=2025-08-18
    * Response
  ```
  [{
    "start": "14:00",
    "end": "15:00"
  },
  {
    "start": "15:00",
    "end": "16:00"
  },
  {
    "start": "16:00",
    "end": "17:00"
  },
  {
    "start": "17:00",
    "end": "18:00"
  }]
  ```

* **Book An Appointment for an owner with a provided date/timeslot**  
  POST /api/appointment/book
    * Request
   ```
   {
  "date": "2025-08-25",
  "inviteeEmail": "slsd@gmail.com",
  "inviteeName": "dsl ds",
  "startTime": "13:00",
  "endTime": "14:00",
  "ownerId": 3
  }
   ```
    * Response
  ```
  {
  "id": 8,
  "ownerId": 3,
  "inviteeName": "dsl ds",
  "inviteeEmail": "slsd@gmail.com",
  "date": "2025-08-25",
  "startTime": "13:00",
  "endTime": "14:00"
  }
  ```

* **Update An Appointment via AppointmentId**  
  PATCH /api/appointment/update/{appointmentId}
    * Request
   ```
   {
  "date": "2025-08-26",
  "inviteeEmail": "newuser@gmail.com",
  "inviteeName": "new user",
  "startTime": "12:00",
  "endTime": "13:00",
  "ownerId": 3
  }
   ```
    * Response
  ```
  {
  "id": 7,
  "ownerId": 2,
  "inviteeName": "new user",
  "inviteeEmail": "newuser@gmail.com",
  "date": "2025-08-26",
  "startTime": "12:00",
  "endTime": "13:00"
  }
  ```

* **Cancel an Appointment by AppointmentId**  
  DELETE /api/appointment/cancel/{id}

### Appointment Review APIs
* **Get Upcoming Appointments for an Owner**  
  GET /api/appointment/upcoming/{ownerId}?size=5
    * Response
  ```
  [{
    "id": 4,
    "ownerId": 2,
    "inviteeName": "Astha Garg",
    "inviteeEmail": "astha.garg@yahoo.com",
    "date": "2025-08-18",
    "startTime": "17:00",
    "endTime": "18:00"
  },
  {
    "id": 5,
    "ownerId": 2,
    "inviteeName": "Ankita Kapoor",
    "inviteeEmail": "ankita.kapoor@aol.com",
    "date": "2025-08-19",
    "startTime": "12:00",
    "endTime": "13:00"
  },
  {
    "id": 6,
    "ownerId": 2,
    "inviteeName": "Jitu Kumar",
    "inviteeEmail": "jitu.k@rediffmail.com",
    "date": "2025-08-19",
    "startTime": "13:00",
    "endTime": "14:00"
  }]
  ```
