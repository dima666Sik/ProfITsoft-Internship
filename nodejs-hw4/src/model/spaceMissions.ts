import mongoose, { Document, Schema } from 'mongoose';

// Define the SpaceMission interface
export interface ISpaceMission extends Document {
    _id: string;
    name: string;
    dateStartMission: Date;
    dateEndMission: Date;
    planetId: string;

    /** will be added by mongo */
    createdAt: Date;
    updatedAt: Date;
}

// Define the SpaceMission schema
const spaceMissionSchema = new Schema({
    name: {
        required: true,
        type: String,
    },
    dateStartMission: {
        required: true,
        type: Date,
    },
    dateEndMission: {
        required: true,
        type: Date,
    },
    planetId: {
        required: true,
        type: String,
    },
}, {
    /**
     * The timestamps option tells mongoose to assign createdAt and updatedAt
     * fields to your schema. The type assigned is Date.
     */
    timestamps: true,
    timezone: 'UTC',
});

// Create the model
const SpaceMission = mongoose.model<ISpaceMission>('SpaceMission', spaceMissionSchema);

export default SpaceMission;

