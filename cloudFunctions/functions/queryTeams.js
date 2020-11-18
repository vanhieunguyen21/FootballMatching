const functions = require('firebase-functions');
const geohash = require('ngeohash');

const admin = require('firebase-admin');
const db = admin.firestore();

exports.querySuggestedTeams = functions.https.onCall(async (query) => {
    // team groups to query - an array of integers, sorted by the relevancy
    let groups = query.groups;
    const currentGroupIndex = query.currentGroupIndex;
    // remove previously queried groups
    groups.splice(currentGroupIndex);
    // last read document reference
    const lastReadTeamId = query.lastReadTeamId;
    let lastReadTeamRef;
    if (lastReadTeamId !== null) lastReadTeamRef = db.collection('teams').doc(lastReadTeamId);
    // limit number of document read
    const limit = query.limit;
    // number of document read and number of document needs to read left
    let count = 0;
    let left = limit;
    let returnResult = [];
    let i, j;

    /* eslint-disable no-await-in-loop */
    for (i = 0; i < groups.length; i++) {
        const group = groups[i];
        let teams;
        if (lastReadTeamId !== null)
            teams = await db.collection('teams').where('mainGroup', '==', group).limit(limit).startAfter(lastReadTeamRef).get();
        else teams = await db.collection('teams').where('mainGroup', '==', group).limit(limit).get();
        for (j = 0; j < teams.docs.length; j++) {
            const teamRecord = teams.docs[j];
            let result = teamRecord.data();
            result.id = teamRecord.id;
            returnResult.push(result);
            count++;
            left--;
            if (count === limit) break;
        }
        if (count === limit) break;
    }
    /* eslint-enable no-await-in-loop */

    // groups.forEach(async (group) => {
    //     const teams = await db.collection('teams').where('mainGroup', '==', group).limit(limit).startAfter(lastReadDocumentRef).get();
    //     teams.forEach((teamRecord) => {
    //         // team result
    //         let result = teamRecord.data();
    //         result.id = teamRecord.id;
    //         returnResult.push(result);
    //         count++;
    //         left--;
    //         if (count == limit) break;
    //     });
    //     if (count == limit) break;
    // });
    return {result: returnResult};
})

exports.queryNearbyTeams = functions.https.onCall(async (query) => {
    // current position
    const longitude = query.longitude;
    const latitude = query.latitude;
    const geoHash = query.geoHash;
    // distance in meter
    const distance = query.distance;
    // latitude and longitude change per meter
    const delta = 0.0000089;
    const coef = delta * distance;
    // calculate boundaries
    const lowerLat = latitude - coef;
    const upperLat = latitude + coef;
    const lowerLong = longitude - coef / Math.cos(latitude * 0.018);
    const upperLong = longitude + coef / Math.cos(latitude * 0.018);
    // get geoHash of lower and upper boundaries
    const lowerGeoHash = geohash.encode(lowerLat, lowerLong);
    const upperGeoHash = geohash.encode(upperLat, upperLong);
    // query teams inside boundaries
    const teams = await db.collection('teams').where('location.geoHash', '>=', lowerGeoHash).where('location.geoHash', '<=', upperGeoHash).get();
    let returnResult = [];
    teams.forEach((teamRecord) => {
        // team result
        let result = teamRecord.data();
        result.id = teamRecord.id;
        returnResult.push(result);
    });
    return {result: returnResult};
})

exports.queryAllTeams = functions.https.onCall(async(myTeamId) => {
    const teams = await db.collection('teams').get();
    let returnResult = [];
    teams.forEach((teamRecord) => {
        // team result
        if (myTeamId !== teamRecord.id){
            let result = teamRecord.data();
            result.id = teamRecord.id;
            returnResult.push(result);
        }
    });
    return {
        result: returnResult
    };
})

exports.queryNotJoinedTeams = functions.https.onCall(async (uid) => {
    let i;
    const teams = await db.collection('teams').get();
    const returnResult = [];
    /* eslint-disable no-await-in-loop */
    for (i = 0 ; i < teams.docs.length ; i++) {
        let joined = true;
        const teamRecord = teams.docs[i];
        const teamMemberRecord = await db.collection('teams').doc(teamRecord.id).collection('teamMembers').doc(uid).get();
        if (teamMemberRecord.exists){
            joined = true;
            const status = teamMemberRecord.data().status;
            if (status !== 'joined') joined = false;
        } else {
            joined = false;
        }
        if (!joined){
            const result = teamRecord.data();
            result.id = teamRecord.id;
            returnResult.push(result);
        }
    }
    /* eslint-enable no-await-in-loop */
    return {
        result: returnResult
    }
})