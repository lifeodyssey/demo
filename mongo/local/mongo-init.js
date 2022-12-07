db.createUser(
    {
        user: "zhenjia",
        pwd: "zhenjia",
        roles: [
            {
                role: "readWrite",
                db:"book_database"
            }
        ]
    }
);
//TODO:could not initiate db and user in the beginning