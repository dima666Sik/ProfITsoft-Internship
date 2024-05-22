import {useIntl} from "react-intl";
import Typography from "../../../components/Typography";
import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {deletePlanet, fetchAllPlanets} from "../actions/planet";
import Card from "../../../components/Card";
import CardContent from "../../../components/CardContent";
import IconButton from "../../../components/IconButton";
import DeleteIcon from "../../../components/icons/Delete";
import {createUseStyles} from "react-jss";
import Dialog from "../../../components/Dialog";
import Snackbar from "../../../components/Snackbar";
import DialogContent from "../../../components/Dialog/content";
import Pagination from "../../../components/Pagination";
import Button from "../../../components/Button";
import Select from "../../../components/Select";
import MenuItem from "../../../components/MenuItem";
import {InputLabel} from "@mui/material";
import FormControl from "../../../components/FormControl";

const getPlanetListStyles = createUseStyles(() => ({
    ulPlanetList: {
        listStyleType: 'none',
        padding: 0
    },
    item: {
        position: 'relative',
        marginBottom: '16px'
    },
    deleteIconContainer: {
        position: 'absolute',
        top: 8,
        right: 8,
    },
    menuBtn: {
        display: "flex",
        alignContent: "center",
        justifyContent: "space-around"
    }
}));
const PlanetList = () => {
    const {formatMessage} = useIntl();
    const dispatch = useDispatch();
    const planets = useSelector((state) => state.planets.planets);
    const isLoading = useSelector((state) => state.planets.isLoading);
    const error = useSelector((state) => state.planets.error);

    const [selectedPlanet, setSelectedPlanet] = useState(null);
    const [isDialogOpen, setIsDialogOpen] = useState(false);
    const [namePrimaryBtn, setNamePrimaryBtn] = useState("Cancel");
    const [typeOfDialog, setTypeOfDialog] = useState("basic");
    const [titleDialog, setTitleDialog] = useState("Info");
    const [dialogMessage, setDialogMessage] = useState("");
    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState("");
    const [snackbarSeverity, setSnackbarSeverity] = useState("success");
    const [hoveredPlanetId, setHoveredPlanetId] = useState(null);
    const [currentPage, setCurrentPage] = useState(1);

    useEffect(() => {
        dispatch(fetchAllPlanets())
            .then(() => {
                setSnackbarMessage(formatMessage({id: 'fetchAllPlanetSuccess'}));
                setSnackbarSeverity("success");
                setSnackbarOpen(true);
            })
            .catch((e) => {
                setSnackbarMessage(formatMessage({id: 'fetchAllPlanetError'}));
                setSnackbarSeverity("error");
                setSnackbarOpen(true);
                setIsDialogOpen(true);
                setDialogMessage(e.message);
                setTypeOfDialog("error");
                setNamePrimaryBtn("Close");
                setTitleDialog("Fetch error info dialog")
            });
    }, [dispatch]);

    const handleDeletePlanetByClick = (planet) => {
        setSelectedPlanet(planet);
        setTypeOfDialog("basic");
        setNamePrimaryBtn("Cancel");
        setTitleDialog("Delete dialog");
        setDialogMessage("Are you sure you want to delete this planet?")
        setIsDialogOpen(true);
    };

    const handleConfirmDelete = () => {
        if (selectedPlanet) {
            dispatch(deletePlanet(selectedPlanet.id))
                .then(() => {
                    setSnackbarMessage(formatMessage({id: 'deleteSuccess'}));
                    setSnackbarSeverity("success");
                    setSnackbarOpen(true);
                    setIsDialogOpen(false);
                })
                .catch((e) => {
                    setSnackbarMessage(formatMessage({id: 'deleteError'}));
                    setSnackbarSeverity("error");
                    setSnackbarOpen(true);
                    setIsDialogOpen(true);
                    setDialogMessage(e.message);
                    setTypeOfDialog("error");
                    setNamePrimaryBtn("Close");
                    setTitleDialog("Delete error info dialog")
                });
        }
    };

    const handleDialogClose = () => {
        setIsDialogOpen(false);
        setDialogMessage("");
    };

    const handlePageChange = (event, value) => {
        setCurrentPage(value);
    };

    const planetListStyles = getPlanetListStyles();
    const itemsPerPage = 6;

    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentPlanets = planets.slice(indexOfFirstItem, indexOfLastItem);


    return (
        <div>
            <div className={planetListStyles.menuBtn}>
                <FormControl>
                    <InputLabel id="demo-simple-select-label">Filter</InputLabel>
                    <Select
                        labelId="demo-simple-select-label"
                        id="demo-simple-select"
                        // value={age}
                        label="Filter"
                        // onChange={handleChange}
                    >
                        <MenuItem>Default</MenuItem>
                        <MenuItem>DESC</MenuItem>
                        <MenuItem>ASC</MenuItem>
                    </Select>
                </FormControl>
                <Button>{formatMessage({id: 'btnCreatePlanet'})}</Button>
            </div>
            <Typography variant="title" color="green">
                {formatMessage({id: 'title'})}
            </Typography>
            {isLoading && <Card
                variant="warning"><Typography>{formatMessage({id: 'loading'})}</Typography></Card>}
            <ul className={planetListStyles.ulPlanetList}>
                {currentPlanets && currentPlanets.length > 0 ? currentPlanets.map((planet) => (
                    <li key={planet.id} className={planetListStyles.item}
                        onMouseMove={() => setHoveredPlanetId(planet.id)}
                        onMouseOut={() => setHoveredPlanetId(null)}
                    >
                        <Card variant="info">
                            <CardContent>
                                <Typography>{formatMessage({id: 'planetName'})}: {planet.name}</Typography>
                                <Typography>{formatMessage({id: 'hasRings'})}: {planet.hasRings === true ? "Yes" : "No"}</Typography>
                                <Typography>{formatMessage({id: 'hasMoons'})}: {planet.hasMoons === true ? "Yes" : "No"}</Typography>
                                <Typography>{formatMessage({id: 'atmosphericComposition'})}: {planet.atmosphericComposition}</Typography>
                                <Typography>{formatMessage({id: 'planetarySystemName'})}: {planet.planetarySystemResponseDto.name}</Typography>
                            </CardContent>
                        </Card>
                        {hoveredPlanetId === planet.id && (
                            <div className={planetListStyles.deleteIconContainer}>
                                <IconButton
                                    onClick={() => handleDeletePlanetByClick(planet)}
                                >
                                    <DeleteIcon color="red"/>
                                </IconButton>
                            </div>
                        )}
                    </li>

                )) : <Card
                    variant="warning"><Typography>{formatMessage({id: 'messageEmptyPlanetList'})}</Typography></Card>}
            </ul>
            <Pagination
                count={Math.ceil(planets.length / itemsPerPage)}
                page={currentPage}
                onChange={handlePageChange}
                variant="outlined"
            />
            <Dialog
                open={isDialogOpen}
            >
                <DialogContent onConfirm={handleConfirmDelete}
                               onClose={handleDialogClose}
                               type={typeOfDialog}
                               primaryNameBtn={namePrimaryBtn}
                               title={titleDialog}
                               secondaryNameBtn="Confirm"
                               message={dialogMessage}
                />
            </Dialog>
            <Snackbar
                open={snackbarOpen}
                severity={snackbarSeverity}
                autoHideDuration={6000}
                message={snackbarMessage}
                onClose={() => setSnackbarOpen(false)}
            />
        </div>
    );
};

export default PlanetList;