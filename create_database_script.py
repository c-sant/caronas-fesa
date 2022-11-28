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
            [creator_id] TEXT,
            [title] TEXT,
            [description] TEXT,
            [place_of_departure] INTEGER,
            [destination] INTEGER,
            [available_weekdays] INTEGER,
            [available_seats] INTEGER,
            [departure_time] TEXT,
            [created_time] TEXT,
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
            [id] INTEGER PRIMARY KEY AUTOINCREMENT,
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
            [user_id] TEXT,
            [receive_notification] BOOLEAN,
            [place_of_departure] INTEGER,
            [max_distance_in_km] REAL,
            [available_weekdays] INTEGER,
            [initial_departure_time] TEXT,
            [final_departure_time] TEXT,
            FOREIGN KEY([user_id]) REFERENCES [User](id),
            FOREIGN KEY([available_weekdays]) REFERENCES [AvailableWeekdays](id) ON DELETE CASCADE,
            FOREIGN KEY([place_of_departure]) REFERENCES [Location](id) ON DELETE CASCADE
        );


        INSERT INTO [User] VALUES ('081200007', 'Guilherme Turtera', 'Sou o Guilherme, aluno de EA6', 1, '(11) 98741-0155', 'EF797C8118F02DFB649607DD5D3F8C7623048C9C063D532CC95C5ED7A898A64F');
        INSERT INTO [AvailableWeekdays] VALUES (1, 0, 1, 1, 1, 1, 1, 0);
        INSERT INTO [Location] VALUES (1, 10.0, 10.0);
        INSERT INTO [NotificationConfig] VALUES (1, '081200007', 1, 1, 10.0, 1, '20:00:00.000000000', '21:00:00.000000000');
        
        INSERT INTO [User] VALUES ('081200008', 'Carlos Santana', 'Sou o Carlos, aluno de EC6', 2, '(11) 98741-1234', 'EF797C8118F02DFB649607DD5D3F8C7623048C9C063D532CC95C5ED7A898A64F');
        INSERT INTO [AvailableWeekdays] VALUES (2, 0, 1, 1, 1, 1, 1, 0);
        INSERT INTO [Location] VALUES (2, 10.0, 10.0);
        INSERT INTO [NotificationConfig] VALUES (2, '081200008', 0, 2, 10.0, 2, '07:00:00.000000000', '08:00:00.000000000');


        
        INSERT INTO [User] VALUES ('081200009', 'Gabriel Mendes', 'Sou o Gabriel, aluno de EC6', 2, '(11) 98741-2345', 'EF797C8118F02DFB649607DD5D3F8C7623048C9C063D532CC95C5ED7A898A64F');
        INSERT INTO [AvailableWeekdays] VALUES (3, 0, 0, 0, 1, 1, 1, 0);
        INSERT INTO [Location] VALUES (3, 10.0, 10.0);
        INSERT INTO [NotificationConfig] VALUES (3, '081200008', 0, 3, 10.0, 3, '07:00:00.000000000', '08:00:00.000000000');
        
        
        INSERT INTO [User] VALUES ('081200010', 'Nathan Vilela', 'Sou o Nathan, aluno de EC6', 2, '(11) 98741-3456', 'EF797C8118F02DFB649607DD5D3F8C7623048C9C063D532CC95C5ED7A898A64F');
        INSERT INTO [User] VALUES ('081200011', 'Caio Rodrigues', 'Sou o Caio, aluno de EC6', 2, '(11) 98741-4567', 'EF797C8118F02DFB649607DD5D3F8C7623048C9C063D532CC95C5ED7A898A64F');


        INSERT INTO [AvailableWeekdays] VALUES (4, 0, 1, 0, 0, 0, 0, 0);
        INSERT INTO [Location] VALUES (4, 10.0, 10.0);
        INSERT INTO [Location] VALUES (5, 10.0000001, 10.0000001);
        INSERT INTO [Post] VALUES (1, '081200007', 'Caronas de segunda', 'Ofereço carona ida e volta na segunda', 4, 5, 4, 4, '08:30:00.000000000', '2022-11-25T20:36:38.548184100');
        
        INSERT INTO [AvailableWeekdays] VALUES (5, 0, 1, 1, 1, 1, 1, 0);
        INSERT INTO [Location] VALUES (6, 10.0, 10.0);
        INSERT INTO [Location] VALUES (7, 10.0000001, 10.0000001);
        INSERT INTO [Post] VALUES (2, '081200008', 'Caronas todos os dias úteis', 'Ofereço carona ida e volta todos os dias de aula, com excessão do sábado', 6, 7, 5, 1, '20:10:00.000000000', '2022-11-26T20:36:38.548184100');

        INSERT INTO [AvailableWeekdays] VALUES (6, 0, 1, 0, 1, 0, 1, 0);
        INSERT INTO [Location] VALUES (8, 10.0001, 10.0001);
        INSERT INTO [Location] VALUES (9, 10.0000001, 10.0000001);
        INSERT INTO [Post] VALUES (3, '081200010', 'Caronas seg, qua e sex', 'Ofereço carona ida segunda, quarta e sexta', 8, 9, 6, 1, '20:10:00.000000000', '2022-11-27T20:36:38.548184100');

        INSERT INTO [Notification] VALUES (1, 0, '081200007', 2, '2022-11-25T20:36:38.548184100');
        INSERT INTO [Notification] VALUES (2, 0, '081200007', 3, '2022-11-26T20:36:38.548184100');

        """
    )

finally:
    if conn:
        conn.close()
