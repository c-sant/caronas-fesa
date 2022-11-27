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
            [password] TEXT,
            [notification_config] INTEGER,
            FOREIGN KEY([notification_config]) REFERENCES [NotificationConfig](id)
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
            [creator_id] TEXT,
            [title] TEXT,
            [description] TEXT,
            [place_of_departure] INTEGER,
            [destination] INTEGER,
            [available_weekdays] INTEGER,
            [available_seats] INTEGER,
            [departure_time] TEXT,
            FOREIGN KEY([creator_id]) REFERENCES [User](id),
            FOREIGN KEY([place_of_departure]) REFERENCES [Location](id) ON DELETE CASCADE,
            FOREIGN KEY([destination]) REFERENCES [Location](id) ON DELETE CASCADE,
            FOREIGN KEY([available_weekdays]) REFERENCES [AvailableWeekdays](id) ON DELETE CASCADE
        );

        DROP TABLE IF EXISTS [AvailableWeekdays];
        CREATE TABLE IF NOT EXISTS [AvailableWeekdays] (
            [id] INTEGER PRIMARY KEY,
            [sunday] BOOLEAN,
            [monday] BOOLEAN,
            [tuesday] BOOLEAN, 
            [wednesday] BOOLEAN, 
            [thursday] BOOLEAN, 
            [friday] BOOLEAN, 
            [saturday] BOOLEAN
        );

        DROP TABLE IF EXISTS [Notification];
        CREATE TABLE IF NOT EXISTS [Notification] (
            [id] INTEGER PRIMARY KEY,
            [viewed] BOOLEAN,
            [subscriber] TEXT,
            [post] INTEGER,
            [notification_time] TEXT,
            FOREIGN KEY([subscriber]) REFERENCES [User](id),
            FOREIGN KEY([post]) REFERENCES [Post](id)
        );

        
        DROP TABLE IF EXISTS [NotificationConfig];
        CREATE TABLE IF NOT EXISTS [NotificationConfig] (
            [id] INTEGER PRIMARY KEY,
            [receive_notification] BOOLEAN,
            [place_of_departure] INTEGER,
            [available_weekdays] INTEGER,
            [initial_departure_time] TEXT,
            [final_departure_time] TEXT,
            FOREIGN KEY([available_weekdays]) REFERENCES [AvailableWeekdays](id),
            FOREIGN KEY([place_of_departure]) REFERENCES [Location](id) ON DELETE CASCADE
        );
        """
    )

finally:
    if conn:
        conn.close()
