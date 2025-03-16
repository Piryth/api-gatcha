const user = process.env.MONGO_INITDB_ROOT_USERNAME ?? "root";
const pwd = process.env.MONGO_INITDB_ROOT_PASSWORD ?? "example";

db = connect(`mongodb://${user}:${pwd}@localhost:27017/admin`);

print(`Database initialization started...`);

const invocation = require('/seed/invocation.json');
const player = require('/seed/player.json');

db = db.getSiblingDB("api_invocation");
db.invocation.insertMany(invocation);

db = db.getSiblingDB("api_player");
db.player.insertMany(player);

print(`Database has been initialized successfully!`);