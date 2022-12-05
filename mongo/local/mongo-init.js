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