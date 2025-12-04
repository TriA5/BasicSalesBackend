package ontap.example.ontap.service.serviceImpl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ontap.example.ontap.dto.CartItemDTO;
import ontap.example.ontap.entity.Cart;
import ontap.example.ontap.entity.CartItem;
import ontap.example.ontap.entity.Product;
import ontap.example.ontap.entity.User;
import ontap.example.ontap.exception.BadRequestException;
import ontap.example.ontap.exception.NotFoundException;
import ontap.example.ontap.repository.CartItemRepository;
import ontap.example.ontap.repository.CartRepository;
import ontap.example.ontap.repository.ProductRepository;
import ontap.example.ontap.repository.UserRepository;
import ontap.example.ontap.service.CartService;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public Cart addToCart(UUID userId, UUID productId, CartItemDTO cartItemDTO){
        //kiểm tra người dùng có tồn tại không
        User user = userRepository.findById(userId)
                    .orElseThrow(()->  new NotFoundException("Người dùng không tồn tại"));
        //Lấy cart của người dùng
        Cart cart = cartRepository.findByUserId(userId)
                    .orElseGet(()->{
                        Cart newCart = new Cart();
                        newCart.setUser(user);
                        return cartRepository.save(newCart);
                
                    });
        //Kiểm tra sản phẩm có tòn tại hay không
        Product product = productRepository.findById(productId)
                          .orElseThrow(() -> new NotFoundException("Sản phẩm không tồn tại"));
        
        //Kiểm tra product có trong giỏ hàng chưa
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                            .orElse(null);

        //kiểm tra số lượng >0
        if(cartItemDTO.getQuantity() <= 0){
            throw new BadRequestException("Số lượng > 0");
        }
        //
        if(cartItem == null){
            //nếu chưa có tạo mới cartItem
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            int newQuantity = cartItemDTO.getQuantity();
            if(newQuantity > product.getSoLuong()){
                throw new BadRequestException("Số lượng vượt quá tồn kho");
            }
            else{
                newCartItem.setQuantity(cartItemDTO.getQuantity());
            }
            newCartItem.setPrice((product.getPrice()));
            cartItemRepository.save(newCartItem);
        }else{
            //nếu đã có ròi
            int newQuantity = cartItem.getQuantity() + cartItemDTO.getQuantity();
            if(newQuantity > product.getSoLuong()){
                throw new BadRequestException("Số lượng vượt quá tồn kho");
            }
            else{
                cartItem.setQuantity(cartItem.getQuantity() + cartItemDTO.getQuantity());
            }
            cartItem.setPrice(product.getPrice());
            cartItemRepository.save(cartItem);
        }
        return cart;
    }
}
