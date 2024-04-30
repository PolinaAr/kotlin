# Client management system
___

## Application Launch Instructions
___

This guide provides instructions on how to launch the application using two different methods. Please follow the one that best suits your setup.

## Option 1: Using Docker

For those who prefer Docker for managing PostgreSQL, follow these steps:

1. **Ensure Docker is Running**: First, make sure Docker is installed and running on your machine. If not, please install Docker from [the official site](https://www.docker.com/get-started) and start it.

2. **Run PostgreSQL Container**: Execute the following command to run a PostgreSQL container:

    ```bash
    docker run --name kotlin-postgres -e POSTGRES_DB=kotlin -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=root -p 5433:5432 -d postgres
    ```

   This command pulls the PostgreSQL image (if not already pulled), creates a new container named `kotlin-postgres`, and sets up a database named `kotlin` with the specified user and password.

3. **Configure Application**: In the project's launch settings, set `spring.profiles.active=local` to use the local profile.

4. **Launch Application**: Now, you can start your application. The application will connect to the PostgreSQL database running in the Docker container.

## Option 2: Using Local PostgreSQL

If you have PostgreSQL installed locally and prefer using it, follow these steps:

1. **Create Database**: First, create a new database named `kotlin`. You can do this using PostgreSQL's command line tools or a graphical interface like pgAdmin.

2. **Configure Application**: Edit the `application-local.yaml` file to include the correct database connection settings (username, password, database name, and host).

3. **Set Active Profile**: In the project's launch settings, set `spring.profiles.active=local` to use the local profile.

4. **Launch Application**: With the configuration set, you can now start your application. It will connect to the local PostgreSQL database you've configured.

---

By following these instructions, you should be able to get your application up and running smoothly.