db.auth('dev_mongo_username', 'dev_mongo_pwd')

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