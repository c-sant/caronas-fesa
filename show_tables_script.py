import sqlite3 as sql
import pandas as pd


def read_table(table: str) -> pd.DataFrame:
    """
    Reads a table from the database and returns it as a DataFrame.

    Args:
        table (str): The table name.

    Returns:
        DataFrame: The table as a DataFrame object.
    """

    conn = None

    try:
        conn = sql.connect("database.db")

        sql_query = f"SELECT * FROM [{table}]"

        df = pd.read_sql_query(sql_query, conn)

        return df

    finally:
        if conn:
            conn.close()


if __name__ == "__main__":
    print(read_table("AvailableWeekdays"))
