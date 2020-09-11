const functions = require('firebase-functions');

const admin = require('firebase-admin');
const db = admin.firestore();

exports.getUser = functions.https.onCall(async (uid) => {
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

        const userType = result.userType;
        // Player information
        if (userType === 'Player') {
            // set joined teams
            let joinedTeams = [];
            const joinedTeamsDocs = await userRef.collection('joinedTeams').get();
            if (!joinedTeamsDocs.empty) {
                joinedTeamsDocs.forEach(joinedTeam => {
                    let joinedTeamData = joinedTeam.data();
                    joinTeamData.id = joinedTeam.id;
                    joinedTeams.push(joinedTeamData);
                });
            }
            result.joinedTeams = joinedTeams;
            // set liked teams
            let likedTeams = [];
            const likedTeamsDocs = await userRef.collection('likedTeams').get();
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
            const likedByTeamsDocs = await userRef.collection('likedByTeams').get();
            if (!likedByTeamsDocs.empty) {
                likedByTeamsDocs.forEach(likedByTeam => {
                    let likedByTeamData = likedByTeam.data();
                    likedByTeamData.id = likedByTeam.id;
                    likedByTeams.push(likedByTeamsData);
                });
            }
            result.likedByTeams = likedByTeams;
        }
        // Referee information
        else if (userType === 'Referee') {
            // Do nothing for now
        }
        
        return result;
    } else {
        return null;
    }
})