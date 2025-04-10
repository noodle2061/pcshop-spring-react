import {
  Avatar,
  Box,
  Button,
  Card,
  CardHeader,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  Modal,
  Pagination,
  Select,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
} from "@mui/material";
import React from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { useState } from "react";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { deleteLaptop, findLaptops } from "../../../Redux/Admin/Laptop/Action";
import UpdateLaptopForm from "../UpdateLaptop/UpdateLaptop";

const ModelUpdate = (props) => {
  return (
    <Modal
      open={props.open}
      onClose={props.handleClose}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
      sx={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        overflow: "hidden",
        padding: 2,
      }}
    >
      <Box className="relative rounded-md bg-black">
        <div className="cursor-pointer z-50 absolute top-3 right-3 rounded-lg p-3 bg-slate-500" onClick={props.handleClose}>
          x
        </div>
        <Box
          sx={{
            maxHeight: "90vh",
            overflowY: "auto",
            scrollbarWidth: "none",
            msOverflowStyle: "none",
            padding: 2,
            boxSizing: "border-box",
            "&::-webkit-scrollbar": {
              display: "none",
            },
          }}
        >
          <UpdateLaptopForm id={props.id} />
        </Box>
      </Box>
    </Modal>
  );
};

const LaptopsTable = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { laptop } = useSelector((store) => store);
  // const {laptop} = useSelector((store) => store);

  // query
  const searchParams = new URLSearchParams(location.search);
  const availability = searchParams.get("availability");
  const category = searchParams.get("category");
  const sortPrice = searchParams.get("sortPrice");
  const page = searchParams.get("page");
  const [open, setOpen] = useState(false);
  const [update, setUpdate] = useState(null);

  const handlePaginationChange = (event, value) => {
    searchParams.set("page", value - 1);
    const query = searchParams.toString();
    navigate({ search: `?${query}` });
  };

  const handleClose = () => {
    setOpen(false);
  };

  useEffect(() => {
    // setFilterValue({ availability, category, sort });
    const data = {
      category: category || "",
      colors: [],
      sizes: [],
      minPrice: 0,
      // maxPrice: 100000,
      minDiscount: 0,
      sortPrice: sortPrice || "increase",
      pageNumber: page || 0,
      pageSize: 10,
      stock: availability,
    };
    dispatch(findLaptops(data));
  }, [availability, category, sortPrice, page, laptop?.deleteLaptop]);

  const handleDeleteLaptop = (laptopId) => {
    window.location.reload()
    dispatch(deleteLaptop(laptopId));
  };

  const handleUpdate = (id) => {
    if (id) {
      setOpen(true);
      setUpdate(id);
    }
  };

  return (
    <Box width={"100%"}>
      <ModelUpdate open={open} handleClose={handleClose} id={update} />
      <Card className="mt-2">
        <CardHeader
          title="Danh sách sản phẩm"
          sx={{
            pt: 2,
            alignItems: "center",
            "& .MuiCardHeader-action": { mt: 0.6 },
          }}
        />
        <TableContainer>
          <Table sx={{ minWidth: 800 }} aria-label="table in dashboard">
            <TableHead>
              <TableRow>
                <TableCell></TableCell>
                <TableCell>Mẫu laptop</TableCell>
                <TableCell sx={{ textAlign: "center" }}>Hang mục</TableCell>
                <TableCell sx={{ textAlign: "center" }}>Giá</TableCell>
                <TableCell sx={{ textAlign: "center" }}>Số lượng</TableCell>
                <TableCell sx={{ textAlign: "center" }}>Cập nhật</TableCell>

                <TableCell sx={{ textAlign: "center" }}>Xóa</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {laptop?.laptops?.map((item) => (
                <TableRow
                  hover
                  key={item.model}
                  sx={{ "&:last-of-type td, &:last-of-type th": { border: 0 } }}
                >
                  <TableCell>
                    {" "}
                    <Avatar
                      alt={item.model}
                      src={"http://localhost:8080" + item.imageUrls[0]}
                    />{" "}
                  </TableCell>

                  <TableCell
                    sx={{ py: (theme) => `${theme.spacing(0.5)} !important` }}
                  >
                    <Box sx={{ display: "flex", flexDirection: "column" }}>
                      <Typography
                        sx={{
                          fontWeight: 500,
                          fontSize: "0.875rem !important",
                        }}
                      >
                        {item.model}
                      </Typography>
                      <Typography variant="caption">{item.brand}</Typography>
                    </Box>
                  </TableCell>
                  <TableCell sx={{ textAlign: "center" }}>
                    {item?.categories[0]?.name}
                  </TableCell>
                  <TableCell sx={{ textAlign: "center" }}>
                    {item.price}
                  </TableCell>
                  <TableCell sx={{ textAlign: "center" }}>
                    {item?.laptopColors[0]?.quantity}
                  </TableCell>

                  <TableCell sx={{ textAlign: "center" }}>
                    <Button
                      variant="text"
                      onClick={() => handleUpdate(item.id)}
                    >
                      Cập nhật
                    </Button>
                  </TableCell>
                  <TableCell hidden={item.status === 0} sx={{ textAlign: "center" }}>
                    <Button
                      className={`${item.status === 0 ? "!hidden" : ""}`}
                      variant="text"
                      onClick={() => handleDeleteLaptop(item.id)}
                    >
                      Xóa
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Card>
      <Card className="mt-2 border">
        <div className="mx-auto px-4 py-5 flex justify-center shadow-lg rounded-md">
          <Pagination
            count={laptop?.laptops?.totalPages}
            color="primary"
            className=""
            onChange={handlePaginationChange}
          // value={page}
          />
        </div>
      </Card>
    </Box>
  );
};

export default LaptopsTable;
