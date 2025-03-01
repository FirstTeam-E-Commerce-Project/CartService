package ecom_ms.service;

import ecom_ms.Feign.ProductClient;
import ecom_ms.dto.Product;
import ecom_ms.entity.Cart;
import ecom_ms.entity.CartItem;
import ecom_ms.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductClient productClient;

    public Cart addCartItem(String userId, long productId, int quantity) {
        Product product = productClient.getProductById(productId);

        if (product != null && product.getPrice() > 0) {
            Cart cart = cartRepository.findByUserId(userId).orElse(new Cart());
            cart.setUserId(userId);

            Optional<CartItem> existingItem = cart.getCartItems().stream()
                    .filter(item -> item.getProductId() == productId)
                    .findFirst();

            if (existingItem.isPresent()) {
                CartItem cartItem = existingItem.get();
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
            } else {
                CartItem cartItem = new CartItem();
                cartItem.setProductId(productId);
                cartItem.setQuantity(quantity);
                cartItem.setCart(cart);
                cart.getCartItems().add(cartItem);
            }

            return cartRepository.save(cart);
        } else {
            throw new IllegalArgumentException("Invalid product details");
        }

    }

    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with ID: " + cartId));
    }
        public List<Cart> getAllCarts(){
        return cartRepository.findAll();
    }

    public Cart updateCartItem(String userId, long productId, int newQuantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user: " + userId));

        Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                .filter(item -> item.getProductId() == productId)
                .findFirst();

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();

            if (newQuantity > 0) {
                cartItem.setQuantity(newQuantity);
            } else {

                cart.getCartItems().remove(cartItem);
            }

            return cartRepository.save(cart);
        } else {
            throw new IllegalArgumentException("Cart item not found for product ID: " + productId);
        }
    }

    public void deleteCartItem(String userId, long productId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user: " + userId));

        Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                .filter(item -> item.getProductId() == productId)
                .findFirst();

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cart.getCartItems().remove(cartItem);
            cartRepository.save(cart);
        } else {
            throw new IllegalArgumentException("Cart item not found for product ID: " + productId);
        }
    }

    public void deleteAllCartItems(String userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user: " + userId));

        cart.getCartItems().clear();
        cartRepository.save(cart);
    }
 }
