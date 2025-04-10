import {applyMiddleware, combineReducers, legacy_createStore} from "redux"
import thunk from "redux-thunk";
import authReducer from "./Auth/Reducer";
import laptopReducer from "./Admin/Laptop/Reducer";
import cartReducer from "./Customers/Cart/Reducer";
import { orderReducer } from "./Customers/Order/Reducer";
import adminOrderReducer from "./Admin/Orders/Reducer";
import reviewReducer from "./Customers/Review/Reducer";
import imageReducer from "./Image/Reducer";

const rootReducers=combineReducers({
    auth:authReducer,
    cart:cartReducer,
    order:orderReducer,
    review:reviewReducer,
    image: imageReducer,
    laptop:laptopReducer,
    adminOrder:adminOrderReducer
});

export const store = legacy_createStore(rootReducers,applyMiddleware(thunk))