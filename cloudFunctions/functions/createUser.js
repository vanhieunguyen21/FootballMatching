const functions = require('firebase-functions');

const admin = require('firebase-admin');
const db = admin.firestore();

exports.createUser = functions.https.onCall(async (data) => {
    try {
        const userRecord = await admin.auth().createUser({
            email: data.email,
            password: data.password,
            displayName: data.fullName
        });
        const currentTimestamp = admin.firestore.FieldValue.serverTimestamp();
        const userInfo = {
            email: data.email,
            fullName: data.fullName,
            address: null,
            birthday: null,
            phone: null,
            joinedTimestamp: currentTimestamp,
            accountActive: true,
            height: 0,
            weight: 0,
            introduction: null,
            preferredPositions: {
                goalkeeper: false,
                defender: false,
                attackingMidfielder: false,
                centreMidfielder: false,
                defensiveMidfielder: false,
                forwardAttacker: false,
                centreAttacker: false
            },
            location: null
        };
        await db.collection('users').doc(userRecord.uid).set(userInfo).catch((error) => {
            return {
                result: 0,
                message: 'Add user into database failed',
                errorCode: error.code,
                errorMessage: error.message
            }
        });
        return {
            result: 1,
            message: 'User created'
        }

    }
    catch (error) {
        return {
            result: 0,
            message: 'Create user failed',
            errorCode: error.code,
            errorMessage: error.message
        }
    }
})