import Card from "../../../components/Card";
import CardContent from "../../../components/CardContent";
import Typography from "../../../components/Typography";
import TextField from "../../../components/TextField";
import IconButton from "../../../components/IconButton";
import Button from "../../../components/Button";
import Snackbar from "../../../components/Snackbar";
import {useDispatch, useSelector} from "react-redux";
import {useEffect, useState} from "react";
import EditIcon from "../../../components/icons/Edit";
import {fetchPlanet} from "../actions/planet";
import {useParams} from "react-router-dom";
import {useIntl} from "react-intl";

const DetailPlanet = () => {
    const {formatMessage} = useIntl();

    const {planetId} = useParams();
    const dispatch = useDispatch();
    const planet = useSelector(state => state.planet.planet);
    const [isEditing, setIsEditing] = useState(false);
    const [planetData, setPlanetData] = useState(planet);
    const [showSnackbar, setShowSnackbar] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState('');
    const [errors, setErrors] = useState({});

    useEffect(() => {
        dispatch(fetchPlanet(planetId));
    }, [dispatch, planetId]);

    console.log(useSelector(state => state))

    useEffect(() => {
        console.log('Planet data:', planet);
        if (planet) {
            setPlanetData(planet);
        }
    }, [planet]);

    const handleEditToggle = () => {
        // setIsEditing(!isEditing);
        // if (!isEditing) {
        //     setPlanetData(planet);
        // }
    };

    const handleChange = (e) => {
        // const { name, value } = e.target;
        // setPlanetData({
        //     ...planetData,
        //     [name]: value,
        // });
    };

    const validate = () => {
        // let tempErrors = {};
        // if (!planetData.name) tempErrors.name = "Name is required";
        // // Add other field validations here
        // setErrors(tempErrors);
        // return Object.keys(tempErrors).length === 0;
    };

    const handleSave = () => {
        // if (validate()) {
        //     dispatch(updatePlanet(planetData.id, planetData))
        //         .then(() => {
        //             setIsEditing(false);
        //             setShowSnackbar(true);
        //             setSnackbarMessage('Planet updated successfully');
        //         })
        //         .catch(() => {
        //             setShowSnackbar(true);
        //             setSnackbarMessage('Error updating planet');
        //         });
        // }
    };

    const handleCancel = () => {
        // setIsEditing(false);
        // setPlanetData(planet);
    };
    console.log(planet)
    return (
        <Card>
            <CardContent>
                {planet && (
                    <div>
                        <Typography variant="h5" component="div">
                            {isEditing ? (
                                <TextField
                                    label="Name"
                                    name="name"
                                    value={planetData.name}
                                    onChange={handleChange}
                                    error={errors.name}
                                    helperText={errors.name}
                                    fullWidth
                                />
                            ) : (
                                <div>
                                    <h1>{planet.name}</h1>
                                    <ul>
                                        <li><strong>{formatMessage({id: 'atmosphericComposition'})}:</strong> {planet.atmosphericComposition}</li>
                                        <li><strong>{formatMessage({id: 'diameter'})}:</strong> {planet.diameterDto.value} {planet.diameterDto.unit}</li>
                                        <li><strong>{formatMessage({id: 'mass'})}:</strong> {planet.massDto.value} {planet.massDto.unit}</li>
                                        <li><strong>{formatMessage({id: 'hasMoons'})}:</strong> {planet.hasMoons ? "Yes" : "No"}</li>
                                        <li><strong>{formatMessage({id: 'hasRings'})}:</strong> {planet.hasRings ? "Yes" : "No"}</li>
                                        <li><strong>{formatMessage({id: 'planetarySystemName'})}:</strong> {planet.planetarySystemResponseDto.name}</li>
                                    </ul>
                                </div>
                            )}
                        </Typography>
                        <IconButton onClick={handleEditToggle}>
                            <EditIcon/>
                        </IconButton>
                        {isEditing && (
                            <div>
                                <Button onClick={handleSave}>Save</Button>
                                <Button onClick={handleCancel}>Cancel</Button>
                            </div>
                        )}
                    </div>
                )}
            </CardContent>
            <Snackbar
                open={showSnackbar}
                autoHideDuration={6000}
                message={snackbarMessage}
                onClose={() => setShowSnackbar(false)}
            />
        </Card>
    );
};

export default DetailPlanet;