import {
    CLEAR_STATE,
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
        case UPDATE_PLANET_LOADING:
            return {
                ...state,
                isLoading: true,
                error: null,
            };
        case UPDATE_PLANET_SUCCESS:
            return {
                ...state,
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
        case CLEAR_STATE:
            return {
                ...state,
                planet: null,
                isLoading: false,
                error: null,
            };
        default:
            return state;
    }
};

export default reducer;