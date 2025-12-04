package ontap.example.ontap.service.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ontap.example.ontap.entity.Address;
import ontap.example.ontap.entity.Cart;
import ontap.example.ontap.entity.CartItem;
import ontap.example.ontap.entity.Order;
import ontap.example.ontap.entity.OrderItem;
import ontap.example.ontap.entity.Product;
import ontap.example.ontap.entity.User;
import ontap.example.ontap.exception.BadRequestException;
import ontap.example.ontap.repository.AddressRepository;
import ontap.example.ontap.repository.CartItemRepository;
import ontap.example.ontap.repository.CartRepository;
import ontap.example.ontap.repository.OrderItemRepository;
import ontap.example.ontap.repository.OrderRepository;
import ontap.example.ontap.repository.ProductRepository;
import ontap.example.ontap.repository.UserRepository;
import ontap.example.ontap.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    public Order checkout(UUID userId, UUID addressId) {
        // Kiểm tra user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User không tồn tại"));

        // Kiểm tra address
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new BadRequestException("Address không tồn tại"));

        // Lấy cart của người dùng
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new BadRequestException("Giỏ hàng trống"));

        // Force load cartItems (để lazy collection được quản lý bởi JPA)
        //SELECT * FROM cart_items WHERE cart_id = :cartId
        //Lây tất cã các cartItems của cart có id hiện tại
        //Cho tôi tất cả CartItem mà cart_id = id của cart hiện tại
        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new BadRequestException("Giỏ hàng trống");
        }

        // Tạo order mới
        Order order = new Order();
        order.setUser(user);
        order.setPhone(address.getPhone());
        order.setStreet(address.getStreet());
        order.setWard(address.getWard());
        order.setDistrict(address.getDistrict());
        order.setCity(address.getCity());
        order = orderRepository.save(order); // lưu trước để lấy id

        double totalPrice = 0;

        for (CartItem cartItem : cartItems) {
            //Lấy sản phẩm mà cartItem này đại diện (cartItem.getProduct()) gán vào object product
            //product ở đây chính là sản phẩm mà CartItem này đại diện.
            Product product = cartItem.getProduct();

            // kiểm tra tồn kho
            if (cartItem.getQuantity() > product.getSoLuong()) {
                throw new BadRequestException("Sản phẩm " + product.getNameProduct() + " vượt quá tồn kho");
            }

            // trừ tồn kho
            product.setSoLuong(product.getSoLuong() - cartItem.getQuantity());
            productRepository.save(product);

            // tạo OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItemRepository.save(orderItem);

            totalPrice += cartItem.getPrice() * cartItem.getQuantity();
        }

        // cập nhật tổng tiền
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        // **Force remove cartItems trước khi delete cart**
        cart.getCartItems().clear(); // orphanRemoval = true sẽ xoá CartItem khi flush
        cartRepository.delete(cart); // xoá cart

        return order;
    }
}
