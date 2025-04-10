import * as React from "react";
import { Grid, TextField, Button, Box, FormControl, FormLabel, RadioGroup, FormControlLabel, Radio, Typography } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { createOrder } from "../../Redux/Customers/Order/Action";
import AddressCard from "../Address/AddressCard";
import { useState } from "react";

export default function AddDeliveryAddressForm({ handleNext }) {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { selectedCartItems } = useSelector((store) => store.order); // Danh sách items đã chọn
  const { auth } = useSelector((store) => store);

  const [selectedAddress, setSelectedAddress] = useState(null);
  const [form, setForm] = useState({
    "id": null,
    "name": "",
    "streetAddress": "",
    "city": "",
    "phoneNumber": ""
  });

  const [paymentMethod, setPaymentMethod] = useState(""); // Không chọn mặc định
  const [error, setError] = useState(""); // Lưu thông báo lỗi


  // Xử lý cập nhật phương thức thanh toán
  const handlePaymentMethodChange = (event) => {
    setPaymentMethod(event.target.value);
    setError(""); // Xóa thông báo lỗi nếu đã chọn phương thức
  };

  // Tạo order với địa chỉ có sẵn
  const handleCreateOrderWithExistingAddress = () => {
    if (!paymentMethod) {
      setError("Vui lòng chọn phương thức thanh toán trước khi tiếp tục!");
      return;
    }

    // Gửi toàn bộ dữ liệu với địa chỉ đã chọn
    dispatch(
      createOrder({
        shippingAddress: selectedAddress,
        paymentMethod,
        cartItems: selectedCartItems,
        navigate,
      })
    );
    handleNext();
  };
  const handleSelectAddress = (address) => {
    setSelectedAddress(address);
    setForm(address)
  }

  // Tạo order với địa chỉ mới
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);

    // Địa chỉ từ form
    const address = {
      name: data.get("name"),
      streetAddress: data.get("streetAddress"),
      city: data.get("city"),
      phoneNumber: data.get("phoneNumber"),
    };

    if (!paymentMethod) {
      setError("Vui lòng chọn phương thức thanh toán trước khi tiếp tục!");
      return;
    }

    // Gửi toàn bộ dữ liệu để tạo order
    dispatch(
      createOrder({
        shippingAddress: address,
        paymentMethod,
        cartItems: selectedCartItems,
        navigate,
      })
    );
    handleNext();
  };

  return (
    <Grid container spacing={4}>
      {/* Cột danh sách địa chỉ */}
      <Grid item xs={12} lg={5}>
        <Box className="border rounded-md shadow-md h-[30.5rem] overflow-y-scroll">
          {auth?.user?.addresses?.map((item) => (
            <div
              key={item.id}
              onClick={() => setSelectedAddress(item)}
              className="p-5 py-7 border-b cursor-pointer"
            >
              <AddressCard address={item} />
              {selectedAddress?.id === item.id && (
                <Button
                  sx={{ mt: 2 }}
                  size="large"
                  variant="contained"
                  color="primary"
                  onClick={() => handleSelectAddress(item)}
                >
                  Giao hàng tại đây
                </Button>
              )}
            </div>
          ))}
        </Box>
      </Grid>

      {/* Cột điền địa chỉ mới và chọn phương thức thanh toán */}
      <Grid item xs={12} lg={7}>
        <Box className="border rounded-md shadow-md p-5">
          {/* Form chọn phương thức thanh toán */}
          <FormControl component="fieldset" sx={{ mb: 3 }}>
            <FormLabel component="legend">Phương thức thanh toán</FormLabel>
            <RadioGroup
              row
              aria-label="paymentMethod"
              name="paymentMethod"
              value={paymentMethod}
              onChange={handlePaymentMethodChange}
            >
              <FormControlLabel value="COD" control={<Radio />} label="COD" />
              <FormControlLabel value="VISA" control={<Radio />} label="QR CODE" />
              {/* <FormControlLabel value="MasterCard" control={<Radio />} label="MasterCard" /> */}
            </RadioGroup>
          </FormControl>
          {error && (
            <Typography color="error" variant="body2" sx={{ mb: 3 }}>
              {error}
            </Typography>
          )}

          {/* Form điền địa chỉ mới */}
          <form onSubmit={handleSubmit}>
            <Grid container spacing={3}>
              <Grid item xs={12} sm={6}>
                <TextField
                  required
                  id="name"
                  name="name"
                  label="Họ và tên"
                  fullWidth
                  autoComplete="given-name"
                  value={form.name}
                  onChange={(e) => setForm({ ...form, name: e.target.value })}
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  required
                  id="phoneNumber"
                  name="phoneNumber"
                  label="Số điện thoại"
                  fullWidth
                  autoComplete="given-name"
                  value={form.phoneNumber}
                  onChange={(e) => setForm({ ...form, phoneNumber: e.target.value })}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  id="streetAddress"
                  name="streetAddress"
                  label="Địa chỉ"
                  fullWidth
                  autoComplete="shipping address"
                  multiline
                  rows={3}
                  value={form.streetAddress}
                  onChange={(e) => setForm({ ...form, streetAddress: e.target.value })}
                />
              </Grid>
              <Grid item xs={12} >
                <TextField
                  required
                  id="city"
                  name="city"
                  label="Thành phố"
                  fullWidth
                  autoComplete="shipping address-level2"
                  value={form.city}
                  onChange={(e) => setForm({ ...form, city: e.target.value })}
                />
              </Grid>
              <Grid item xs={12}>
                <Button
                  sx={{ padding: ".9rem 1.5rem" }}
                  size="large"
                  type="submit"
                  variant="contained"
                  color="primary"
                >
                  Tiếp tục
                </Button>
              </Grid>
            </Grid>
          </form>
        </Box>
      </Grid>
    </Grid>
  );
}
