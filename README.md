# PancakeLab

PancakeLab is a Java-based application designed to simulate a pancake ordering system. The application follows a modular architecture, adhering to best practices like object-oriented programming (OOP), test-driven development (TDD), and design patterns. The project is managed with Maven, containerized using Docker, and documented with UML diagrams.

## Table of Contents

- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Building the Project](#building-the-project)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [Docker Usage](#docker-usage)
- [Documentation](#documentation)

## Getting Started

These instructions will help you set up and run the PancakeLab project on your local machine for development and testing purposes.

### Prerequisites

- **Java 17**: Ensure you have Java 17 installed.
- **Maven 3.8+**: Maven is used for dependency management and building the project.
- **Docker**: Docker is required if you want to run the application inside a container.

### Installation

1. **Clone the repository**:
   ```bash
   git clone -b dev git@github.com:amitfritz8/pancake-lab.git
   cd pancake-lab
   ```

2. **Verify Java and Maven installation**:
   ```bash
   java -version
   mvn -version
   ```

## Project Structure

Here's a brief overview of the key files and directories in the project:

\`\`\`
PancakeLab/
│
├── Dockerfile                   # Docker configuration for containerization
├── pom.xml                      # Maven project file for dependency management
├── README.md                    # Project documentation
├── src/                         # Source code and tests
│   ├── main/                    # Application source code
│   │   ├── java/                # Java classes and packages
│   │   └── resources/           # Configuration files (e.g., logback.xml)
│   └── test/                    # Unit tests
├── docs/                        # UML diagrams and other documentation
│   └── uml/                     # UML diagrams in .puml format
└── logs/                        # Application log files
\`\`\`

### Key Components

- **Domain Entities / Value Object**: Classes representing the core business logic, such as `PancakeOrderEntity`, `RoomNumber`, `Building`.
- **Services**: Application services like `PancakeOrderService` handle the core operations.
- **Event Listeners**: Implement domain event handling using the Observer pattern, such as `ChefEventListener`, `CustomerEventListener`.
- **Repositories**: Interfaces and in-memory implementations for data persistence, like `PancakeRepository` and `OrderRepository`.

## Building the Project

To build the PancakeLab project, follow these steps:

1. **Navigate to the project directory**:
   ```bash
   cd pancake-lab
   ```

2. **Build the project using Maven**:
   ```bash
   mvn clean package
   ```

This command will compile the source code, run the tests, and package the application into a JAR file located in the `target/` directory.

## Running the Application

### Locally

You can run the application locally using the following command:

```bash
java -jar target/pancake-lab-1.0-SNAPSHOT.jar
```

### Docker

If you prefer to run the application inside a Docker container, follow these steps:

1. **Build the Docker image**:
   ```bash
   docker build -t pancakelab-app .
   ```

2. **Run the Docker container**:
   ```bash
   docker run --rm pancakelab-app
   ```

This will start the PancakeLab application inside a Docker container.

## Testing

To run the tests, execute the following Maven command:

```bash
mvn test
```

This will run all unit tests defined in the project.

## Docker Usage

### Build the Docker Image

To build the Docker image, run:

```bash
docker build -t pancakelab-app .
```

### Run the Docker Container

To run the application in a Docker container:

```bash
docker run --rm pancakelab-app
```

This command runs the container and removes it after it stops.

## Documentation

The project includes several UML diagrams to help understand the system architecture:

- **Class Diagrams**: Show the structure of the system in terms of classes and relationships.
- **Use Case Diagrams**: Describe the functional requirements of the system.
- **State Diagrams**: Explain the states of key entities in the system.
- **Sequence Diagrams**: Detail the interaction between objects in various scenarios.

These diagrams can be found in the `docs/uml/` directory.
