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
        //set leader 
        const leaderId = result.leader;
        const leaderRecord = await db.collection('users').doc(leaderId).get();
        if (leaderRecord.exists) {
            let leaderData = leaderRecord.data();
            leaderData.id = leaderRecord.id;
            result.leader = leaderData;
        }
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
                teamMemberPromises.push(db.collection('users').doc(playerId).get());
            }
            await Promise.all(teamMemberPromises).then((playerRecords) => {
                for (i = 0 ; i < playerRecords.length ; i++) {
                    let playerData = playerRecords[i].data();
                    playerData.id = playerRecords[i].id;
                    teamMembers[i].player = playerData;
                }
                return null;
            });
            // teamMemberDocs.forEach((teamMember) => {
            //     let teamMemberData = teamMember.data();
            //     teamMemberData.id = teamMember.id;
            //     teamMembers.push(teamMemberData);
            // });
        }
        result.teamMembers = teamMembers;
        // set game history
        const gameHistoryDocs = await teamRef.collection('gameHistory').get();
        let gameHistory = [];
        if (!gameHistoryDocs.empty) {
            let gameHistoryPromisises = [];
            for (i = 0 ; i < gameHistoryDocs.docs.length ; i++) {
                const playedGame = gameHistoryDocs.docs[i];
                let playedGameData = playedGame.data();
                playedGameData.id = playedGame.id;
                gameHistory.push(playedGameData);
                const gameId = playedGameData.game;
                gameHistoryPromisises.push(db.collection('games').doc(gameId).get());
            }
            await Promise.all(gameHistoryPromisises).then((playedGames) => {
                for (i = 0; i < playedGames.length ; i++) {
                    let playedGameData = playedGames[i].data();
                    playedGameData.id = playedGame[i].id;
                    gameHistory[i].game = playedGameData;
                }
                return null;
            });
            // gameHistoryDocs.forEach((playedGame) => {
            //     let playedGameData = playedGame.data();
            //     playedGameData.id = playedGame.id;
            //     gameHistory.push(playedGameData);
            // })
        }
        result.gameHistory = gameHistory;
        // Set liked teams
        let likedTeams = [];
        const likedTeamsDocs = await teamRef.collection('likedTeams').get();
        if (!likedTeamsDocs.empty) {
            let likedTeamsPromisies = [];
            for (i = 0 ; i < likedTeamsDocs.docs.length; i++){
                const likedTeam = likedTeamsDocs.docs[i];
                let likedTeamData = likedTeam.data();
                likedTeamData.id = likedTeam.id;
                likedTeams.push(likedTeamData);
                const teamId = likedTeamData.team;
                likedTeamsPromisies.push(db.collection('teams').doc(teamId).get());
            }
            await Promise.all(likedTeamsPromisies).then((teamRecords) => {
                for (i = 0 ; i<likedTeams.length; i++){
                    let teamData = teamRecords[i].data();
                    teamData.id = teamRecords[i].id;
                    likedTeams[i].team = teamData;
                }
                return null;
            });
        }
        result.likedTeams = likedTeams;
        // set liked by teams
        let likedByTeams = [];
        const likedByTeamsDocs = await teamRef.collection('likedByTeams').get();
        if (!likedByTeamsDocs.empty) {
            let likedByTeamsPromisies = [];
            for (i = 0 ; i < likedByTeamsDocs.docs.length; i++){
                const likedByTeam = likedByTeamsDocs.docs[i];
                let likedByTeamData = likedByTeam.data();
                likedByTeamData.id = likedByTeam.id;
                likedByTeams.push(likedByTeamData);
                const teamId = likedByTeamData.team;
                likedByTeamsPromisies.push(db.collection('teams').doc(teamId).get());
            }
            await Promise.all(likedByTeamsPromisies).then((teamRecords) => {
                for (i = 0 ; i<likedByTeams.length; i++){
                    let teamData = teamRecords[i].data();
                    teamData.id = teamRecords[i].id;
                    likedByTeams[i].team = teamData;
                }
                return null;
            });
        }
        result.likedByTeams = likedByTeams;
        // set liked players
        let likedPlayers = [];
        const likedPlayersDocs = await teamRef.collection('likedPlayers').get();
        if (!likedPlayersDocs.empty) {
            const likedPlayersPromises = [];
            for (i = 0 ; i < likedPlayersDocs.docs.length; i++){
                const likedPlayer = likedPlayersDocs.docs[i];
                const likedPlayerData = likedPlayer.data();
                likedPlayerData.id = likedPlayer.id;
                likedPlayers.push(likedPlayerData);
                const playerId = likedPlayerData.player;
                likedPlayersPromises.push(db.collection('users').doc(playerId).get());
            }
            await Promise.all(likedPlayersPromises).then((playerRecords) => {
                for (i = 0 ; i<likedPlayers.length; i++){
                    const playerData = playerRecords[i].data();
                    playerData.id = playerRecords[i].id;
                    likedPlayers[i].player = playerData;
                }
                return null;
            })
        }
        result.likedPlayers = likedPlayers;
        // set liked by players
        let likedByPlayers = [];
        const likedByPlayersDocs = await teamRef.collection('likedByPlayers').get();
        if (!likedByPlayersDocs.empty) {
            const likedByPlayersPromises = [];
            for (i = 0 ; i < likedByPlayersDocs.docs.length; i++){
                const likedByPlayer = likedByPlayersDocs.docs[i];
                const likedByPlayerData = likedByPlayer.data();
                likedByPlayerData.id = likedByPlayer.id;
                likedByPlayers.push(likedByPlayerData);
                const playerId = likedByPlayerData.player;
                likedByPlayersPromises.push(db.collection('users').doc(playerId).get());
            }
            await Promise.all(likedByPlayersPromises).then((playerRecords) => {
                for (i = 0 ; i<likedByPlayers.length; i++){
                    const playerData = playerRecords[i].data();
                    playerData.id = playerRecords[i].id;
                    likedByPlayers[i].player = playerData;
                }
                return null;
            })
        }
        result.likedByPlayers = likedByPlayers;
        
        return result;
    } else {
        return null;
    }
})