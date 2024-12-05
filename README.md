
````
# Ringmaster Backend App

Ringmaster is a fun, gamified backend application built with **Clojure**. It demonstrates the basics of backend development, API design, and state management. Players can register, claim the ring, and compete to become the "Ringmaster."

## Features

- **Register Players**: Players can register with a unique name.
- **Claim the Ring**: Registered players can claim the ring to become the Ringmaster.
- **Check Status**: Displays the current Ringmaster or shows if the ring is unclaimed.
- **Dynamic Updates**: Allows multiple players to compete for the ring.

---

## Tech Stack

- **Language**: [Clojure](https://clojure.org/)
- **Frameworks/Libraries**:
  - [Compojure](https://github.com/weavejester/compojure) for routing
  - [Ring](https://github.com/ring-clojure/ring) for HTTP handling
  - [Cheshire](https://github.com/dakrone/cheshire) for JSON serialization

---

## API Endpoints

### 1. **Register a Player**

- **Endpoint**: `POST /register`
- **Request**:
  - `name`: Player's name (e.g., `"Alice"`)
- **Response**:

  {
    "player-id": "uuid",
    "message": "Player Alice registered successfully!"
  }


### 3. **Check Ring Status**

- **Endpoint**: `GET /status`
- **Response**:
  - **If the ring is claimed**:

    {
      "ringmaster": "Alice"
    }

  - **If the ring is unclaimed**:

    {
      "message": "The ring is unclaimed!"
    }


---

## How to Run the App

1. **Clone the Repository**:

   git clone <repository-url>
   cd ringmaster-backend


### 2. **Claim the Ring**

- **Endpoint**: `POST /claim`
- **Request**:
  - `player-id`: UUID of the player (retrieved from the `/register` endpoint).
- **Response**:
  - **If successful**:

    {
      "success": true,
      "message": "Alice is now the Ringmaster!"
    }

  - **If the player is not found**:

    {
      "success": false,
      "message": "Player not found!"
    }


### 3. **Check Ring Status**

- **Endpoint**: `GET /status`
- **Response**:
  - **If the ring is claimed**:

    {
      "ringmaster": "Alice"
    }

  - **If the ring is unclaimed**:

    {
      "message": "The ring is unclaimed!"
    }


---

## How to Run the App

1. **Clone the Repository**:

   git clone <repository-url>
   cd ringmaster-backend


### 2. Install Dependencies

Ensure you have **Leiningen** installed. If it's not already installed, follow these steps:

1. **Download Leiningen**:
   Visit the [Leiningen official site](https://leiningen.org/) and download the script.

2. **Install Leiningen**:
   Follow the instructions provided on the website for your operating system.

3. **Verify Installation**:
   Run the following command to verify that Leiningen is installed correctly:

   lein --version


You should see the version of Leiningen printed to the console, confirming the installation was successful.

---

### 3. Run the App

Start the server by running the following command:


lein ring server

The server will be available at: http://localhost:3000



---

## Testing the Endpoints

You can test the backend using `curl`, **Postman**, or any other API testing tool. Here are some examples:

### Register a Player
- **Endpoint**: `POST /register`
- **Command**:

curl -X POST -d "name=Alice" http://localhost:3000/register

- **Response**:

 {
   "player-id": "uuid",
   "message": "Player Alice registered successfully!"
 }



### Claim the Ring
- **Response**:
- **If successful**:

 {
   "success": true,
   "message": "Alice is now the Ringmaster!"
 }

- **If unsuccessful**:

 {
   "success": false,
   "message": "Player not found!"
 }


---


### Check Ring Status
- **Endpoint**: `GET /status`
- **Command**:

curl http://localhost:3000/status


````
