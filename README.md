# ResuMaker

ResuMaker is a Spring Boot application that generates PDF resumes based on user input. The application uses the iText library to create and format the PDF documents.

## Features

- Generate PDF resumes with headers, summary, education, work experience, and skills sections.
- Add hyperlinks to the PDF without displaying the full URL.
- Customize line spacing in the PDF.

## Prerequisites

- Java 17
- Maven
- Spring Boot

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/Adarsh0311/ResuMaker.git
   cd ResuMaker
   


2. Json data for resume
```json
{
  "contact": {
    "name": "John Doe",
    "email": "john.doe@example.com",
    "phone": "123-456-7890",
    "address": "123 Main St, Anytown, USA",
    "summary": "Experienced software developer with a strong background in Java and Spring Boot."
  },
  "education": [
    {
      "degree": "B.Sc. in Computer Science",
      "university": "University of Example"
    }
  ],
  "workExperience": [
    {
      "company": "Example Corp",
      "title": "Senior Software Engineer",
      "points": [
        "Developed and maintained web applications using Java and Spring Boot.",
        "Led a team of 5 developers."
      ]
    }
  ],
  "skills": {
    "skills": {
      "Programming Languages": ["Java", "Python", "JavaScript"],
      "Frameworks": ["Spring Boot", "React"]
    }
  }
}