db.createUser(
    {
        user: "usr-customer-mongo",
        pwd: "pwd-customer-mongo",
        roles: [
            {
                role: "readWrite",
                db: "customer-dev-mongo"
            }
        ]
    }
);