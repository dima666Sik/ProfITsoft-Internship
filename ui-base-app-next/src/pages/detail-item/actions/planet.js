import {
    FETCH_PLANET_ERROR,
    FETCH_PLANET_LOADING,
    FETCH_PLANET_SUCCESS,
    UPDATE_PLANET_ERROR,
    UPDATE_PLANET_SUCCESS,
    UPDATE_PLANET_LOADING,
    ADD_PLANET_LOADING,
    ADD_PLANET_SUCCESS,
    ADD_PLANET_ERROR,
} from "app/constants/actionTypes";
import axios from "axios";
import mockPlanets from "../../list-entities/actions/mock-planets.json";

const planetInstance = axios.create({
    baseURL: "http://localhost:8080/api",
});
const addPlanetLoading = () => {
    return {
        type: ADD_PLANET_LOADING,
    };
};
const addPlanetSuccess = (payload) => {
    return {
        type: ADD_PLANET_SUCCESS,
        payload,
    };
};
const addPlanetError = (payload) => {
    return {
        type: ADD_PLANET_ERROR,
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


const updatePlanetLoading = () => {
    return {
        type: UPDATE_PLANET_LOADING,
    };
};
const updatePlanetSuccess = (payload) => {
    return {
        type: UPDATE_PLANET_SUCCESS,
        payload,
    };
};
const updatePlanetError = (payload) => {
    return {
        type: UPDATE_PLANET_ERROR,
        payload,
    };
};

export const fetchPlanet = (id) => async (dispatch) => {
    try {
        dispatch(fetchPlanetLoading());
        // const response = await planetInstance.get(`/planet/${id}`);
        // console.log(response.data)
        // dispatch(fetchPlanetSuccess(response.data));

        // Mock
        const response = mockPlanets.find((item) => item.id === Number(id));
        console.log(id, response, mockPlanets)
        dispatch(fetchPlanetSuccess(response));
    } catch (e) {
        dispatch(fetchPlanetError(e.message));
    }
};

export const addPlanet = (body) => async (dispatch) => {
    try {
        dispatch(addPlanetLoading());

        // const response = await planetInstance.post("/planet", body);
        // if (response.status === 201) {
        //     dispatch(addPlanetsSuccess(body));
        // }

        // Mock
        body.id = mockPlanets.length + 1;
        dispatch(addPlanetSuccess(body));
    } catch (e) {
        dispatch(addPlanetError(e.message));
    }
};

export const updatePlanet = (id, body) => async (dispatch) => {
    try {
        dispatch(updatePlanetLoading());
        // const response = await planetInstance.put(`/planet/${id}`, body);
        // if (response.status === 200) {
        //     dispatch(updatePlanetsSuccess(body));
        // }
        // Mock
        dispatch(updatePlanetSuccess(body));
    } catch (e) {
        dispatch(updatePlanetError(e.message));
        throw e;
    }
};
