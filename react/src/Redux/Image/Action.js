
import {
    FETCH_IMAGE_CAROUSEL_REQUEST,
    FETCH_IMAGE_CAROUSEL_SUCCESS,
    FETCH_IMAGE_CAROUSEL_FAIL,
} from "./ActionTypes";
import api from "../../Config/api";

export const fetchCarouselImages = () => async (dispatch) => {
    dispatch({ type: FETCH_IMAGE_CAROUSEL_REQUEST });
    try {
        const response = await api.get("/home/slideimage"); // API backend
        dispatch({
            type: FETCH_IMAGE_CAROUSEL_SUCCESS,
            payload: response.data, // Danh sách URL
        });
    } catch (error) {
        dispatch({
            type: FETCH_IMAGE_CAROUSEL_FAIL,
            payload: error.message || "Failed to load carousel images",
        });
    }
};
