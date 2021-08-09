# TRAVEL APP

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
2. [Schema](#Schema)
3. [Walkthrough](#Walkthrough)

## Overview
### Description
Provides the user with travel information. 


### App Evaluation

- **Category:** Lifestyle / Travel

- **Mobile:** Android

- **Story:** Allows users to get dates and prices for flights.  Plan itinerary on Calendar.

- **Market:** This is for people who are planning for their trips. 

- **Habit:** This app is mostly seasonal for each individual, and more so for those who travel a lot. 

- **Scope:** We want to launch this for the leisure travel individuals first, we would like to create other features to allows users to plan a travel all within the same app. 
- **API:** This will utilize the Skyscanner API

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

- [x] Find flight information by typing in destination
- [x] Enter inbound information with dates
- [x] Planning Activities on Calendar


 
### Optional Nice-to-have Stories
- [ ] Details on the destination location
- [ ] Pictures, available hotel, to-do recommendations
- [ ] Utilize hotels API

### 2. Screen Archetypes

* Flight Search Screen
   * Find flight information by typing in destination and date
* Result Screen
   * View a list of flight result matching criteria
* Calendar Screen
    * User can select day on calendar for scheduling activities. 
* Daily Schedule Screen
    * View a checklist of all the tasks schedule on the particular day. 



### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Flight search 
* Flight result list - Recyclerview that display flights
* Calendar fragment - User can view calendar.

**Flow Navigation** (Screen to Screen)

* Add flight
   * Add selected flight from result to calendar
* Daily Schedule 
   * list activities on specific day. 

## Schema 
### Models
#### Post

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | user          | String   | unique id for the user|   
   | cities        | String   | name of the cities |
   | departDate    | Long     | date of depareture for flight  |
   | arriveDate    | long     | date of arrival for flight   |
   | toDo          | toDoItem | a toDoItem that contains the information of what happens on the day   |

### Networking
#### List of network requests by screen
   - Flight Search Screen
      - (Read/GET) Query all flights according to dates 
         ```java
AsyncHttpClient client = new DefaultAsyncHttpClient();
client.prepare("GET", "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsedates/v1.0/US/USD/en-US/SFO-sky/LAX-sky/2019-09-01?inboundpartialdate=2019-12-01")
	.setHeader("x-rapidapi-key", "7b4dfa76b8mshbfa8c1a8e2cadf6p143008jsnd94aafc4c7a7")
	.setHeader("x-rapidapi-host", "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com")
	.execute()
	.toCompletableFuture()
	.thenAccept(System.out::println)
	.join();

client.close();
         java
      - (Create/POST) Create a new like on a post
      - (Delete) Delete existing like
      - (Create/POST) Create a new comment on a post
      - (Delete) Delete existing comment
   - Get List of places
   AsyncHttpClient client = new DefaultAsyncHttpClient();
client.prepare("GET", "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/autosuggest/v1.0/UK/GBP/en-GB/?query=Stockholm")
	.setHeader("x-rapidapi-key", "7b4dfa76b8mshbfa8c1a8e2cadf6p143008jsnd94aafc4c7a7")
	.setHeader("x-rapidapi-host", "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com")
	.execute()
	.toCompletableFuture()
	.thenAccept(System.out::println)
	.join();

client.close();


##### Skyscanner API
- Base URL - https://rapidapi.com/skyscanner/api

   HTTP Verb | Endpoint | Description
   ----------|----------|------------
    `GET`    | /cities | gets all cities
    `GET`    | /cities/byId/:id | gets specific city by :id
    `GET`    | /continents | gets all continents
    `GET`    | /continents/byId/:id | gets specific continent by :id
    `GET`    | /regions | gets all regions
    `GET`    | /regions/byId/:id | gets specific region by :id
    
    
## Walkthrough 
### Overview: switch between Flight and Schedule fragments with buttom navigation view.
<img src='https://github.com/Freebee2day/TravelApp/blob/main/TravelApp_Fragments.gif' title='Fragments Walkthrough' width='' alt='Fragments Walkthrough' />
    
### Flight Searching Fragment: 
Search for flight with Skyscanner Flight Search API.
Enter departing & arriving cities, select date on calendar with datepicker.
<img src='https://github.com/Freebee2day/TravelApp/blob/main/TravelApp_Flight_Search.gif' title='Search Flight Walkthrough' width='' alt='Search Flight Walkthrough' />	
	
RecyclerView to display the flight results. Users can choose their preferred flight and add it to calendar.
<img src='https://github.com/Freebee2day/TravelApp/blob/main/TravelApp_Flight_Result.gif' title='Flight Result Walkthrough' width='' alt='Flight Result Walkthrough' />
    
### Itinerary Planning Fragment:
Plan travel events on calendar.  Click date to view the events/tasks on the selected date.
<img src='https://github.com/Freebee2day/TravelApp/blob/main/TravelApp_Flight_Added.gif' title='Add Flight Walkthrough' width='' alt='Add Flight Walkthrough' />

Data is saved in Android Studio SQLite: allow users to add tasks, remove task upon long press, and update task (check off).
<img src='https://github.com/Freebee2day/TravelApp/blob/main/TravelApp_Calendar_Tasks.gif' title='Tasks Walkthrough' width='' alt='Tasks Walkthrough' />

