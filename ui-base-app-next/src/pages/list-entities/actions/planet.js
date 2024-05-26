import {
    FETCH_PLANETS_LOADING,
    FETCH_PLANETS_SUCCESS,
    FETCH_PLANETS_ERROR,
    DELETE_PLANET_ERROR,
    DELETE_PLANET_SUCCESS,
    DELETE_PLANET_LOADING,
    FILTER_PLANETS_ERROR,
    FILTER_PLANETS_LOADING,
    FILTER_PLANETS_SUCCESS,
} from "app/constants/actionTypes";
import axios from "axios";
import mockPlanets from "./mock-planets.json";

const planetInstance = axios.create({
    baseURL: "http://localhost:8080/api",
});
//fetch planets
const fetchPlanetsLoading = () => {
    return {
        type: FETCH_PLANETS_LOADING,
    };
};
const fetchPlanetsSuccess = (payload) => {
    return {
        type: FETCH_PLANETS_SUCCESS,
        payload,
    };
};
const fetchPlanetsError = (payload) => {
    return {
        type: FETCH_PLANETS_ERROR,
        payload,
    };
};

const deletePlanetsLoading = () => {
    return {
        type: DELETE_PLANET_LOADING,
    };
};
const deletePlanetsSuccess = (payload) => {
    return {
        type: DELETE_PLANET_SUCCESS,
        payload,
    };
};
const deletePlanetsError = (payload) => {
    return {
        type: DELETE_PLANET_ERROR,
        payload,
    };
};
const filterPlanetsLoading = () => {
    return {
        type: FILTER_PLANETS_LOADING,
    };
};

const filterPlanetsSuccess = (payload) => {
    return {
        type: FILTER_PLANETS_SUCCESS,
        payload,
    };
};

const filterPlanetsError = (payload) => {
    return {
        type: FILTER_PLANETS_ERROR,
        payload,
    };
};

export const deletePlanet = (id) => async (dispatch) => {
    try {
        dispatch(deletePlanetsLoading());
        // await planetInstance.delete(`/planet/${id}`);

        dispatch(deletePlanetsSuccess(id));
    } catch (e) {
        console.log(e)
        dispatch(deletePlanetsError(e.message));
        throw e;
    }
};

export const filterPlanets = (filterValue) => async (dispatch) => {
    try {
        dispatch(filterPlanetsLoading());
        // Mock filter
        // const response = await planetInstance.get(`/planet/filter/${filterValue}`);
        // console.log(response)
        dispatch(filterPlanetsSuccess(filterValue));
    } catch (e) {
        console.log(e)
        dispatch(filterPlanetsError(e.message));
        throw e;
    }
}

export const fetchAllPlanets = () => async (dispatch) => {
    try {
        dispatch(fetchPlanetsLoading());
        // const response = await planetInstance.get("/planet/all");

        // Mock list planets
        dispatch(fetchPlanetsSuccess());

        // dispatch(fetchPlanetsSuccess(response.data.list));
    } catch (e) {
        dispatch(fetchPlanetsError(e.message));
    }
};


