import {
    FETCH_PLANETS_ERROR,
    FETCH_PLANETS_SUCCESS,
    FETCH_PLANETS_LOADING,
    DELETE_PLANET_LOADING,
    DELETE_PLANET_SUCCESS,
    DELETE_PLANET_ERROR,
    ADD_PLANETS_ERROR,
    ADD_PLANETS_LOADING,
    ADD_PLANETS_SUCCESS,
    FETCH_PLANET_ERROR,
    FETCH_PLANET_SUCCESS,
    FETCH_PLANET_LOADING,
    UPDATE_PLANET_ERROR,
    UPDATE_PLANET_SUCCESS,
    UPDATE_PLANET_LOADING,
    SET_FILTER,
} from "app/constants/actionTypes";

const initialState = {
    planets: [],
    isLoading: false,
    error: null,
    selectedPlanet: null,
    filter: "",
};

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case FETCH_PLANETS_LOADING:
            return {
                ...state,
                isLoading: true,
                error: null,
            };
        case FETCH_PLANETS_SUCCESS:
            console.log(state)
            return {
                ...state,
                planets: action.payload,
                isLoading: false,
                error: null,
            };
        case FETCH_PLANETS_ERROR:
            return {
                ...state,
                isLoading: false,
                error: action.payload,
            };
        case FETCH_PLANET_LOADING:
            return {
                ...state,
                selectedPlanet: null,
                isLoading: true,
                error: null,
            };
        case FETCH_PLANET_SUCCESS:
            return {
                ...state,
                selectedPlanet: action.payload,
                isLoading: false,
                error: null,
            };
        case FETCH_PLANET_ERROR:
            return {
                ...state,
                isLoading: false,
                error: action.payload,
            };
        case DELETE_PLANET_LOADING:
            return {
                ...state,
                isLoading: true,
                error: null,
            };
        case DELETE_PLANET_SUCCESS:
            return {
                ...state,
                planets: state.planets.filter(({ id }) => id !== action.payload),
                isLoading: false,
                error: null,
            };
        case DELETE_PLANET_ERROR:
            return {
                ...state,
                isLoading: false,
                error: action.payload,
            };
        case ADD_PLANETS_LOADING:
            return {
                ...state,
                isLoading: true,
                error: null,
            };
        case ADD_PLANETS_SUCCESS:
            return {
                ...state,
                planets: [...state.planets, action.payload],
                isLoading: false,
                error: null,
            };
        case ADD_PLANETS_ERROR:
            return {
                ...state,
                isLoading: false,
                error: action.payload,
            };
        case UPDATE_PLANET_LOADING:
            return {
                ...state,
                isLoading: true,
                error: null,
            };
        case UPDATE_PLANET_SUCCESS:
            return {
                ...state,
                planets: state.planets.map((planet) =>
                    planet.id === action.payload.id ? action.payload : planet
                ),
                selectedPlanet: action.payload,
                isLoading: false,
                error: null,
            };
        case UPDATE_PLANET_ERROR:
            return {
                ...state,
                isLoading: false,
                error: action.payload,
            };
        case SET_FILTER:
            return {
                ...state,
                filter: action.payload,
            };

        default:
            return state;
    }
};

export default reducer;