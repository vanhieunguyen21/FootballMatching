const functions = require('firebase-functions');

const admin = require('firebase-admin');
const db = admin.firestore();

exports.teamLikeTeam = functions.https.onCall(async (data) => {
    const sourceTeamId = data.sourceTeamId;
    const destinationTeamId = data.destinationTeamId;
    const sourceTeamRef = db.collection('teams').doc(sourceTeamId).collection('likedTeams').doc(destinationTeamId);
    const destinationTeamRef = db.collection('teams').doc(destinationTeamId).collection('likedByTeams').doc(sourceTeamId);
    const currentTimestamp = admin.firestore.FieldValue.serverTimestamp();
    const sourceTeamLikeData = {
        team: destinationTeamId,
        actionTimestamp: currentTimestamp
    }
    const destinationTeamLikeData = {
        team: sourceTeamId,
        actionTimestamp: currentTimestamp
    }
    const batch = db.batch();
    batch.create(sourceTeamRef, sourceTeamLikeData);
    batch.create(destinationTeamRef, destinationTeamLikeData);
    await batch.commit().catch((error) => {
        return {
            result: 0,
            errorCode: error.code,
            errorMessage: error.message
        }
    });
    return {
        result: 1,
        message: 'Liked team'
    }
})

exports.playerLikeTeam = functions.https.onCall(async (data) => {
    const playerId = data.playerId;
    const destinationTeamId = data.destinationTeamId;
    const playerRef = db.collection('users').doc(playerId).collection('likedTeams').doc(destinationTeamId);
    const destinationTeamRef = db.collection('teams').doc(destinationTeamId).collection('likedByPlayers').doc(playerId);
    const currentTimestamp = admin.firestore.FieldValue.serverTimestamp();
    const playerData = {
        team: destinationTeamId,
        actionTimestamp: currentTimestamp
    }
    const destinationTeamData = {
        player: playerId,
        actionTimestamp: currentTimestamp
    }
    const batch = db.batch();
    batch.create(playerRef, playerData);
    batch.create(destinationTeamRef, destinationTeamData);
    await batch.commit().catch((error) => {
        return {
            result: 0,
            errorCode: error.code,
            errorMessage: error.message
        }
    });
    return {
        result: 1,
        message: 'Liked team'
    }
})

exports.teamLikePlayer = functions.https.onCall(async (data) => {
    const sourceTeamId = data.sourceTeamId;
    const playerId = data.playerId;
    const sourceTeamRef = db.collection('teams').doc(sourceTeamId).collection('likedPlayers').doc(playerId);
    const playerRef = db.collection('users').doc(playerId).collection('likedByTeams').doc(sourceTeamId);
    const currentTimestamp = admin.firestore.FieldValue.serverTimestamp();
    const sourceTeamData = {
        player: playerId,
        actionTimestamp: currentTimestamp
    }
    const playerData = {
        team: sourceTeamId,
        actionTimestamp: currentTimestamp
    }
    const batch = db.batch();
    batch.create(sourceTeamRef, sourceTeamData);
    batch.create(playerRef, playerData);
    await batch.commit().catch((error) => {
        return {
            result: 0,
            errorCode: error.code,
            errorMessage: error.message
        }
    });
    return {
        result: 1,
        message: 'Liked player'
    }
})