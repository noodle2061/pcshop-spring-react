package com.id.akn.controller;

import com.id.akn.exception.CartItemException;
import com.id.akn.exception.UserException;
import com.id.akn.model.User;
import com.id.akn.request.CartItemDTO;
import com.id.akn.response.ApiRes;
import com.id.akn.service.CartItemService;
import com.id.akn.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cart_items")
@Tag(name="Cart Item Management", description = "create cart item delete cart item")
public class CartItemController {

    private CartItemService cartItemService;
    private UserService userService;


    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiRes>deleteCartItemHandler(@PathVariable String cartItemId, @RequestHeader("Authorization")String jwt) throws CartItemException, UserException{

        User user=userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiRes res=new ApiRes("Item Remove From Cart",true);

        return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItemDTO>updateCartItemHandler(@PathVariable String cartItemId, @RequestBody CartItemDTO cartItem,

                                                            @RequestHeader("Authorization")String jwt) throws CartItemException, UserException{
        User user=userService.findUserProfileByJwt(jwt);
        CartItemDTO updatedCartItem =cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
        return new ResponseEntity<>(updatedCartItem,HttpStatus.ACCEPTED);
    }
}

