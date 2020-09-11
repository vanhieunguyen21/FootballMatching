const functions = require('firebase-functions');

const admin = require('firebase-admin');
const db = admin.firestore();

exports.getTeam = functions.https.onCall(async (teamId) => {
    let i;
    const teamRef = db.collection('teams').doc(teamId);
    const teamRecord = await teamRef.get();
    if (teamRecord.exists) {
        // return result
        let result = teamRecord.data();
        result.id = teamId;
        // set photos
        const teamPhotoDocs = await teamRef.collection('photos').get();
        let photos = [];
        if (!teamPhotoDocs.empty) {
            teamPhotoDocs.forEach(photo => {
                let photoData = photo.data();
                photoData.id = photo.id;
                photos.push(photoData);
            });
        }
        result.photos = photos;
        // set team members
        const teamMemberDocs = await teamRef.collection('teamMembers').get();
        let teamMembers = [];
        if (!teamMemberDocs.empty) {
            let teamMemberPromises = [];
            for (i = 0 ; i < teamMemberDocs.docs.length ; i++){
                const teamMember = teamMemberDocs.docs[i];
                let teamMemberData = teamMember.data();
                teamMemberData.id = teamMember.id;
                teamMembers.push(teamMemberData);
                const playerId = teamMemberData.player;
                teamMemberPromises.push(db.collection('user').doc(playerId).get());
            }
            await Promise.all(teamMemberPromises).then((playerRecord) => {

            })

            teamMemberDocs.forEach((teamMember) => {
                let teamMemberData = teamMember.data();
                teamMemberData.id = teamMember.id;
                teamMembers.push(teamMemberData);
            })
        }
        result.teamMembers = teamMembers;
        // set game history
        const gameHistoryDocs = await teamRef.collection('gameHistory').get();
        let gameHistory = [];
        if (!gameHistoryDocs.empty) {
            gameHistoryDocs.forEach((playedGame) => {
                let playedGameData = playedGame.data();
                playedGameData.id = playedGame.id;
                gameHistory.push(playedGameData);
            })
        }
        result.gameHistory = gameHistory;
        // Set liked teams
        let likedTeams = [];
        const likedTeamsDocs = await teamRef.collection('likedTeams').get();
        if (!likedTeamsDocs.empty) {
            likedTeamsDocs.forEach(likedTeam => {
                let likedTeamData = likedTeam.data();
                likedTeamData.id = likedTeam.id;
                likedTeams.push(likedTeamData);
            });
        }
        result.likedTeams = likedTeams;
        // set liked by teams
        let likedByTeams = [];
        const likedByTeamsDocs = await teamRef.collection('likedByTeams').get();
        if (!likedByTeamsDocs.empty) {
            likedByTeamsDocs.forEach(likedByTeam => {
                let likedByTeamData = likedByTeam.data();
                likedByTeamData.id = likedByTeam.id;
                likedByTeams.push(likedByTeamData);
            });
        }
        result.likedByTeams = likedByTeams;
        // set liked players
        let likedPlayers = [];
        const likedPlayersDocs = await teamRef.collection('likedPlayers').get();
        if (!likedPlayersDocs.empty) {
            likedPlayersDocs.forEach(likedPlayer => {
                let likedPlayerData = likedPlayer.data();
                likedPlayerData.id = likedPlayer.id;
                likedPlayers.push(likedPlayerData);
            });
        }
        result.likedPlayers = likedPlayers;
        // set liked by players
        let likedByPlayers = [];
        const likedByPlayersDocs = await teamRef.collection('likedByPlayers').get();
        if (!likedByPlayersDocs.empty) {
            likedByPlayersDocs.forEach(likedByPlayer => {
                let likedByPlayerData = likedByPlayer.data();
                likedByPlayerData.id = likedByPlayer.id;
                likedByPlayers.push(likedByPlayerData);
            });
        }
        result.likedByPlayers = likedByPlayers;
        
        return result;
    } else {
        return null;
    }
})