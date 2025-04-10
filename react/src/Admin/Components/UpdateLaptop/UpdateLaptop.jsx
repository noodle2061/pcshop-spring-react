import { useState } from "react";
import {
  Box,
  Checkbox,
  IconButton,
  ListItemText,
  Typography,
} from "@mui/material";
import {
  Grid,
  TextField,
  Button,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";
import "./index.css";

import { Fragment } from "react";
// import "./CreateLaptopForm.css";
import { useDispatch, useSelector } from "react-redux";
import {
  createLaptop,
  findLaptopById,
  updateLaptop,
  uploadFiles,
} from "../../../Redux/Admin/Laptop/Action";
import { useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import RemoveIcon from "@mui/icons-material/Remove";
import CloseIcon from "@mui/icons-material/Close";
import AddIcon from "@mui/icons-material/Add";

const initialSizes = [
  { name: "S", quantity: 0 },
  { name: "M", quantity: 0 },
  { name: "L", quantity: 0 },
];

const UpdateLaptopForm = (props) => {
  const { laptopId } = useParams();

  const id = laptopId ?? props.id;
  const dispatch = useDispatch();
  const { laptops, loading, error } = useSelector((state) => state.laptop);

  const [laptopData, setLaptopData] = useState({
    brandId: null,
    model: "",
    cpu: "",
    gpus: [],
    ramMemory: 0,
    ramDetail: "",
    diskCapacity: 0,
    diskDetail: "",
    screenSize: 0,
    screenDetail: "",
    osVersionId: null,
    keyboardType: "",
    batteryCharger: "",
    design: "",
    laptopColors: [{ colorId: null, quantity: 0 }],
    categories: [],
    origin: "",
    warranty: 0,
    price: 0,
    status: 0,
    discountPercent: 0,
  });

  useEffect(() => {
    const initValue = laptops.find((obj) => obj.id === id);
    setLaptopData(initValue);
  }, [laptops]);

  const [brands, setBrands] = useState([]);
  const [colors, setColors] = useState([]);
  const [gpus, setGpus] = useState([]);
  const [cpus, setCpus] = useState([]);
  const [categories, setCategories] = useState([]);
  const [availableColors, setAvailableColors] = useState([]);
  const [osVersions, setOsVersions] = useState([]);
  const [files, setFiles] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const baseUrl = "http://localhost:8080/";
        const [
          brandsRes,
          colorsRes,
          gpusRes,
          cpusRes,
          categoriesRes,
          osVersionsRes,
        ] = await Promise.all([
          axios.get(`${baseUrl}brands`),
          axios.get(`${baseUrl}colors`),
          axios.get(`${baseUrl}gpus`),
          axios.get(`${baseUrl}cpus`),
          axios.get(`${baseUrl}categories`),
          axios.get(`${baseUrl}osversions`),
        ]);
        setBrands(brandsRes.data);
        setColors(colorsRes.data);
        setAvailableColors(colorsRes.data);
        setOsVersions(osVersionsRes.data);
        setGpus(gpusRes.data);
        setCpus(cpusRes.data);
        setCategories(categoriesRes.data);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };
    fetchData();
  }, []);

  const handleChange = (e) => {

    const { name, value } = e.target;
    setLaptopData((prevState) => {
      let parsedValue = value;
      if (name === "cpu") {
        parsedValue = { id: value }; // CPU là object { id }
      } else if (name === "screenSize" || name === "discountPercent") {
        parsedValue = value === "" ? "" : parseFloat(value);
      }
      return {
        ...prevState,
        [name]: parsedValue ?? "",
      };
    });
  };

  const handleCategoryChange = (e) => {
    const value = e.target.value; // Mảng id được chọn
    const updatedCategories = value.map((id) => ({ id })); // Chuyển thành mảng object { id }

    setLaptopData((prevState) => ({
      ...prevState,
      categories: updatedCategories, // Lưu mảng object { id }
    }));
  };

  const handleLaptopColorChange = (index, field, value) => {
    const updatedColors = [...laptopData.laptopColors];
    updatedColors[index][field] = value;
    setLaptopData((prevState) => ({
      ...prevState,
      laptopColors: updatedColors,
    }));

    if (field === "colorId") {
      const selectedColorIds = updatedColors.map((color) => color.colorId);
      const remainingColors = colors.filter(
        (color) => !selectedColorIds.includes(color.id)
      );
      setAvailableColors(remainingColors);
    }
  };

  const addLaptopColorRow = () => {
    if (laptopData.laptopColors.length < colors.length) {
      setLaptopData((prevState) => ({
        ...prevState,
        laptopColors: [
          ...prevState.laptopColors,
          { colorId: "", quantity: "" },
        ],
      }));
    }
  };

  const removeLaptopColorRow = (index) => {
    const updatedColors = [...laptopData.laptopColors];
    updatedColors.splice(index, 1);
    setLaptopData((prevState) => ({
      ...prevState,
      laptopColors: updatedColors,
    }));

    const selectedColorIds = updatedColors.map((color) => color.colorId);
    const remainingColors = colors.filter(
      (color) => !selectedColorIds.includes(color.id)
    );
    setAvailableColors(remainingColors);
  };

  const handleGpuChange = (event) => {
    const selectedGpuIds = event.target.value;
    const updatedGpus = selectedGpuIds.map((id) => ({ id }));
    if (selectedGpuIds.length > 2) {
      return;
    }
    setLaptopData((prevState) => ({
      ...prevState,
      gpus: updatedGpus,
    }));
  };

  // const isGpuTypeSelected = (type) =>
  //   laptopData.gpus.some((gpu) => {
  //     const selectedGpu = gpus.find((g) => g.id === gpu.id);
  //     return selectedGpu?.type === type;
  //   });

  // const isGpuTypeSelected = (type, currentGpuId) =>
  //   laptopData.gpus.some((gpuId) => {
  //     const gpu = gpus.find((g) => g.id === gpuId);
  //     return gpu && gpu.type === type && gpu.id !== currentGpuId;
  //   });
  const isGpuTypeSelected = (type, currentGpuId) =>
    laptopData.gpus.some((gpu) => {
      const selectedGpu = gpus.find((g) => g.id === gpu.id); // Tìm GPU trong danh sách `gpus` gốc
      return selectedGpu?.type === type && gpu.id !== currentGpuId; // So sánh loại và loại bỏ GPU hiện tại
    });

  // const isGpuDisabled = (gpuType, gpuId) => {
  //   const selectedGpuTypes = laptopData.gpus.map((gpu) => {
  //     const selectedGpu = gpus.find((g) => g.id === gpu.id);
  //     return selectedGpu?.type;
  //   });

  //   if (selectedGpuTypes.includes("INTEGRATED") && gpuType === "INTEGRATED" && !laptopData.gpus.some((gpu) => gpu.id === gpuId)) {
  //     return true;
  //   }

  //   if (selectedGpuTypes.includes("DISCRETE") && gpuType === "DISCRETE" && !laptopData.gpus.some((gpu) => gpu.id === gpuId)) {
  //     return true;
  //   }

  //   return false;
  // };

  const status = [
    {
      value: 0,
      label: "Deleted",
    },
    {
      value: 1,
      label: "Active",
    },
  ];

  console.log('status', laptopData);


  const handleFileChange = (e) => {
    const selectedFiles = Array.from(e.target.files);
    setFiles((prevFiles) => {
      const uniqueFiles = [...prevFiles];
      selectedFiles.forEach((file) => {
        if (!uniqueFiles.some((f) => f.name === file.name)) {
          uniqueFiles.push(file);
        }
      });
      return uniqueFiles;
    });
    // Reset input field to allow selecting the same file again
    e.target.value = "";
  };

  const removeFile = (index) => {
    setFiles((prevFiles) => prevFiles.filter((_, i) => i !== index));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log("Submitting form...");
    console.log(laptopData);
    const res = await dispatch(updateLaptop(laptopData));
    console.log("Result:", res);
    if (res) {
      console.log(res.id);
      const formData = new FormData();
      files.forEach((file) => {
        formData.append("files", file);
      });
      const fullData = await dispatch(uploadFiles(res.id, formData));
      alert("Laptop created successfully", fullData);
    }
  };

  return (
    <Fragment>
      <Typography variant="h3" sx={{ textAlign: "center", marginBottom: 3 }}>
        Update New Laptop
      </Typography>
      <form onSubmit={handleSubmit}>
        <Grid container spacing={2}>
          {/*Brand*/}
          <Grid item xs={12} sm={6}>
            <FormControl fullWidth>
              <InputLabel>Brand</InputLabel>
              <Select
                name="brandId" // Đảm bảo tên trường khớp với state
                value={laptopData.brandId} // Đảm bảo giá trị được lấy từ laptopData.brandId
                onChange={(e) => handleChange(e)} // Sử dụng handleChange để cập nhật state
                renderValue={(selected) =>
                  brands.find((brand) => brand.id === selected)?.name ||
                  "Select Brand"
                }
                MenuProps={{ PaperProps: { style: { maxHeight: "50vh" } } }}
                label="Brand"
              >
                {brands.map((brand) => (
                  <MenuItem key={brand.id} value={brand.id}>
                    {brand.name}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>

          {/*Model*/}
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label="Model"
              name="model"
              value={laptopData.model}
              onChange={handleChange}
            />
          </Grid>

          {/* CPU */}
          <Grid item xs={12} sm={6}>
            <FormControl fullWidth>
              <InputLabel>CPU</InputLabel>
              <Select
                name="cpu" // Tên trường khớp với handleChange
                value={laptopData.cpu?.id || ""} // Lấy id từ object cpu trong state
                onChange={handleChange} // Sử dụng handleChange
                renderValue={(selected) =>
                  cpus.find((cpu) => cpu.id === selected)?.model || "Select CPU"
                }
                label="CPU"
                MenuProps={{
                  PaperProps: {
                    style: { maxHeight: "50vh" },
                  },
                }}
              >
                {cpus.map((cpu) => (
                  <MenuItem key={cpu.id} value={cpu.id}>
                    {cpu.model}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>

          {/* GPU */}
          {/* <Grid item xs={12} sm={6}>
            <FormControl fullWidth>
              <InputLabel>GPUs</InputLabel>
              <Select multiple value={laptopData.gpus.map((gpu) => gpu.id)} onChange={handleGpuChange}
                renderValue={(selected) => selected.map((id) => gpus.find((gpu) => gpu.id === id)?.model || "Unknown GPU").join(", ")}
                label="GPUs" MenuProps={{ PaperProps: { style: { maxHeight: "50vh" } } }} >
                {gpus.map((gpu) => (
                  <MenuItem
                    key={gpu.id}
                    value={gpu.id}
                    disabled={!laptopData.gpus.includes(gpu.id) && isGpuTypeSelected(gpu.type)}
                  >
                    <Checkbox checked={laptopData.gpus.some((selectedGpu) => selectedGpu.id === gpu.id)} />
                    <ListItemText primary={`${gpu.model} (${gpu.type})`} />
                  </MenuItem>

                ))}
              </Select>
            </FormControl>
          </Grid> */}

          <Grid item xs={12} sm={6}>
            <FormControl fullWidth>
              <InputLabel>GPUs</InputLabel>
              <Select
                multiple
                value={laptopData.gpus.map((gpu) => gpu.id)}
                onChange={handleGpuChange}
                renderValue={(selected) =>
                  selected
                    .map(
                      (id) =>
                        gpus.find((gpu) => gpu.id === id)?.model ||
                        "Unknown GPU"
                    )
                    .join(", ")
                }
                label="GPUs"
                MenuProps={{ PaperProps: { style: { maxHeight: "50vh" } } }}
              >
                {gpus.map((gpu) => (
                  <MenuItem
                    key={gpu.id}
                    value={gpu.id}
                    disabled={
                      !laptopData.gpus.some(
                        (selectedGpu) => selectedGpu.id === gpu.id
                      ) && isGpuTypeSelected(gpu.type, gpu.id)
                    }
                  >
                    <Checkbox
                      checked={laptopData.gpus.some(
                        (selectedGpu) => selectedGpu.id === gpu.id
                      )}
                    />
                    <ListItemText primary={`${gpu.model} (${gpu.type})`} />
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>

          <Grid item xs={12} sm={6}>
            <FormControl fullWidth>
              <InputLabel>Status</InputLabel>
              <Select
                name="status" // Tên trường khớp với handleChange
                value={laptopData.status} // Lấy id từ object cpu trong state
                onChange={handleChange} // Sử dụng handleChange
                renderValue={(selected) =>
                  status.find((st) => st.value === selected)?.label ?? 'Active'
                }
                label="Status"
                MenuProps={{
                  PaperProps: {
                    style: { maxHeight: "50vh" },
                  },
                }}
              >
                {status.map((st) => (
                  <MenuItem key={st.value} value={st.value}>
                    {st.label}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>

          {/* <Grid item xs={12} sm={6}>
            <FormControl fullWidth>
              <InputLabel>GPUs</InputLabel>
              <Select
                multiple
                value={laptopData.gpus.map((gpu) => gpu.id)}
                onChange={handleGpuChange}
                renderValue={(selected) =>
                  selected.map((id) => gpus.find((gpu) => gpu.id === id)?.model || "Unknown GPU").join(", ")
                }
                label="GPUs"
                MenuProps={{ PaperProps: { style: { maxHeight: "50vh" } } }}
              >
                {gpus.map((gpu) => (
                  <MenuItem
                    key={gpu.id}
                    value={gpu.id}
                    disabled={isGpuDisabled(gpu.type, gpu.id)}
                  >
                    <Checkbox checked={laptopData.gpus.some((selectedGpu) => selectedGpu.id === gpu.id)} />
                    <ListItemText primary={`${gpu.model} (${gpu.type})`} />
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid> */}

          {/*RAM*/}
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label="RAM Memory"
              name="ramMemory"
              value={laptopData.ramMemory}
              onChange={handleChange}
              type="number"
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label="RAM Detail"
              name="ramDetail"
              value={laptopData.ramDetail}
              onChange={handleChange}
            />
          </Grid>

          {/*Disk*/}
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label="Disk Capacity (GB)"
              name="diskCapacity"
              value={laptopData.diskCapacity}
              onChange={handleChange}
              type="number"
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label="Disk Detail"
              name="diskDetail"
              value={laptopData.diskDetail}
              onChange={handleChange}
            />
          </Grid>

          {/* Screen */}
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label="Screen Size (inch)"
              name="screenSize"
              value={laptopData.screenSize}
              onChange={handleChange}
              type="number"
              inputProps={{ step: "0.1", min: 0 }}
            />
          </Grid>

          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label="Screen Detail"
              name="screenDetail"
              value={laptopData.screenDetail}
              onChange={handleChange}
            />
          </Grid>

          {/* OS Version */}
          <Grid item xs={12} sm={6}>
            <FormControl fullWidth>
              <InputLabel>OS Version</InputLabel>
              <Select
                name="osVersionId" // Tên trường khớp với state
                value={laptopData.osVersionId || ""} // Lấy giá trị từ laptopData.osVersionId
                onChange={(e) => handleChange(e)} // Sử dụng handleChange để cập nhật state
                renderValue={(selected) =>
                  osVersions.find((os) => os.id === selected)?.version ||
                  "Select OS Version"
                }
                MenuProps={{ PaperProps: { style: { maxHeight: "50vh" } } }}
                label="OS version"
              >
                {osVersions.map((os) => (
                  <MenuItem key={os.id} value={os.id}>
                    {os.version}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>

          {/* Keyboard Type */}
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label="Keyboard Type"
              name="keyboardType"
              value={laptopData.keyboardType}
              onChange={handleChange}
            />
          </Grid>

          {/* Battery Charger */}
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label="Battery Charger"
              name="batteryCharger"
              value={laptopData.batteryCharger}
              onChange={handleChange}
            />
          </Grid>

          {/* Design */}
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label="Design"
              name="design"
              value={laptopData.design}
              onChange={handleChange}
            />
          </Grid>

          {/*Laptop - Color - Quantity*/}
          <Grid item xs={12}>
            <Typography variant="h6">Laptop Colors</Typography>
            {laptopData.laptopColors.map((laptopColor, index) => (
              <Grid
                container
                spacing={2}
                alignItems="center"
                key={index}
                sx={{ marginTop: index > 0 ? 2 : 0 }}
              >
                <Grid item xs={5}>
                  <FormControl fullWidth>
                    <InputLabel>Color</InputLabel>
                    <Select
                      value={laptopColor.colorId}
                      label="Color"
                      onChange={(e) =>
                        handleLaptopColorChange(
                          index,
                          "colorId",
                          e.target.value
                        )
                      }
                      renderValue={(selected) =>
                        colors.find((color) => color.id === selected)?.name ||
                        ""
                      }
                    >
                      {availableColors.map((color) => (
                        <MenuItem key={color.id} value={color.id}>
                          {" "}
                          {color.name}{" "}
                        </MenuItem>
                      ))}
                    </Select>
                  </FormControl>
                </Grid>

                <Grid item xs={5}>
                  <TextField
                    fullWidth
                    label="Quantity"
                    type="number"
                    value={laptopColor.quantity}
                    onChange={(e) =>
                      handleLaptopColorChange(index, "quantity", e.target.value)
                    }
                  />
                </Grid>
                <Grid item xs={2}>
                  {index > 0 && (
                    <IconButton onClick={() => removeLaptopColorRow(index)}>
                      <RemoveIcon />
                    </IconButton>
                  )}
                  <IconButton onClick={addLaptopColorRow}>
                    <AddIcon />
                  </IconButton>
                </Grid>
              </Grid>
            ))}
          </Grid>

          {/*Category*/}
          <Grid item xs={12} sm={6}>
            <FormControl fullWidth>
              <InputLabel>Categories</InputLabel>
              <Select
                multiple
                name="categories"
                value={laptopData.categories.map((cat) => cat.id)} // Chuyển đổi thành mảng id
                label="Categories"
                onChange={handleCategoryChange}
                MenuProps={{ PaperProps: { style: { maxHeight: "50vh" } } }}
                renderValue={(selected) =>
                  selected
                    .map(
                      (id) =>
                        categories.find((category) => category.id === id)
                          ?.name || "Unknown Category"
                    )
                    .join(", ")
                }
              >
                {categories.map((category) => (
                  <MenuItem key={category.id} value={category.id}>
                    <Checkbox
                      checked={laptopData.categories.some(
                        (cat) => cat.id === category.id
                      )}
                    />
                    <ListItemText primary={category.name} />
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>

          {/* Origin */}
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label="Origin"
              name="origin"
              value={laptopData.origin}
              onChange={handleChange}
            />
          </Grid>

          {/* Warranty*/}
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label="Warranty (months)"
              name="warranty"
              value={laptopData.warranty}
              onChange={handleChange}
              type="number"
            />
          </Grid>

          {/* Price */}
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label="Price"
              name="price"
              value={laptopData.price}
              onChange={handleChange}
              type="number"
            />
          </Grid>

          {/* Discount Percentage */}
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label="Discount Percentage"
              name="discountPercent"
              value={laptopData.discountPercent}
              onChange={handleChange}
              type="number"
              inputProps={{ step: "0.1", min: 0, max: 100 }}
            />
          </Grid>

          {/*Image*/}
          <Grid item xs={12} sm={6}>
            <Box
              sx={{
                border: "1px solid rgba(255, 255, 255, 0.23)",
                borderRadius: "4px",
                padding: "10px",
                display: "flex",
                alignItems: "center",
                height: "56px",
                transition: "border-color 0.3s",
                "&:hover": { borderColor: "rgba(255, 255, 255, 0.9)" },
              }}
            >
              <Button
                variant="outlined"
                component="label"
                style={{ marginRight: "10px" }}
              >
                Choose Files
                <input
                  type="file"
                  hidden
                  multiple
                  onChange={handleFileChange}
                />
              </Button>
              <Typography>
                {files.length > 0
                  ? `${files.length} files selected`
                  : "No files selected"}
              </Typography>
            </Box>
          </Grid>
          {/*Display image*/}
          <Grid item xs={12}>
            <div style={{ display: "flex", flexWrap: "wrap", gap: "10px" }}>
              {files.map((file, index) => (
                <div
                  key={index}
                  style={{ position: "relative", display: "inline-block" }}
                >
                  <img
                    src={URL.createObjectURL(file)}
                    alt="preview"
                    style={{
                      width: "100px",
                      height: "100px",
                      objectFit: "cover",
                    }}
                  />
                  <IconButton
                    size="small"
                    style={{ position: "absolute", top: 0, right: 0 }}
                    onClick={() => removeFile(index)}
                  >
                    <CloseIcon fontSize="small" />
                  </IconButton>
                </div>
              ))}
            </div>
          </Grid>

          <Grid item xs={12}>
            <Button
              variant="contained"
              color="primary"
              size="large"
              fullWidth
              type="submit"
              disabled={loading}
            >
              {loading ? "Submitting..." : "Submit"}
            </Button>
          </Grid>
        </Grid>
      </form>

      {error && (
        <Typography variant="h6" style={{ color: "red", marginTop: "20px" }}>
          {" "}
          {error}{" "}
        </Typography>
      )}
    </Fragment>
  );
};

export default UpdateLaptopForm;
