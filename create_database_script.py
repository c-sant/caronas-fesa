import sqlite3 as sql

conn = None

try:
    conn = sql.connect("database.db")
    cursor = conn.cursor()

    cursor.executescript(
        """
        DROP TABLE IF EXISTS [User];
        CREATE TABLE IF NOT EXISTS [User] (
            [id] TEXT PRIMARY KEY,
            [name] TEXT,
            [description] TEXT,
            [course] INTEGER,
            [phone_number] TEXT,
            [password] TEXT
        );
        
        DROP TABLE IF EXISTS [Location];
        CREATE TABLE IF NOT EXISTS [Location] (
            [id] INTEGER PRIMARY KEY,
            [latitude] REAL,
            [longitude] REAL
        );
        
        DROP TABLE IF EXISTS [Post];
        CREATE TABLE IF NOT EXISTS [Post] (
            [id] INTEGER PRIMARY KEY,
            [title] TEXT,
            [description] TEXT,
            [place_of_departure] INTEGER,
            [destination] INTEGER,
            available_seats INTEGER,
            departure_time TEXT,
            FOREIGN KEY([place_of_departure]) REFERENCES [Location](id),
            FOREIGN KEY([destination]) REFERENCES [Location](id)
        );

        DROP TABLE IF EXISTS [AvailableWeekdays];
        CREATE TABLE IF NOT EXISTS [AvailableWeekdays] (
            [id] INTEGER PRIMARY KEY,
            [post_id] INTEGER,
            [sunday] BOOLEAN,
            [monday] BOOLEAN,
            [tuesday] BOOLEAN, 
            [wednesday] BOOLEAN, 
            [thursday] BOOLEAN, 
            [friday] BOOLEAN, 
            [saturday] BOOLEAN,
            FOREIGN KEY([post_id]) REFERENCES [Post](id) 
        )
        """
    )

finally:
    if conn:
        conn.close()