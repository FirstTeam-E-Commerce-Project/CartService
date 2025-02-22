package ecom_ms.controller;
import ecom_ms.entity.Cart;
import ecom_ms.entity.CartItem;
import ecom_ms.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class ProductController {

    @Autowired
    public CartService cartService;

    @PostMapping("/{userId}/add")
    public Cart addCartItem(@PathVariable String userId, @RequestBody CartItem cartItem) {
        return cartService.addCartItem(userId, cartItem.getProductId(), cartItem.getQuantity());
    }

    @GetMapping("/{cartId}")
    public Cart getCartById(@PathVariable Long cartId) {
        return cartService.getCartById(cartId);
    }

    @GetMapping
    public List<Cart> getAllCarts(){
        return cartService.getAllCarts();
    }

    @PutMapping("/update")
    public ResponseEntity<Cart> updateCartItem(@RequestParam String userId, @RequestParam long productId, @RequestParam int newQuantity) {
        return ResponseEntity.ok(cartService.updateCartItem(userId, productId, newQuantity));
    }

    @DeleteMapping("/{userId}/items/{productId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable String userId, @PathVariable long productId) {
        try {
            cartService.deleteCartItem(userId, productId);
            return ResponseEntity.ok("Cart item deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}/items")
    public ResponseEntity<String> deleteAllCartItems(@PathVariable String userId) {
        try {
            cartService.deleteAllCartItems(userId);
            return ResponseEntity.ok("All cart items deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
