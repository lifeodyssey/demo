db.auth('local_mongo_username', 'local_mongo_pwd')

db = db.getSiblingDB('book')

db.createUser({
    user: 'bookadmin',
    pwd: 'bookpwd',
    roles: [
        {
            role: 'root',
            db: 'book',
        },
    ],
});