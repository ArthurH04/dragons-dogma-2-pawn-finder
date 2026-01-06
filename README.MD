# 🐉 Dragons Dogma 2 Pawn Finder Project

Dragons Dogma 2 Pawn Finder is a website designed to help DD2 players find pawns easily from anywhere, without needing to open the game.

This is a complete project that includes frontend, backend, and advanced features such as security, JWT authentication, and database integration.

---

## 🛠️ Tech Stack

- ⚛️ **Frontend**: React  
- 🏗️ **Backend**: Spring Boot  
- 🗄️ **Database**: PostgreSQL 15 (via Docker + Flyway)  
- 🐳 **Tools**: Docker, Docker Compose, PgAdmin  
- 🔒 **Security**: JWT authentication and role-based access control  

---

## 📋 Prerequisites

- 🟢 Node.js (v22.11.0 recommended)  
- 📦 npm or yarn  
- 🐳 Docker and Docker Compose  

---

## ▶️ Running Locally

### 1️⃣ Clone the project

```bash
git clone https://link-to-project
cd project-name
```

---

### 2️⃣ Frontend

1. Go to the frontend directory:

```bash
cd frontend
```

2. Install dependencies:

```bash
npm install
```

3. Start the server:

```bash
npm run start
```

> ⚠️ Note: Some projects use `npm run dev` for development mode, check your `package.json`.

---

### 3️⃣ Backend

1. Go to the backend directory:

```bash
cd backend
```


2. Create the `.env` file from the example:

```bash
cp .env.example .env
```

3. Configure the environment variables in `.env` as below (fill with your own values):

**Database Configuration**
```
DB_USER=user
DB_PASSWORD=your_secure_password_here
DB_NAME=name
DB_PORT=1234
```

**Application Email Configuration (for sending emails, e.g. password recovery)**
```
APP_EMAIL=your_email@gmail.com
APP_PASSWORD=your_app_password
```

**PgAdmin Configuration**
```
PGADMIN_EMAIL=admin@admin.com
PGADMIN_PASSWORD=your_admin_password_here
PGADMIN_PORT=5050
```

**JWT Configuration**
```
JWT_SECRET=your_512bit_secret_key
JWT_EXPIRATION=3600000 # in milliseconds (e.g. 1h)
JWT_REFRESH_EXPIRATION=604800000 # in milliseconds (e.g. 7 days)
```

**Cloudinary Configuration (optional, for image upload)**
```
CLOUD_NAME=your_cloud_name
API_KEY=your_api_key
API_SECRET=your_api_secret
```

> See the `.env.example` file for all available variables.

4. Start the Docker containers:

```bash
docker compose up -d
```

5. Verify if the services are running:

```bash
docker ps
```

---

## 📝 Notes

- Restart the server after changing environment variables.