package ontap.example.ontap.security;

public class Endpoints {
    public static final String[] PUBLIC_GET = {
        "/users",
        "/api/roles",
        "/api/users",
        "/api/users/**",
        "/api/products",
        "/api/products/**",
        "/api/categorys/**",
        "/api/categorys/"
    };
    public static final String[] PUBLIC_POST = {
        "/users",
        "/api/roles",
        "/api/users",
        "/api/users/**",
        "/api/products",
        "/api/products/**",
        "/api/carts/**",
        "/api/address/**",
        "/api/orders/**"
    };
    public static final String[] PUBLIC_PUT = {
        "/users",
        "/api/roles",
        "/api/users",
        "/api/products",
        "/api/products/**",
        "/api/carts/**",
        "/api/address"
    };
    public static final String[] PUBLIC_DELETE = {
        "/users",
        "/api/roles",
        "/api/users",
        "/api/products",
        "/api/products/**",
        "/api/address/**"
    };
}
