const functions = require('firebase-functions');

const admin = require('firebase-admin');
const db = admin.firestore();

exports.createTeam = functions.https.onCall(async (data) => {
    const teamName = data.name;
    const leader = data.leader;
    const currentTimestamp = admin.firestore.FieldValue.serverTimestamp();
    const teamData = {
        name: teamName,
        avatar: null,
        contactTelephones: [],
        contactEmails: [],
        location: null,
        introduction: null,
        status: 'active',
        mainGroup: -1,
        groups: null,
        leader: leader,
        schedules: [],
        statistics: {
            skills: -1,
            fairPlay: -1,
            numberOfMatches: 0,
            numberOfWins: 0,
            numberOfLosses: 0,
            winStreak: 0,
            loseStreak: 0,
            matchRating: -1
        }
    }
    // data of first member to write to team member subcollection
    const teamMember = {
        player: leader,
        title: 'Leader',
        invitedTimestamp: null,
        joinedTimestamp: currentTimestamp,
        status: 'joined',
        leftTimestamp: null,
        kickedTimestamp: null,
        showOrder: 0
    }
    
    const newTeamRef = db.collection('teams').doc();
    const batch = db.batch();
    batch.create(newTeamRef, teamData);
    const newMemberRef = newTeamRef.collection('teamMembers').doc(leader);
    batch.create(newMemberRef, teamMember);
    const teamId = newTeamRef.id;
    // data of joined team to write to joined team subcollection
    const newJoinedTeamRef = db.collection('users').doc(leader).collection('joinedTeams').doc(newTeamRef.id);
    const joinedTeam = {
        team: teamId,
        joinedTimestamp: currentTimestamp,
        status: 'joined',
        leftTimestamp: null,
        kickedTimestamp: null
    }
    batch.create(newJoinedTeamRef, joinedTeam);
    await batch.commit().catch((error) => {
        return {
            result: 0,
            errorCode: error.code,
            errorMessage: error.message
        }
    });

    return {
        result: 1,
        message: 'Create team successfully'
    }
})