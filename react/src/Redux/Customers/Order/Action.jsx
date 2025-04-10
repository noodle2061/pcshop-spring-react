import axios from "axios";
import {
  SET_SELECTED_CART_ITEMS,
  CREATE_ORDER_FAILURE,
  CREATE_ORDER_REQUEST,
  CREATE_ORDER_SUCCESS,
  GET_ORDER_BY_ID_FAILURE,
  GET_ORDER_BY_ID_REQUEST,
  GET_ORDER_BY_ID_SUCCESS,
  GET_ORDER_HISTORY_FAILURE,
  GET_ORDER_HISTORY_REQUEST,
  GET_ORDER_HISTORY_SUCCESS,

} from "./ActionType";
import api, { API_BASE_URL } from "../../../Config/api";

export const setSelectedCartItems = (cartItems) => ({
  type: SET_SELECTED_CART_ITEMS,
  payload: cartItems,
});

export const createOrder = (orderData) => async (dispatch) => {
  console.log("Order data ", orderData);
  try {
    dispatch({ type: CREATE_ORDER_REQUEST });
    const payload = {
      shippingAddress: orderData.shippingAddress,
      paymentMethod: orderData.paymentMethod,
      cartItems: orderData.cartItems
    };
    const { data } = await api.post(`${API_BASE_URL}/api/orders/`,payload);
    if (data.id) {
      orderData.navigate({ search: `step=3&order_id=${data.id}` });
    }
    console.log("Created order: ", data);
    dispatch({
      type: CREATE_ORDER_SUCCESS,
      payload: data,
    });
  } catch (error) {
    console.log("catch error : ", error);
    dispatch({
      type: CREATE_ORDER_FAILURE,
      payload:
        error.response && error.response.data.message
          ? error.response.data.message
          : error.message,
    });
  }
};

export const getOrderById = (orderId) => async (dispatch) => {
  console.log("get order req ", orderId);
  try {
    dispatch({ type: GET_ORDER_BY_ID_REQUEST });

    const { data } = await api.get(`/api/orders/${orderId}`);
    console.log("order by id ", data);
    dispatch({
      type: GET_ORDER_BY_ID_SUCCESS,
      payload: data,
    });
  } catch (error) {
    console.log("catch ",error)
    dispatch({
      type: GET_ORDER_BY_ID_FAILURE,
      payload:
        error.response && error.response.data.message
          ? error.response.data.message
          : error.message,
    });
  }
};

export const getOrderHistory = (status='',page=1) => async (dispatch, getState) => {
  try {
    dispatch({ type: GET_ORDER_HISTORY_REQUEST });
    const { data } = await api.get(`/api/orders/user?status=${status}&page=${page}`);
    console.log("Order history -------- ", data);
    dispatch({
      type: GET_ORDER_HISTORY_SUCCESS,
      payload: data,
    });
  } catch (error) {
    dispatch({
      type: GET_ORDER_HISTORY_FAILURE,
      payload:
        error.response && error.response.data.message
          ? error.response.data.message
          : error.message,
    });
  }
};
