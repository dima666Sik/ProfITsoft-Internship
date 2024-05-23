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
        case FILTER_PLANETS_LOADING:
            return {
                ...state,
                isLoading: true,
                error: null,
            };
        case FILTER_PLANETS_SUCCESS:
            return {
                planets: action.payload,
                isLoading: false,
                error: null,
            };
        case FILTER_PLANETS_ERROR:
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