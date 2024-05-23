import {
    DELETE_PLANET_LOADING,
    DELETE_PLANET_SUCCESS,
    DELETE_PLANET_ERROR,
    FETCH_PLANET_ERROR,
    FETCH_PLANET_SUCCESS,
    FETCH_PLANET_LOADING,
    UPDATE_PLANET_ERROR,
    UPDATE_PLANET_SUCCESS,
    UPDATE_PLANET_LOADING,
} from "app/constants/actionTypes";

const initialState = {
    planet: null,
    isLoading: false,
    error: null,
};

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case FETCH_PLANET_LOADING:
            return {
                ...state,
                planet: null,
                isLoading: true,
                error: null,
            };
        case FETCH_PLANET_SUCCESS:
            return {
                ...state,
                planet: action.payload,
                isLoading: false,
                error: null,
            };
        case FETCH_PLANET_ERROR:
            return {
                ...state,
                planet: null,
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
                planets: state.planets.filter(({id}) => id !== action.payload),
                isLoading: false,
                error: null,
            };
        case DELETE_PLANET_ERROR:
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
        default:
            return state;
    }
};

export default reducer;