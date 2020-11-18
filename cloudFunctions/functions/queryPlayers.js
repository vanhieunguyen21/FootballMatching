const functions = require('firebase-functions');

const admin = require('firebase-admin');
const db = admin.firestore();

exports.queryAllPlayers = functions.https.onCall(async (data) => {
    const myId = data.myId;
    const myTeamId = data.myTeamId;
    let i;
    const players = await db.collection('users').get();
    const returnResult = [];
    /* eslint-disable no-await-in-loop */
    for (i = 0 ; i < players.docs.length; i++){
        let joined = true;
        const playerRecord = players.docs[i];
        if (myId !== playerRecord.id){
            const joinedMyTeamRecord = await db.collection('users').doc(playerRecord.id).collection('joinedTeams').doc(myTeamId).get();
            if (joinedMyTeamRecord.exists){
                joined = true;
                const status = joinedMyTeamRecord.data().status;
                if (status !== 'joined') joined = false;
            } else {
                joined = false;
            }
        }
        if (!joined) {
            let result = playerRecord.data();
            result.id = playerRecord.id;
            returnResult.push(result);
        }
    }
    /* eslint-enable no-await-in-loop */
    return {
        result: returnResult
    }
})