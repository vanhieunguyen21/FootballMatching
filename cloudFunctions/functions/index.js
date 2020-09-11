const admin = require('firebase-admin');
admin.initializeApp();

const createUser = require('./createUser');
const getUser = require('./getUser');
const getTeam = require('./getTeam');

exports.createUser = createUser.createUser;
exports.getUser = getUser.getUser;
exports.getTeam = getTeam.getTeam;
exports.queryTeams = require('./queryTeams');