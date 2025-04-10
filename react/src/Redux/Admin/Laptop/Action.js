import {
  CREATE_LAPTOP_REQUEST,
  CREATE_LAPTOP_SUCCESS,
  CREATE_LAPTOP_FAILURE,
  UPDATE_LAPTOP_REQUEST,
  UPDATE_LAPTOP_SUCCESS,
  UPDATE_LAPTOP_FAILURE,
  DELETE_LAPTOP_REQUEST,
  DELETE_LAPTOP_SUCCESS,
  DELETE_LAPTOP_FAILURE,
  UPLOAD_FILES_REQUEST,
  UPLOAD_FILES_SUCCESS,
  UPLOAD_FILES_FAILURE,

  GET_LAPTOPS_REQUEST,
  GET_LAPTOPS_SUCCESS,
  GET_LAPTOPS_FAILURE,
  SEARCH_LAPTOP_REQUEST,
  SEARCH_LAPTOP_SUCCESS,
  SEARCH_LAPTOP_FAILURE,
  FIND_LAPTOP_BY_ID_REQUEST,
  FIND_LAPTOP_BY_ID_SUCCESS,
  FIND_LAPTOP_BY_ID_FAILURE,
  FIND_LAPTOPS_BY_FILTER_REQUEST,
  FIND_LAPTOPS_BY_FILTER_SUCCESS,
  FIND_LAPTOPS_BY_FILTER_FAILURE,

} from "./ActionType";
import api, { API_BASE_URL } from "../../../Config/api";

export const searchLaptop = (keyword) => async (dispatch) => {
    try {
        dispatch({type: SEARCH_LAPTOP_REQUEST});

        const {data} = await api.get(`/laptops/search`, {
            params: {
                q: keyword
            }
        });

        console.log("laptops by  id : ", data);
        dispatch({
            type: SEARCH_LAPTOP_SUCCESS,
            payload: data,
        });
    } catch (error) {
        dispatch({
            type: SEARCH_LAPTOP_FAILURE,
            payload:
                error.response && error.response.data.message
                    ? error.response.data.message
                    : error.message,
        });
    }
};

export const getAllLaptops = () => async (dispatch) => {
  try {
    dispatch({ type: GET_LAPTOPS_REQUEST });
    const { data } = await api.get(`${API_BASE_URL}/laptops`);
    dispatch({
      type: GET_LAPTOPS_SUCCESS,
      payload: data,
    });
  } catch (error) {
    dispatch({
      type: GET_LAPTOPS_FAILURE,
      payload:
        error.response && error.response.data.message
          ? error.response.data.message
          : error.message,
    });
  }
};
export const findLaptopById = (reqData) => async (dispatch) => {
    try {
        dispatch({type: FIND_LAPTOP_BY_ID_REQUEST});

        const {data} = await api.get(`/laptops/${reqData.laptopId}`);

        console.log("laptops by  id : ", data);
        dispatch({
            type: FIND_LAPTOP_BY_ID_SUCCESS,
            payload: data,
        });
    } catch (error) {
        dispatch({
            type: FIND_LAPTOP_BY_ID_FAILURE,
            payload:
                error.response && error.response.data.message
                    ? error.response.data.message
                    : error.message,
        });
    }
};
export const findLaptops = (reqData) => async (dispatch) => {
    const {
        brands,
        category,
        gpuType,
        cpuTechnologies,
        ramCapacity,
        diskCapacity,
        screenSize,
        cpuId,
        minPrice,
        maxPrice,
        sortPrice,
        minRamMemory,
        maxRamMemory,
        page,
        size,
        color,
        gpuIds,
        brandId
    } = reqData;

    try {
        dispatch({ type: FIND_LAPTOPS_BY_FILTER_REQUEST });
        // Xây dựng URL query string
        const queryParams = new URLSearchParams();

        // if (brands) queryParams.append("brands", brands.join(","));
        // if (category) queryParams.append("category", category);
        // if (gpuType) queryParams.append("gpuType", gpuType);
        // if (cpuTechnologies) queryParams.append("cpuTechnologies", cpuTechnologies.join(","));
        if (gpuIds) queryParams.append("gpuIds", gpuIds.join(","));
        if (diskCapacity) {
          queryParams.append("minDiskCapacity", diskCapacity[0])
          queryParams.append("maxDiskCapacity", diskCapacity[1])
        };
        // if (ramCapacity) queryParams.append("ramCapacity", ramCapacity.join(","));
        // if (diskCapacity) queryParams.append("diskCapacity", diskCapacity.join(","));
        // if (screenSize) queryParams.append("screenSize", screenSize.join(","));

        if (brandId) queryParams.append("brandId", brandId);
        if (minRamMemory) queryParams.append("minRamMemory", minRamMemory);
        if (cpuId) queryParams.append("cpuId", cpuId);
        if (maxRamMemory) queryParams.append("maxRamMemory", maxRamMemory);
        if (minPrice) queryParams.append("minPrice", minPrice);
        if (maxPrice) queryParams.append("maxPrice", maxPrice);
        if (color) queryParams.append("colors", color);

        if (sortPrice) queryParams.append("sortPrice", sortPrice);
        if (page !== undefined) queryParams.append("page", page);
        if (size !== undefined) queryParams.append("size", size);


        console.log('cls-linh-req',queryParams);
        

        // Gửi request GET với các query param đã xây dựng
        const res = await api.get(`/laptops/filter?${queryParams.toString()}`);
        
        dispatch({
            type: FIND_LAPTOPS_BY_FILTER_SUCCESS,
            payload: res.data.content,
        });
    } catch (error) {
        dispatch({
            type: FIND_LAPTOPS_BY_FILTER_FAILURE,
            payload:
                error.response && error.response.data.message
                    ? error.response.data.message
                    : error.message,
        });
    }
};


