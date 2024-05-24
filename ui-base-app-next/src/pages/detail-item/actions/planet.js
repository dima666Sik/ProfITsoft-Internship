import {
    FETCH_PLANETS_LOADING,
    FETCH_PLANETS_SUCCESS,
    FETCH_PLANETS_ERROR,
    CLEAR_STATE,
    ADD_PLANETS_LOADING,
    ADD_PLANETS_ERROR,
    ADD_PLANETS_SUCCESS,
    FETCH_PLANET_ERROR,
    FETCH_PLANET_LOADING,
    FETCH_PLANET_SUCCESS,
    UPDATE_PLANET_ERROR,
    UPDATE_PLANET_SUCCESS,
    UPDATE_PLANET_LOADING,
} from "app/constants/actionTypes";
import axios from "axios";

const planetInstance = axios.create({
    baseURL: "http://localhost:8080/api",
});
const addPlanetsLoading = () => {
    return {
        type: ADD_PLANETS_LOADING,
    };
};
const addPlanetsSuccess = (payload) => {
    return {
        type: ADD_PLANETS_SUCCESS,
        payload,
    };
};
const addPlanetsError = (payload) => {
    return {
        type: ADD_PLANETS_ERROR,
        payload,
    };
};
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
//fetch planet
const fetchPlanetLoading = () => {
    return {
        type: FETCH_PLANET_LOADING,
    };
};
const fetchPlanetSuccess = (payload) => {
    return {
        type: FETCH_PLANET_SUCCESS,
        payload,
    };
};
const fetchPlanetError = (payload) => {
    return {
        type: FETCH_PLANET_ERROR,
        payload,
    };
};


const updatePlanetsLoading = () => {
    return {
        type: UPDATE_PLANET_LOADING,
    };
};
const updatePlanetsSuccess = (payload) => {
    return {
        type: UPDATE_PLANET_SUCCESS,
        payload,
    };
};
const updatePlanetsError = (payload) => {
    return {
        type: UPDATE_PLANET_ERROR,
        payload,
    };
};

export const fetchPlanet = (id) => async (dispatch) => {
    try {
        dispatch(fetchPlanetLoading());
        const response = await planetInstance.get(`/planet/${id}`);
        console.log(response.data)
        dispatch(fetchPlanetSuccess(response.data));
    } catch (e) {
        dispatch(fetchPlanetError(e.message));
    }
};

export const addPlanet = (body) => async (dispatch) => {
    try {
        dispatch(addPlanetsLoading());
        const response = await planetInstance.post("/planet", body);
        if (response.status === 201) {
            dispatch(addPlanetsSuccess(body));
        }
    } catch (e) {
        dispatch(addPlanetsError(e.message));
    }
};

export const updatePlanet = (id, body) => async (dispatch) => {
    try {
        dispatch(updatePlanetsLoading());
        const response = await planetInstance.put(`/planet/${id}`, body);
        if (response.status === 200) {
            dispatch(updatePlanetsSuccess(body));
        }
    } catch (e) {
        dispatch(updatePlanetsError(e.message));
        throw e;
    }
};

export const fetchAllPlanets = () => async (dispatch) => {
    try {
        dispatch(fetchPlanetsLoading());
        const response = await planetInstance.get("/planet/all");
        console.log(response.data)
        dispatch(fetchPlanetsSuccess(response.data.list));
    } catch (e) {
        dispatch(fetchPlanetsError(e.message));
    }
};

export const clearState = () => {
    return {
        type: CLEAR_STATE,
    };
}