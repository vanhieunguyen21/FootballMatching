const functions = require('firebase-functions');

const admin = require('firebase-admin');
const db = admin.firestore();

exports.getUser = functions.https.onCall(async (uid) => {
    let i;
    const userRef = db.collection('users').doc(uid);
    const userRecord = await userRef.get();
    if (userRecord.exists) {
        // return result
        let result = userRecord.data();
        result.id = uid;
        // set photos
        const userPhotosDocs = await userRef.collection('photos').get();
        let photos = [];
        if (!userPhotosDocs.empty) {
            userPhotosDocs.forEach(photo => {
                let photoData = photo.data();
                photoData.id = photo.id;
                photos.push(photoData);
            });
        }
        result.photos = photos;
        // set joined teams
        let joinedTeams = [];
        const joinedTeamsDocs = await userRef.collection('joinedTeams').get();
        if (!joinedTeamsDocs.empty) {
            let joinedTeamsPromises = [];
            for (i = 0 ; i < joinedTeamsDocs.docs.length ; i++){
                let joinedTeamData = joinedTeamsDocs.docs[i].data();
                joinedTeamData.id = joinedTeamsDocs.docs[i].id;
                joinedTeams.push(joinedTeamData);
                const teamId = joinedTeamData.team;
                joinedTeamsPromises.push(db.collection('teams').doc(teamId).get());
            }
            await Promise.all(joinedTeamsPromises).then((teamRecords) => {
                for (i = 0 ; i < joinedTeams.length ; i++){
                    let teamData = teamRecords[i].data();
                    teamData.id = teamRecords[i].id;
                    joinedTeams[i].team = teamData;
                }
                return null;
            })
        }
        result.joinedTeams = joinedTeams;
        // set liked teams
        let likedTeams = [];
        const likedTeamsDocs = await userRef.collection('likedTeams').get();
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
        const likedByTeamsDocs = await userRef.collection('likedByTeams').get();
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
        return result;
    } else {
        return null;
    }
})