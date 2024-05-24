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
import {addPlanet, fetchPlanet, updatePlanet} from "../actions/planet";
import {useParams, useNavigate} from "react-router-dom";
import {useIntl} from "react-intl";
import {createUseStyles} from "react-jss";
import * as pages from "../../../constants/pages";

const getPlanetStyles = createUseStyles(() => ({
    item: {
        position: 'relative',
    },
    iconContainer: {
        position: 'absolute',
        top: 8,
        right: 8,
    },
    buttonContainer: {
        marginTop: 20,
        display: 'flex',
        justifyContent: 'space-between',
    }
}));
const DetailPlanet = () => {
    const {formatMessage} = useIntl();
    const navigate = useNavigate();
    const {planetId} = useParams();
    const dispatch = useDispatch();
    const planet = useSelector(state => state.planet.planet);

    const initialPlanetData = {
        name: "",
        atmosphericComposition: "",
        diameterDto: {value: "", unit: "KILOMETER"},
        massDto: {value: "", unit: "KILOGRAM"},
        hasMoons: false,
        hasRings: false,
        planetarySystemResponseDto: {name: ""}
    };

    const [isEditing, setIsEditing] = useState(!planetId);
    const [planetData, setPlanetData] = useState(planetId ? planet : initialPlanetData);
    const [showSnackbar, setShowSnackbar] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState('');
    const [errors, setErrors] = useState({});
    const [snackbarSeverity, setSnackbarSeverity] = useState("success");
    const planetStyles = getPlanetStyles();

    useEffect(() => {
        if (planetId) {
            dispatch(fetchPlanet(planetId));
        }
    }, [dispatch, planetId]);

    const handleEditToggle = () => {
        setIsEditing(!isEditing);
        if (!isEditing) {
            setPlanetData(planet);
        }
    };

    const handleChange = (e) => {
        const {name, value} = e.target;
        // Convert the value based on its expected type
        let newValue;
        if (name === "massDto" || name === "diameterDto") {
            newValue = parseFloat(value);
        } else {
            newValue = value; // Default to string
        }

        if ((name === "hasMoons" || name === "hasRings") && (value === "true" || value === "false")) {
            newValue = value === "true"; // Convert to boolean
        }

        // Handle nested properties
        const keys = name.split(".");
        let updatedData = {...planetData};
        let tempData = updatedData;

        keys.forEach((key, index) => {
            if (index === keys.length - 1) {
                tempData[key] = newValue;
            } else {
                tempData[key] = {...tempData[key]};
                tempData = tempData[key];
            }
        });
        console.log("u1", updatedData)
        setPlanetData(updatedData);
        console.log("u2", updatedData)
    };


    const validate = () => {
        let tempErrors = {};

        // Name validation
        if (!planetData.name) tempErrors.name = "Name is required";

        // Atmospheric Composition validation
        if (!planetData.atmosphericComposition) tempErrors.atmosphericComposition = "Atmospheric Composition is required";

        // Diameter validation
        if (!planetData.diameterDto || !planetData.diameterDto.value) {
            tempErrors.diameter = "Diameter is required";
        } else if (isNaN(planetData.diameterDto.value)) {
            tempErrors.diameter = "Diameter must be a number";
        }

        // Mass validation
        if (!planetData.massDto || !planetData.massDto.value) {
            tempErrors.mass = "Mass is required";
        } else if (isNaN(planetData.massDto.value)) {
            tempErrors.mass = "Mass must be a number";
        }

        // Has Moons validation
        if (planetData.hasMoons === '') tempErrors.hasMoons = "Has Moons is required";

        // Has Rings validation
        if (planetData.hasRings === '') tempErrors.hasRings = "Has Rings is required";

        // Planetary System Name validation
        if (!planetData.planetarySystemResponseDto || !planetData.planetarySystemResponseDto.name) {
            tempErrors.planetarySystemName = "Planetary System Name is required";
        }

        setErrors(tempErrors);

        if (Object.keys(tempErrors).length > 0) {
            const errorMessages = Object.values(tempErrors).join('. ');
            setSnackbarMessage(errorMessages);
            setSnackbarSeverity("error");
            setShowSnackbar(true);
        }

        return Object.keys(tempErrors).length === 0;
    };


    const handleSave = () => {
        if (validate()) {
            if (planetId) {
                dispatch(updatePlanet(planetData.id, planetData))
                    .then(() => {
                        setIsEditing(false);
                        setShowSnackbar(true);
                        setSnackbarSeverity("success");
                        setSnackbarMessage('Planet updated successfully');
                    })
                    .catch(() => {
                        setShowSnackbar(true);
                        setSnackbarMessage('Error updating planet');
                        setSnackbarSeverity("error");
                    });
            } else {
                dispatch(addPlanet(planetData))
                    .then(() => {
                        navigate(`../${pages.listEntitiesPage}`); // Redirect to the list after creation
                        setShowSnackbar(true);
                        setSnackbarSeverity("success");
                        setSnackbarMessage('Planet created successfully');
                    })
                    .catch(() => {
                        setShowSnackbar(true);
                        setSnackbarMessage('Error creating planet');
                        setSnackbarSeverity("error");
                    });
            }
        }
    };

    const handleCancel = () => {
        if (planetId) {
            setIsEditing(false);
            setPlanetData(planet);
        } else navigate(`../${pages.listEntitiesPage}`);
    };

    console.log(planet)

    return (
        <Card>
            <CardContent>
                {(planet || !planetId) && (
                    <div className={planetStyles.item}>
                        <div className={planetStyles.iconContainer}>
                            <IconButton onClick={handleEditToggle}>
                                <EditIcon/>
                            </IconButton>
                        </div>
                        <Typography variant="h5" component="div">
                            {isEditing ? (
                                <>
                                    <TextField
                                        label={formatMessage({id: 'planetName'})}
                                        name="name"
                                        value={planetData.name}
                                        onChange={handleChange}
                                        helperText={errors.name}
                                        isError={errors.name}
                                        fullWidth
                                    />
                                    <TextField
                                        label={formatMessage({id: 'atmosphericComposition'})}
                                        name="atmosphericComposition"
                                        value={planetData.atmosphericComposition}
                                        onChange={handleChange}
                                        helperText={errors.atmosphericComposition}
                                        isError={errors.atmosphericComposition}
                                        fullWidth
                                    />
                                    <TextField
                                        label={formatMessage({id: 'diameter'})}
                                        name="diameterDto.value"
                                        value={planetData.diameterDto.value}
                                        onChange={handleChange}
                                        helperText={errors.diameter}
                                        isError={errors.diameter}
                                        fullWidth
                                    />
                                    <TextField
                                        label={formatMessage({id: 'mass'})}
                                        name="massDto.value"
                                        value={planetData.massDto.value}
                                        onChange={handleChange}
                                        helperText={errors.mass}
                                        isError={errors.mass}
                                        fullWidth
                                    />
                                    {/* in the future use radio to handle boolean type */}
                                    <TextField
                                        label={formatMessage({id: 'hasMoons'})}
                                        name="hasMoons"
                                        value={planetData.hasMoons}
                                        onChange={handleChange}
                                        isError={errors.hasMoons}
                                        helperText={errors.hasMoons}
                                        fullWidth
                                    />
                                    <TextField
                                        label={formatMessage({id: 'hasRings'})}
                                        name="hasRings"
                                        value={planetData.hasRings}
                                        isError={errors.hasRings}
                                        onChange={handleChange}
                                        helperText={errors.hasRings}
                                        fullWidth
                                    />

                                    <TextField
                                        label={formatMessage({id: 'planetarySystemName'})}
                                        name="planetarySystemResponseDto.name"
                                        value={planetData.planetarySystemResponseDto.name}
                                        onChange={handleChange}
                                        isError={errors.planetarySystemName}
                                        helperText={errors.planetarySystemName}
                                        fullWidth
                                    />
                                </>
                            ) : (
                                <div>
                                    <h1>{planet.name}</h1>
                                    <ul>
                                        <li>
                                            <strong>{formatMessage({id: 'atmosphericComposition'})}:</strong> {planet.atmosphericComposition}
                                        </li>
                                        <li>
                                            <strong>{formatMessage({id: 'diameter'})}:</strong> {planet?.diameterDto?.value || formatMessage({id: 'unknown'})} {planet?.diameterDto?.unit || formatMessage({id: 'unknown'})}
                                        </li>
                                        <li>
                                            <strong>{formatMessage({id: 'mass'})}:</strong> {planet?.massDto?.value || formatMessage({id: 'unknown'})} {planet?.massDto?.unit || formatMessage({id: 'unknown'})}
                                        </li>
                                        <li>
                                            <strong>{formatMessage({id: 'hasMoons'})}:</strong> {planet.hasMoons ? "Yes" : "No"}
                                        </li>
                                        <li>
                                            <strong>{formatMessage({id: 'hasRings'})}:</strong> {planet.hasRings ? "Yes" : "No"}
                                        </li>
                                        <li>
                                            <strong>{formatMessage({id: 'planetarySystemName'})}:</strong> {planet?.planetarySystemResponseDto?.name || formatMessage({id: 'unknown'})}
                                        </li>
                                    </ul>
                                </div>
                            )}
                        </Typography>

                        {isEditing && (
                            <div className={planetStyles.buttonContainer}>
                                <Button onClick={handleSave}>{planetId ? 'Save' : 'Create'}</Button>
                                <Button onClick={handleCancel}>Cancel</Button>
                            </div>
                        )}
                    </div>
                )}
            </CardContent>
            <Snackbar
                open={showSnackbar}
                severity={snackbarSeverity}
                autoHideDuration={6000}
                message={snackbarMessage}
                onClose={() => setShowSnackbar(false)}
            />
        </Card>
    );
};

export default DetailPlanet;