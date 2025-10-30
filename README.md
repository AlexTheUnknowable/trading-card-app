# 🃏 Trading Card Marketplace

A **full-stack trading card web app** built with **Java**, **JDBC**, **PostgreSQL**, **Spring Boot**, and **React**.
Users can collect, trade, and sell Pokémon-style cards — all powered by data from the [PokeAPI](https://pokeapi.co/).

---

## 🚀 Features

- **User Authentication**  
  Create accounts and log in securely. Each user has a balance and a collection of cards.

- **"Pull" cards**  
  Users can open virtual “packs” of cards. Each pack randomly generates cards using data fetched from the database, which is populated with data from PokeAPI.

- **Marketplace**  
  Players can list cards from their collection on a store page for other users to buy.  
  Purchases automatically transfer cards and currency between users.

- **Database Seeding**  
  The backend populates the card templates table using data from PokeAPI, ensuring an evolving and data-driven card set.

---

## 🧠 Tech Stack

| Layer | Technology | Description |
|-------|-------------|-------------|
| **Frontend** | React | Handles user interactions and dynamic UI updates |
| **Backend** | Spring Boot (Java) | REST API for business logic and routing |
| **Database** | PostgreSQL | Stores users, card templates, and items |
| **ORM / Access** | JDBC | Direct SQL queries for fine-grained control |
| **API Integration** | PokeAPI | External source for card data |

---

## ⚙️ Core Functionalities (Technical Breakdown)

- **Data Model:**  
  - `users` → stores account credentials, balances
  - `cards` → templates for items, pre-populated from PokeAPI 
  - `items` → represents individual user-owned cards.
  - - "Price" variable determines whether item is available for sale on the store.

- **Transaction Handling:**  
  - Atomic SQL operations ensure that both currency and card ownership transfer safely.  
  - Balance updates and ownership changes are wrapped in transactional JDBC operations.

- **Frontend React Components:**  
  - `CardList`, `MyCardsPage`, `PullPage`, `StorePage`, `ItemInfoComponent`  
  - Uses React hooks for state management and REST calls to the backend.

---

## 📊 Example User Flow

1. Sign up or log in.

2. “Pull” a pack to receive random cards from the PokeAPI pool.

3. View your collection on your "My Cards" page.

4. Put a card up for sale on the store.

5. Another user can purchase it, transferring balance and ownership automatically.

## 💡 What This Project Demonstrates

✅ Full-stack architecture (frontend ↔ backend ↔ database)  
✅ REST API design and data flow  
✅ Real-world API integration (PokeAPI)  
✅ SQL schema design and transactional logic  
✅ State management and authentication  
✅ CRUD operations across multiple entities  

---

## 🧭 Future Improvements

- JWT-based authentication  
- User-to-user trades (not just store sales)  
- Pack rarity tiers  
- Real-time marketplace updates via WebSockets  
- Docker setup for easier local deployment  