# MemoryRace

**MemoryRace** is an application designed to simplify the identification and retrieval of race photos using bib number recognition.

It allows runners to quickly access the photos in which they appear by simply entering their bib number, eliminating the need to manually browse through large image galleries.

---

## Project Context

During sporting events such as marathons, half-marathons, or triathlons, professional and amateur photographers play a key role in capturing memorable moments of runners. These photos are not only valuable keepsakes for participants, but they are also used by event organizers for promotion purposes.

Currently, these photos are often published on the official event website without any specific organization. As a result, runners must browse through large image galleries to find the photos in which they appear, which is time-consuming and inefficient.

To address this issue, some events have started using systems that automatically identify photos based on a runner’s bib number. While this greatly improves the user experience, such solutions are still not widely adopted.

MemoryRace aims to provide a simple, accessible, and efficient solution that enables runners to retrieve their photos effortlessly through automated bib number recognition.

---

## Project Objectives

The main goal of MemoryRace is to improve the post-race experience for runners by simplifying access to their photos.

Instead of manually searching through hundreds of images, participants can simply enter their bib number to instantly retrieve the corresponding photos.

The objectives are twofold:

1. Save time for runners by automating photo searches.  
2. Assist photographers and event organizers by simplifying image management and distribution.

---

## Technologies Used

- **Python**
  - OpenCV (cv2)
  - Pytesseract (OCR)
- **Java**
  - Swing (GUI)
  - Java Archive (JAR)
- **MariaDB**

---

## Project Structure

### Data Management

The project includes a photo database used to test and validate the different features. This database contains real race photos, reflecting real-world conditions and challenges.

### Bib Number Extraction

The application uses an Optical Character Recognition (OCR) system implemented in Python via **Pytesseract**.  
This module extracts bib numbers from images and associates each image with the detected bib numbers. The extracted data is then stored in the database for later use.

### Bib-to-Photo Association

Once the bib numbers are extracted, a Java program associates each runner with a list of photo references in which their bib number appears.  
This preprocessing step ensures fast response times when users request access to their photos.

### Graphical User Interface (GUI)

As the project targets large-scale usage, the graphical user interface is a key component.  
The GUI is designed to be simple, intuitive, and accessible, minimizing the number of actions required for users to retrieve their photos.

Java Swing was chosen to implement the interface.
