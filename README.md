# Greenpanion - An Environmental Impact App Made in A Week For a Hackathon

## Context

During the month of August 2022, I was doing an internship at [Endava](https://www.endava.com/) where I got to learn the basics of Android in Java. 
They had other internships at time as well— on other languages, technologies and fields— and our mentors ran an internal hackathon where we were grouped into teams of ~7 members.
Each team had the following breakdown: 3 people working on the web frontend, 1 working on the backend, 2 doing testing and 1 on Android— that's the part of the story where I come in!

## App Description

## Main Idea

The theme of the Hackathon this app was made for was _**Environmental Impact**_. After some brainstorming, we decided on making a cute, gamified, 
Duolingo-style app where users would recycle things and earn XP, level up and earn some— obviously fictitious— real-life rewards! 
There were 5 teams in the company's internal Hackathon and our team won 2<sup>nd</sup> place (along with a hefty cash prize). 

## Features

The whole idea is that users would go to a recycling centre, recycle, and that a machine there would produce a QR-code describing what they had just recycled (e.g. paper, metal, plastic).
For this:
- I implemented a QR scanner
- used the Google Maps SDK to show the user the locations of the nearest recycling centres
- had a screen with a user guide as to what rewards one can win
- had a screen where the user can see their level and how much XP they need to get to the next one
- did the backend using Firebase (Firebase Auth for user login and Firebase Firestore for user data)

## Look and Feel

### User Account Login:


<img src="https://github.com/user-attachments/assets/c694ea9c-f6bd-443b-bb62-faa954684be5" width="25%">


### QR Code Reader, XP, Levels and Map:


<img src="https://github.com/user-attachments/assets/03d5b2db-189c-4646-8a20-f3354fa5f28c" width="25%">


### Sign Up, Log Out:


<img src="https://github.com/user-attachments/assets/a18100cd-5dbf-4a15-9cf0-645f4d9344e3" width="25%">


## Building and Running the Project

This version of the project has been built most recently with **Android Studio Koala | 2024.1.1**, targeting Android SDK 34. 

**NOTE**: For obivous reasons, I will not allow any foreign Android projects to connect to my instance of Firebase.
Make sure to run your own Firebase project. 

## License

MIT License
