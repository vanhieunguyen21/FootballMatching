const admin = require('firebase-admin');
admin.initializeApp();

const createUser = require('./createUser');
const createTeam = require('./createTeam');
const getUser = require('./getUser');
const getTeam = require('./getTeam');
const queryPlayers = require('./queryPlayers');

exports.createUser = createUser.createUser;
exports.createTeam = createTeam.createTeam;
exports.getUser = getUser.getUser;
exports.getTeam = getTeam.getTeam;
exports.queryTeams = require('./queryTeams');
exports.match = require('./match');
exports.queryAllPlayers = queryPlayers.queryAllPlayers;