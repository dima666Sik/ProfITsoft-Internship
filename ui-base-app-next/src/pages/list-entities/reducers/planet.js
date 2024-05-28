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
    FETCH_PLANET_ERROR,
    FETCH_PLANET_SUCCESS,
    FETCH_PLANET_LOADING,
    UPDATE_PLANET_ERROR,
    UPDATE_PLANET_SUCCESS,
    UPDATE_PLANET_LOADING,
    ADD_PLANET_LOADING,
    ADD_PLANET_SUCCESS,
    ADD_PLANET_ERROR
} from "app/constants/actionTypes";
import mockPlanets from "../actions/mock-planets.json";

const initialState = {
    planets: mockPlanets,
    isLoading: false,
    error: null,
    planet: null
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
                planets: action.payload ? state.planets : mockPlanets,
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
                planets: state.planets.slice().sort((a, b) => {
                    if (action.payload === 'ASC') {
                        return a.name.localeCompare(b.name);
                    } else if (action.payload === 'DESC') {
                        return b.name.localeCompare(a.name);
                    }
                    return 0;
                }),
                isLoading: false,
                error: null,
            };
        case FILTER_PLANETS_ERROR:
            return {
                ...state,
                isLoading: false,
                error: action.payload,
            };
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
                planet: action.payload,
                isLoading: false,
                error: null,
            };
        case UPDATE_PLANET_ERROR:
            return {
                ...state,
                isLoading: false,
                error: action.payload,
            };
        case ADD_PLANET_LOADING:
            return {
                ...state,
                isLoading: true,
                error: null,
            };
        case ADD_PLANET_SUCCESS:
            return {
                ...state,
                planets: [...state.planets, action.payload],
                isLoading: false,
                error: null,
            };
        case ADD_PLANET_ERROR:
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