export const createLaptop = (laptop) => async (dispatch) => {
  try {
    dispatch({ type: CREATE_LAPTOP_REQUEST });
    const { data } = await api.post(`${API_BASE_URL}/laptops/api/admin`, laptop.data);
    dispatch({
      type: CREATE_LAPTOP_SUCCESS,
      payload: data,
    });
    console.log("Created laptop: ", data);
    return data;
  } catch (error) {
    dispatch({
      type: CREATE_LAPTOP_FAILURE,
      payload:
        error.response && error.response.data.message
          ? error.response.data.message: error.message,
    });
    console.error("Error creating laptop:", error);
  }
};

export const uploadFiles = (laptopId, formData) => async (dispatch) => {
  try {
    dispatch({ type: UPLOAD_FILES_REQUEST });
    const { data } = await api.post(
        `${API_BASE_URL}/laptops/api/admin/${laptopId}/images`,
        formData,
        { headers: { "Content-Type": "multipart/form-data" } }
    );
    dispatch({
      type: UPLOAD_FILES_SUCCESS,
      payload: data,
    });
    console.log("Files uploaded successfully", data);
    return data; 
  } catch (error) {
    dispatch({
      type: UPLOAD_FILES_FAILURE,
      payload:
          error.response && error.response.data.message
              ? error.response.data.message
              : error.message,
    });
  }
};
export const updateLaptop = (options) => async (dispatch) => {
  try {
    dispatch({ type: UPDATE_LAPTOP_REQUEST });
    const { data } = await api.put(
      `${API_BASE_URL}/laptops/api/admin/${options.id}`,
      options
    );
    dispatch({
      type: UPDATE_LAPTOP_SUCCESS,
      payload: data,
    });
    window.location.reload();
    
  } catch (error) {
    dispatch({
      type: UPDATE_LAPTOP_FAILURE,
      payload:
        error.response && error.response.data.message
          ? error.response.data.message
          : error.message,
    });
  }
};

export const deleteLaptop = (data) => async (dispatch) => {
  console.log("Delete laptop with id:", data)
  try {
    const jwt = localStorage.getItem("jwt");
    dispatch({ type: DELETE_LAPTOP_REQUEST });
    await api.put(`/laptops/api/delete/${data}`, {jwt});
    dispatch({
      type: DELETE_LAPTOP_SUCCESS,
      // payload: data,
    });
    console.log("Laptop deleted ", data);
  } catch (error) {
    console.log("Catch error ", error)
    dispatch({
      type: DELETE_LAPTOP_FAILURE,
      payload:
        error.response && error.response.data.message
          ? error.response.data.message
          : error.message,
    });
  }
};
// export const deleteLaptop = (laptopId) => async (dispatch) => {
//     console.log("delete laptop action", laptopId)
//     try {
//         dispatch({type: DELETE_LAPTOP_REQUEST});

//         let {data} = await api.delete(`/api/admin/laptops/${laptopId}/delete`);

//         dispatch({
//             type: DELETE_LAPTOP_SUCCESS,
//             payload: data,
//         });

//         console.log("laptop delte ", data)
//     } catch (error) {
//         console.log("catch error ", error)
//         dispatch({
//             type: DELETE_LAPTOP_FAILURE,
//             payload:
//                 error.response && error.response.data.message
//                     ? error.response.data.message
//                     : error.message,
//         });
//     }
// };

